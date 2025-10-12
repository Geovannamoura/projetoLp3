package org.organizador.repositorio;

import org.organizador.modelo.Projeto;
import org.organizador.modelo.Tarefa;

import java.io.*;
import java.util.*;

/**
 * Repositório responsável por gerenciar os Projetos.
 * Inclui delete seguro — não permite excluir se houver tarefas associadas.
 */
public class RepositorioProjeto {
    private final String arquivo = "projetos.dat"; // nome do arquivo onde os dados são salvos
    private final String arquivoTemp = "projetos_temp.dat"; // arquivo temporário para salvar com segurança

    private List<Projeto> itens = new ArrayList<>();
    private int proxId = 1;

    // Referência ao repositório de tarefas (para checar vínculos)
    private RepositorioTarefa repoTarefa;

    /**
     * Construtor que recebe o repositório de tarefas — usado quando queremos verificar vínculos.
     */
    public RepositorioProjeto(RepositorioTarefa repoTarefa) {
        this.repoTarefa = repoTarefa;
        carregar();
    }

    /**
     * Construtor alternativo — usado quando ainda não queremos verificar vínculos.
     * (mas permite depois configurar o repo de tarefas com um setter)
     */
    public RepositorioProjeto() {
        carregar();
    }

    /** Permite definir o repositório de tarefas após a criação (útil em casos de dependência circular) */
    public void setRepositorioTarefa(RepositorioTarefa repoTarefa) {
        this.repoTarefa = repoTarefa;
    }

    // ---------- CARREGAR E SALVAR DADOS ----------
    @SuppressWarnings("unchecked")
    private void carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            itens = (List<Projeto>) ois.readObject();
            proxId = itens.stream().mapToInt(Projeto::getId).max().orElse(0) + 1;
        } catch (Exception e) {
            System.err.println("Erro ao carregar projetos: " + e.getMessage());
        }
    }

    /** Salvar com segurança — escreve em arquivo temporário antes de substituir o principal */
    private synchronized void salvar() {
        File original = new File(arquivo);
        File temp = new File(arquivoTemp);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp))) {
            oos.writeObject(itens);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Erro ao salvar projetos (etapa temporária): " + e.getMessage());
            return;
        }

        if (original.exists()) original.delete();
        temp.renameTo(original);
    }

    // ---------- CRUD ----------
    /** Criação de projeto */
    public synchronized Projeto criar(String nome, String descricao) {
        Projeto p = new Projeto(proxId++, nome, descricao);
        itens.add(p);
        salvar();
        return p;
    }

    /** Listagem de todos os projetos */
    public List<Projeto> listar() {
        return new ArrayList<>(itens);
    }

    /** Busca projeto pelo ID */
    public Projeto buscarPorId(int id) {
        return itens.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
    }

    /** Atualização de projeto */
    public synchronized boolean atualizar(Projeto atualizado) {
        for (int i = 0; i < itens.size(); i++) {
            if (itens.get(i).getId() == atualizado.getId()) {
                itens.set(i, atualizado);
                salvar();
                return true;
            }
        }
        return false;
    }

    /**
     * DELETE SEGURO:
     * Impede excluir projeto se houver tarefas associadas a ele.
     */
    public synchronized boolean deletar(int id) {
        // 1️⃣ Verifica se há repositório de tarefas configurado
        if (repoTarefa == null) {
            System.err.println("Erro: Repositório de tarefas não foi configurado!");
            return false;
        }

        // 2️⃣ Busca tarefas associadas a este projeto (usa método correto)
        List<Tarefa> tarefasDoProjeto = repoTarefa.listar()
                .stream()
                .filter(t -> t.getIdProjeto() == id)
                .toList();

        // 3️⃣ Impede exclusão se houver vínculos
        if (!tarefasDoProjeto.isEmpty()) {
            System.out.println("❌ Não é possível excluir este projeto!");
            System.out.println("Existem tarefas associadas a ele.");
            return false;
        }

        // 4️⃣ Se não houver tarefas, exclui normalmente
        boolean removido = itens.removeIf(p -> p.getId() == id);
        if (removido) {
            salvar();
            System.out.println("✅ Projeto removido com sucesso!");
        } else {
            System.out.println("⚠️ Projeto não encontrado.");
        }
        return removido;
    }
}
