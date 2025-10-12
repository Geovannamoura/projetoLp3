package org.organizador.repositorio;

import org.organizador.modelo.Usuario;
import org.organizador.modelo.Tarefa;
import java.io.*;
import java.util.*;

public class RepositorioUsuario {
    private final String arquivo = "usuarios.dat";
    private List<Usuario> itens = new ArrayList<>();
    private int proxId = 1;

    // Referência ao repositório de tarefas (para verificar vínculos)
    private final RepositorioTarefa repoTarefa;

    // Construtor agora recebe o repositório de tarefas
    public RepositorioUsuario(RepositorioTarefa repoTarefa) {
        this.repoTarefa = repoTarefa;
        carregar();
    }

    @SuppressWarnings("unchecked")
    private void carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            itens = (List<Usuario>) ois.readObject();
            proxId = itens.stream().mapToInt(Usuario::getId).max().orElse(0) + 1;
        } catch (Exception e) {
            System.err.println("Erro ao carregar usuários: " + e.getMessage());
        }
    }

    private void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(itens);
        } catch (IOException e) {
            System.err.println("Erro ao salvar usuários: " + e.getMessage());
        }
    }

    /** Cria um novo usuário */
    public Usuario criar(String nome, String email) {
        Usuario u = new Usuario(proxId++, nome, email);
        itens.add(u);
        salvar();
        return u;
    }

    /** Lista todos os usuários */
    public List<Usuario> listar() {
        return new ArrayList<>(itens);
    }

    /** Busca usuário pelo ID */
    public Usuario buscarPorId(int id) {
        return itens.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    /** Atualiza um usuário existente */
    public boolean atualizar(Usuario atualizado) {
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
     * Impede excluir usuário se houver tarefas associadas a ele.
     */
    public boolean deletar(int id) {
        // 1️⃣ Verifica se existem tarefas associadas
        List<Tarefa> tarefasDoUsuario = repoTarefa.listarPorUsuario(id);

        if (!tarefasDoUsuario.isEmpty()) {
            System.out.println("Não é possível excluir este usuário!");
            System.out.println("Existem tarefas associadas a ele.");
            return false;
        }

        // 2️⃣ Se não houver tarefas, exclui normalmente
        boolean removido = itens.removeIf(u -> u.getId() == id);
        if (removido) {
            salvar();
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Usuário não encontrado.");
        }
        return removido;
    }
}