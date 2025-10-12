package org.organizador.modelo;

import java.io.Serializable;
import java.util.Objects;

/** Modelo Projeto */
public class Projeto implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private String descricao;

    public Projeto() {}
    public Projeto(int id, String nome, String descricao) {
        this.id = id; this.nome = nome; this.descricao = descricao;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    @Override
    public String toString() {
        return String.format("Projeto{id=%d, nome='%s', descricao='%s'}", id, nome, descricao);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Projeto)) return false;
        return id == ((Projeto)o).id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
