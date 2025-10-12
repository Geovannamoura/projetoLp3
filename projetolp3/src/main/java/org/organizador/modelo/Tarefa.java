package org.organizador.modelo;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * Classe modelo que representa uma Tarefa no sistema.
 * Implementa Serializable para poder ser salva e carregada de arquivos binários.
 */
public class Tarefa implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Tarefa(int i, String titulo, String descricao, int idUsuario, int idProjeto, int idCategoria, LocalDate dataVencimento, Status status) {
    }

    // Enum interno representando o status da tarefa
    public enum Status { AFAZER, FAZENDO, FEITO }

    // Enum interno representando a prioridade da tarefa
    public enum Prioridade { BAIXA, MEDIA, ALTA }

    // Atributos principais
    private int id;
    private String titulo;
    private String descricao;
    private int idUsuario;     // ID do responsável (usuário)
    private int idProjeto;     // ID do projeto ao qual pertence
    private int idCategoria;   // ID da categoria
    private Prioridade prioridade; // prioridade da tarefa
    private Status status;         // status da tarefa
    private LocalDate dataCriacao; // data em que foi criada
    private LocalDate dataVencimento; // data limite para concluir

    // Construtor vazio — necessário para serialização e criação sem parâmetros
    public Tarefa() {}

    /**
     * Construtor completo com regras de validação.
     */
    public Tarefa(int id, String titulo, String descricao,
                  int idUsuario, int idProjeto, int idCategoria,
                  Prioridade prioridade, LocalDate dataVencimento, Status status) {

        // ⚠️ Regras de validação — impedem criar tarefa sem dados obrigatórios
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título da tarefa é obrigatório.");
        }
        if (idUsuario <= 0) {
            throw new IllegalArgumentException("O responsável (usuário) é obrigatório.");
        }
        if (idProjeto <= 0) {
            throw new IllegalArgumentException("O projeto é obrigatório.");
        }
        if (dataVencimento == null) {
            throw new IllegalArgumentException("A data de vencimento é obrigatória.");
        }

        // Inicializa os atributos após validações
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.idUsuario = idUsuario;
        this.idProjeto = idProjeto;
        this.idCategoria = idCategoria;
        this.prioridade = prioridade != null ? prioridade : Prioridade.MEDIA; // valor padrão
        this.dataCriacao = LocalDate.now(); // sempre a data de hoje
        this.dataVencimento = dataVencimento;
        this.status = status != null ? status : Status.AFAZER; // padrão inicial
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("O título não pode ser vazio.");
        }
        this.titulo = titulo;
    }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) {
        if (idUsuario <= 0)
            throw new IllegalArgumentException("É obrigatório informar um responsável (usuário).");
        this.idUsuario = idUsuario;
    }

    public int getIdProjeto() { return idProjeto; }
    public void setIdProjeto(int idProjeto) {
        if (idProjeto <= 0)
            throw new IllegalArgumentException("É obrigatório informar um projeto.");
        this.idProjeto = idProjeto;
    }

    public int getIdCategoria() { return idCategoria; }
    public void setIdCategoria(int idCategoria) { this.idCategoria = idCategoria; }

    public Prioridade getPrioridade() { return prioridade; }
    public void setPrioridade(Prioridade prioridade) { this.prioridade = prioridade; }

    public Status getStatus() { return status; }
    public void setStatus(Status status) { this.status = status; }

    public LocalDate getDataCriacao() { return dataCriacao; }
    public void setDataCriacao(LocalDate dataCriacao) { this.dataCriacao = dataCriacao; }

    public LocalDate getDataVencimento() { return dataVencimento; }
    public void setDataVencimento(LocalDate dataVencimento) {
        if (dataVencimento == null)
            throw new IllegalArgumentException("A data de vencimento é obrigatória.");
        this.dataVencimento = dataVencimento;
    }

    @Override
    public String toString() {
        return String.format(
                "Tarefa{id=%d, titulo='%s', usuario=%d, projeto=%d, categoria=%d, prioridade=%s, status=%s, criadaEm=%s, venceEm=%s}",
                id, titulo, idUsuario, idProjeto, idCategoria, prioridade, status,
                dataCriacao, dataVencimento);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tarefa)) return false;
        Tarefa tarefa = (Tarefa) o;
        return id == tarefa.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}