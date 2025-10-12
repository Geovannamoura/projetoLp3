package org.organizador.modelo;

import java.io.Serializable;
import java.util.Objects;

/** Modelo Usuario */
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nome;
    private String email;

    public Usuario() {}
    public Usuario(int id, String nome, String email) {
        this.id = id; this.nome = nome; this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Usuario{id=%d, nome='%s', email='%s'}", id, nome, email);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Usuario)) return false;
        return id == ((Usuario)o).id;
    }
    @Override
    public int hashCode() { return Objects.hash(id); }
}
