package org.organizador.repositorio;

import org.organizador.modelo.Tarefa;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/** Repositório para Tarefas */
public class RepositorioTarefa {
    private final String arquivo = "tarefas.dat";
    private List<Tarefa> itens = new ArrayList<>();
    private int proxId = 1;

    public RepositorioTarefa() { carregar(); }

    @SuppressWarnings("unchecked")
    private void carregar() {
        File f = new File(arquivo);
        if (!f.exists()) return;
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            itens = (List<Tarefa>) ois.readObject();
            proxId = itens.stream().mapToInt(Tarefa::getId).max().orElse(0) + 1;
        } catch (Exception e) { System.err.println("Erro ao carregar tarefas: " + e.getMessage()); }
    }

    private void salvar() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(itens);
        } catch (IOException e) { System.err.println("Erro ao salvar tarefas: " + e.getMessage()); }
    }

    public Tarefa criar(String titulo, String descricao, int idUsuario, int idProjeto, int idCategoria,
                        LocalDate dataVencimento, Tarefa.Status status) {
        Tarefa t = new Tarefa(proxId++, titulo, descricao, idUsuario, idProjeto, idCategoria, dataVencimento, status);
        itens.add(t); salvar(); return t;
    }

    public List<Tarefa> listar() { return new ArrayList<>(itens); }
    public Tarefa buscarPorId(int id) { return itens.stream().filter(t -> t.getId()==id).findFirst().orElse(null); }
    public boolean atualizar(Tarefa atualizado) {
        for (int i=0;i<itens.size();i++){
            if (itens.get(i).getId() == atualizado.getId()) { itens.set(i, atualizado); salvar(); return true; }
        }
        return false;
    }
    public boolean deletar(int id) {
        boolean rem = itens.removeIf(t -> t.getId() == id);
        if (rem) salvar();
        return rem;
    }

    // Relatórios básicos
    public Map<Tarefa.Status, List<Tarefa>> agruparPorStatus() {
        return itens.stream().collect(Collectors.groupingBy(Tarefa::getStatus));
    }

    public List<Tarefa> vencidas(LocalDate hoje) {
        return itens.stream()
                .filter(t -> t.getDataVencimento() != null && t.getDataVencimento().isBefore(hoje))
                .collect(Collectors.toList());
    }

    public Map<Integer, Long> contarPorProjeto() {
        return itens.stream().collect(Collectors.groupingBy(Tarefa::getIdProjeto, Collectors.counting()));
    }

    public Map<Integer, Long> contarPorUsuario() {
        return itens.stream().collect(Collectors.groupingBy(Tarefa::getIdUsuario, Collectors.counting()));
    }

    public List<Tarefa> listarPorUsuario(int idUsuario) {
        List<Tarefa> resultado = new ArrayList<>();
        for (Tarefa t : itens) {
            if (t.getIdUsuario() == idUsuario) {
                resultado.add(t);
            }
        }
        return resultado;
    }
}

