package org.organizador;

import org.organizador.modelo.*;
import org.organizador.repositorio.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final Scanner sc = new Scanner(System.in);

    // Repositórios agora serão criados dentro do main()
    private static RepositorioUsuario repoUsuario;
    private static RepositorioProjeto repoProjeto;
    private static RepositorioCategoria repoCategoria;
    private static RepositorioTarefa repoTarefa;

    public static void main(String[] args) {
        // Ordem de criação correta
        repoTarefa = new RepositorioTarefa();
        repoUsuario = new RepositorioUsuario(repoTarefa);
        repoProjeto = new RepositorioProjeto(repoTarefa);
        repoCategoria = new RepositorioCategoria(repoTarefa);

        System.out.println("=== Organizador de Tarefas (Terminal) ===");

        while (true) {
            System.out.println("\nMenu principal:");
            System.out.println("1) Usuários  2) Projetos  3) Categorias  4) Tarefas  5) Relatórios  0) Sair");
            System.out.print("Escolha: ");
            String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": menuUsuarios(); break;
                case "2": menuProjetos(); break;
                case "3": menuCategorias(); break;
                case "4": menuTarefas(); break;
                case "5": menuRelatorios(); break;
                case "0": System.out.println("Tchau!"); return;
                default: System.out.println("Opção inválida.");
            }
        }
    }

    // ---------- Menu Usuarios ----------
    private static void menuUsuarios() {
        while (true) {
            System.out.println("\n-- Usuários -- 1) Criar 2) Listar 3) Atualizar 4) Remover 0) Voltar");
            System.out.print("Escolha: "); String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": criarUsuario(); break;
                case "2": listarUsuarios(); break;
                case "3": atualizarUsuario(); break;
                case "4": removerUsuario(); break;
                case "0": return;
                default: System.out.println("Inválido");
            }
        }
    }
    private static void criarUsuario() {
        System.out.print("Nome: "); String nome = sc.nextLine();
        System.out.print("Email: "); String email = sc.nextLine();
        Usuario u = repoUsuario.criar(nome, email);
        System.out.println("Criado: " + u);
    }
    private static void listarUsuarios() {
        List<Usuario> all = repoUsuario.listar();
        if (all.isEmpty()) System.out.println("Nenhum usuário.");
        else all.forEach(System.out::println);
    }
    private static void atualizarUsuario() {
        try {
            System.out.print("ID do usuário a editar: "); int id = Integer.parseInt(sc.nextLine());
            Usuario u = repoUsuario.buscarPorId(id);
            if (u == null) { System.out.println("Não encontrado"); return; }
            System.out.print("Novo nome (" + u.getNome() + "): "); String nome = sc.nextLine();
            System.out.print("Novo email (" + u.getEmail() + "): "); String email = sc.nextLine();
            if (!nome.isBlank()) u.setNome(nome);
            if (!email.isBlank()) u.setEmail(email);
            if (repoUsuario.atualizar(u)) System.out.println("Atualizado"); else System.out.println("Erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }
    private static void removerUsuario() {
        try {
            System.out.print("ID do usuário a remover: "); int id = Integer.parseInt(sc.nextLine());
            boolean ok = repoUsuario.deletar(id);
            System.out.println(ok ? "Removido" : "Não encontrado/erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }

    // ---------- Projetos ----------
    private static void menuProjetos() {
        while (true) {
            System.out.println("\n-- Projetos -- 1) Criar 2) Listar 3) Atualizar 4) Remover 0) Voltar");
            System.out.print("Escolha: "); String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": criarProjeto(); break;
                case "2": listarProjetos(); break;
                case "3": atualizarProjeto(); break;
                case "4": removerProjeto(); break;
                case "0": return;
                default: System.out.println("Inválido");
            }
        }
    }
    private static void criarProjeto() {
        System.out.print("Nome do projeto: "); String nome = sc.nextLine();
        System.out.print("Descrição: "); String desc = sc.nextLine();
        Projeto p = repoProjeto.criar(nome, desc);
        System.out.println("Criado: " + p);
    }
    private static void listarProjetos() {
        List<Projeto> all = repoProjeto.listar();
        if (all.isEmpty()) System.out.println("Nenhum projeto.");
        else all.forEach(System.out::println);
    }
    private static void atualizarProjeto() {
        try {
            System.out.print("ID do projeto a editar: "); int id = Integer.parseInt(sc.nextLine());
            Projeto p = repoProjeto.buscarPorId(id);
            if (p == null) { System.out.println("Não encontrado"); return; }
            System.out.print("Novo nome (" + p.getNome() + "): "); String nome = sc.nextLine();
            System.out.print("Nova descrição (" + p.getDescricao() + "): "); String d = sc.nextLine();
            if (!nome.isBlank()) p.setNome(nome);
            if (!d.isBlank()) p.setDescricao(d);
            if (repoProjeto.atualizar(p)) System.out.println("Atualizado"); else System.out.println("Erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }
    private static void removerProjeto() {
        try {
            System.out.print("ID do projeto a remover: "); int id = Integer.parseInt(sc.nextLine());
            boolean ok = repoProjeto.deletar(id);
            System.out.println(ok ? "Removido" : "Não encontrado/erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }

    // ---------- Categorias ----------
    private static void menuCategorias() {
        while (true) {
            System.out.println("\n-- Categorias -- 1) Criar 2) Listar 3) Atualizar 4) Remover 0) Voltar");
            System.out.print("Escolha: "); String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": criarCategoria(); break;
                case "2": listarCategorias(); break;
                case "3": atualizarCategoria(); break;
                case "4": removerCategoria(); break;
                case "0": return;
                default: System.out.println("Inválido");
            }
        }
    }
    private static void criarCategoria() {
        System.out.print("Nome da categoria: "); String nome = sc.nextLine();
        Categoria c = repoCategoria.criar(nome);
        System.out.println("Criado: " + c);
    }
    private static void listarCategorias() {
        List<Categoria> all = repoCategoria.listar();
        if (all.isEmpty()) System.out.println("Nenhuma categoria.");
        else all.forEach(System.out::println);
    }
    private static void atualizarCategoria() {
        try {
            System.out.print("ID da categoria a editar: "); int id = Integer.parseInt(sc.nextLine());
            Categoria c = repoCategoria.buscarPorId(id);
            if (c == null) { System.out.println("Não encontrado"); return; }
            System.out.print("Novo nome (" + c.getNome() + "): "); String nome = sc.nextLine();
            if (!nome.isBlank()) c.setNome(nome);
            if (repoCategoria.atualizar(c)) System.out.println("Atualizado"); else System.out.println("Erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }
    private static void removerCategoria() {
        try {
            System.out.print("ID da categoria a remover: "); int id = Integer.parseInt(sc.nextLine());
            boolean ok = repoCategoria.deletar(id);
            System.out.println(ok ? "Removido" : "Não encontrado/erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }

    // ---------- Tarefas ----------
    private static void menuTarefas() {
        while (true) {
            System.out.println("\n-- Tarefas -- 1) Criar 2) Listar 3) Atualizar 4) Remover 0) Voltar");
            System.out.print("Escolha: "); String opt = sc.nextLine().trim();
            switch (opt) {
                case "1": criarTarefa(); break;
                case "2": listarTarefas(); break;
                case "3": atualizarTarefa(); break;
                case "4": removerTarefa(); break;
                case "0": return;
                default: System.out.println("Inválido");
            }
        }
    }

    private static void criarTarefa() {
        try {
            System.out.print("Título: "); String titulo = sc.nextLine();
            System.out.print("Descrição: "); String desc = sc.nextLine();
            System.out.print("ID do usuário responsável: "); int uid = Integer.parseInt(sc.nextLine());
            if (repoUsuario.buscarPorId(uid) == null) { System.out.println("Usuário não existe."); return; }
            System.out.print("ID do projeto: "); int pid = Integer.parseInt(sc.nextLine());
            if (repoProjeto.buscarPorId(pid) == null) { System.out.println("Projeto não existe."); return; }
            System.out.print("ID da categoria: "); int cid = Integer.parseInt(sc.nextLine());
            if (repoCategoria.buscarPorId(cid) == null) { System.out.println("Categoria não existe."); return; }
            System.out.print("Data de vencimento (YYYY-MM-DD) ou vazio: "); String d = sc.nextLine();
            LocalDate venc = d.isBlank() ? null : LocalDate.parse(d);
            System.out.print("Status (AFAZER, FAZENDO, FEITO): "); String s = sc.nextLine().toUpperCase();
            Tarefa.Status status = Tarefa.Status.valueOf(s);
            Tarefa t = repoTarefa.criar(titulo, desc, uid, pid, cid, venc, status);
            System.out.println("Criada: " + t);
        } catch (DateTimeParseException ex) { System.out.println("Formato de data inválido."); } catch (NumberFormatException ex) { System.out.println("ID inválido."); } catch (IllegalArgumentException ex) { System.out.println("Status inválido."); }
    }

    private static void listarTarefas() {
        List<Tarefa> lista = repoTarefa.listar();
        if (lista.isEmpty()) { System.out.println("Nenhuma tarefa."); return; }
        for (Tarefa t : lista) {
            String nomeUsuario = nomeOuNA(repoUsuario.buscarPorId(t.getIdUsuario()));
            String nomeProjeto = nomeOuNA(repoProjeto.buscarPorId(t.getIdProjeto()));
            String nomeCategoria = nomeOuNA(repoCategoria.buscarPorId(t.getIdCategoria()));
            System.out.printf("ID:%d | %s | Resp:%s | Projeto:%s | Categoria:%s | Venc:%s | Status:%s%n",
                    t.getId(), t.getTitulo(), nomeUsuario, nomeProjeto, nomeCategoria,
                    t.getDataVencimento()==null ? "-" : t.getDataVencimento(), t.getStatus());
        }
    }

    private static String nomeOuNA(Object o) {
        if (o == null) return "n/a";
        if (o instanceof Usuario) return ((Usuario)o).getNome();
        if (o instanceof Projeto) return ((Projeto)o).getNome();
        if (o instanceof Categoria) return ((Categoria)o).getNome();
        return o.toString();
    }

    private static void atualizarTarefa() {
        try {
            System.out.print("ID da tarefa a editar: "); int id = Integer.parseInt(sc.nextLine());
            Tarefa t = repoTarefa.buscarPorId(id);
            if (t == null) { System.out.println("Não encontrado"); return; }
            System.out.print("Novo título (" + t.getTitulo() + "): "); String titulo = sc.nextLine();
            System.out.print("Nova descrição: "); String desc = sc.nextLine();
            System.out.print("Novo usuário responsável (id) (" + t.getIdUsuario() + "): "); String uid = sc.nextLine();
            System.out.print("Novo projeto (id) (" + t.getIdProjeto() + "): "); String pid = sc.nextLine();
            System.out.print("Nova categoria (id) (" + t.getIdCategoria() + "): "); String cid = sc.nextLine();
            System.out.print("Data de vencimento (YYYY-MM-DD) ou vazio (" + (t.getDataVencimento()==null? "": t.getDataVencimento()) + "): "); String d = sc.nextLine();
            System.out.print("Status (AFAZER, FAZENDO, FEITO) (" + t.getStatus() + "): "); String s = sc.nextLine();

            if (!titulo.isBlank()) t.setTitulo(titulo);
            if (!desc.isBlank()) t.setDescricao(desc);
            if (!uid.isBlank()) t.setIdUsuario(Integer.parseInt(uid));
            if (!pid.isBlank()) t.setIdProjeto(Integer.parseInt(pid));
            if (!cid.isBlank()) t.setIdCategoria(Integer.parseInt(cid));
            if (!d.isBlank()) t.setDataVencimento(LocalDate.parse(d));
            if (!s.isBlank()) t.setStatus(Tarefa.Status.valueOf(s.toUpperCase()));

            if (repoTarefa.atualizar(t)) System.out.println("Atualizado"); else System.out.println("Erro ao atualizar");
        } catch (Exception ex) { System.out.println("Erro: " + ex.getMessage()); }
    }

    private static void removerTarefa() {
        try {
            System.out.print("ID da tarefa a remover: "); int id = Integer.parseInt(sc.nextLine());
            boolean ok = repoTarefa.deletar(id);
            System.out.println(ok ? "Removido" : "Não encontrado/erro");
        } catch (NumberFormatException e) { System.out.println("ID inválido."); }
    }

    // ---------- Relatórios ----------
    private static void menuRelatorios() {
        System.out.println("\n-- Relatórios --");
        System.out.println("1) Tarefas por Status  2) Tarefas vencidas  3) Resumo por Projeto  4) Resumo por Usuário  0) Voltar");
        System.out.print("Escolha: "); String opt = sc.nextLine().trim();
        switch (opt) {
            case "1": relatorioPorStatus(); break;
            case "2": relatorioVencidas(); break;
            case "3": relatorioPorProjeto(); break;
            case "4": relatorioPorUsuario(); break;
            case "0": return;
            default: System.out.println("Inválido");
        }
    }

    private static void relatorioPorStatus() {
        Map<Tarefa.Status, List<Tarefa>> mapa = repoTarefa.agruparPorStatus();
        for (Tarefa.Status st : Tarefa.Status.values()) {
            List<Tarefa> lista = mapa.getOrDefault(st, List.of());
            System.out.printf("%s: %d tarefas%n", st, lista.size());
            lista.forEach(t -> System.out.println("  - " + t.getTitulo() + " (ID:" + t.getId() + ")"));
        }
    }

    private static void relatorioVencidas() {
        List<Tarefa> vencidas = repoTarefa.vencidas(LocalDate.now());
        System.out.println("Tarefas vencidas (hoje = " + LocalDate.now() + "): " + vencidas.size());
        vencidas.forEach(t -> System.out.println("ID:" + t.getId() + " | " + t.getTitulo() + " | Venc:" + t.getDataVencimento()));
    }

    private static void relatorioPorProjeto() {
        Map<Integer, Long> m = repoTarefa.contarPorProjeto();
        System.out.println("Resumo por projeto:");
        for (var e : m.entrySet()) {
            Projeto p = repoProjeto.buscarPorId(e.getKey());
            String nome = p == null ? "n/a" : p.getNome();
            System.out.printf("Projeto ID:%d (%s) -> %d tarefas%n", e.getKey(), nome, e.getValue());
        }
    }

    private static void relatorioPorUsuario() {
        Map<Integer, Long> m = repoTarefa.contarPorUsuario();
        System.out.println("Resumo por usuário:");
        for (var e : m.entrySet()) {
            Usuario u = repoUsuario.buscarPorId(e.getKey());
            String nome = u == null ? "n/a" : u.getNome();
            System.out.printf("Usuario ID:%d (%s) -> %d tarefas%n", e.getKey(), nome, e.getValue());
        }
    }
}
