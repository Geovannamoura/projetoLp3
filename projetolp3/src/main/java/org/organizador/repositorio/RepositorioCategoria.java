package org.organizador.repositorio;

import org.organizador.modelo.Categoria;
import org.organizador.modelo.Tarefa;

import java.io.*;
import java.util.*;

/**
 * Repositório responsável por gerenciar as Categorias.
 * Inclui delete seguro — impede exclusão se houver tarefas vinculadas.
 */
public class RepositorioCategoria {
    private final String arquivo = "categorias.dat";
    private final String arquivoTemp = "categorias_temp.dat";

    private List<Categoria> itens = new ArrayList<>();
    private int proxId = 1;

    // Referência ao repositório de tarefas, usada para verificar vínculos
    private RepositorioTarefa repoTarefa;

    /**
     * Construtor com repositório de tarefas (para permitir verificação de vínculos)
     */
    public RepositorioCategoria(RepositorioTarefa repoTarefa) {
        this.repoTarefa = repoTarefa;
        carregar();
    }

    /**
     * Construtor padrão (sem repositório de tarefas configurado ainda)
     */
    public RepositorioCategoria() {
        carregar();
    }

    /**
     * Setter opcional, útil se você instanciar os repositórios separadamente
     */
    public void setRepositorioTarefa(RepositorioTarefa repoTarefa) {
        this.repoTarefa = repoTarefa;
    }

    // ---------------------- MÉTODOS DE PERSISTÊNCIA ----------------------

    @SuppressWarnings("unchecked")
    private void carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return; // se ainda não existe arquivo, não faz nada
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            itens = (List<Categoria>) ois.readObject();
            // atualiza o próximo ID com base no maior já existente
            proxId = itens.stream().mapToInt(Categoria::getId).max().orElse(0) + 1;
        } catch (Exception e) {
            System.err.println("Erro ao carregar categorias: " + e.getMessage());
        }
    }

    /**
     * Método de salvamento com segurança — usa arquivo temporário antes de substituir o principal.
     */
    private synchronized void salvar() {
        File original = new File(arquivo);
        File temp = new File(arquivoTemp);

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(temp))) {
            oos.writeObject(itens);
            oos.flush();
        } catch (IOException e) {
            System.err.println("Erro ao salvar categorias (etapa temporária): " + e.getMessage());
            return;
        }

        if (original.exists()) original.delete();
        temp.renameTo(original);
    }

    // ---------------------- CRUD ----------------------

    /** Cria nova categoria */
    public synchronized Categoria criar(String nome) {
        Categoria c = new Categoria(proxId++, nome);
        itens.add(c);
        salvar();
        return c;
    }

    /** Lista todas as categorias */
    public List<Categoria> listar() {
        return new ArrayList<>(itens);
    }

    /** Busca categoria pelo ID */
    public Categoria buscarPorId(int id) {
        return itens.stream().filter(c -> c.getId() == id).findFirst().orElse(null);
    }

    /** Atualiza categoria existente */
    public synchronized boolean atualizar(Categoria atualizado) {
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
     * Impede exclusão da categoria se houver tarefas associadas a ela.
     */
    public synchronized boolean deletar(int id) {
        // Verifica se o repositório de tarefas foi configurado
        if (repoTarefa == null) {
            System.err.println("Erro: repositório de tarefas não configurado!");
            return false;
        }

        // Busca tarefas associadas a esta categoria
        List<Tarefa> tarefasDaCategoria = repoTarefa.listar()
                .stream()
                .filter(t -> t.getIdCategoria() == id)
                .toList();

        // Se houver vínculos, não permite excluir
        if (!tarefasDaCategoria.isEmpty()) {
            System.out.println("Não é possível excluir esta categoria!");
            System.out.println("Existem tarefas associadas a ela.");
            return false;
        }

        // Se não houver vínculos, remove normalmente
        boolean removido = itens.removeIf(c -> c.getId() == id);
        if (removido) {
            salvar();
            System.out.println("Categoria removida com sucesso!");
        } else {
            System.out.println("Categoria não encontrada.");
        }
        return removido;
    }
}
