package org.organizador.modelo;

import java.io.Serializable;
import java.util.Objects;

/** Modelo Categoria */
public class Categoria implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;

    public Categoria() {}
    public Categoria(int id, String nome) {
        this.id = id; this.nome = nome;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    @Override
    public String toString() {
        return String.format("Categoria{id=%d, nome='%s'}", id, nome);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categoria)) return false;
        return id == ((Categoria)o).id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
