package br.com.peludinhos.web.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Entidade Cliente.
 *
 * <p>Projeto: PeludinhosMours-Web-UC15</p>
 * <p>Autor: Karol</p>
 * <p>Data: 2025-10-30</p>
 * <p>Versão: 1.0</p>
 */


@Entity
public class Cliente {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String nome;

    @Email
    private String email;

    private String telefone;

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
   
    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
}
