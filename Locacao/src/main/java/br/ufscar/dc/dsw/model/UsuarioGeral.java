package br.ufscar.dc.dsw.model;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.annotation.Generated;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import br.ufscar.dc.dsw.validation.UniqueCPF;

@Entity
@Table(name = "UsuarioGeral")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class UsuarioGeral {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @UniqueCPF (message = "{Unique.usuarioGeral.CPF}")
	@Size(min = 18, max = 18, message = "{Size.usuarioGeral.CPF}")
	@Column(nullable = false, unique = true, length = 60)
    private String cpf_cnpj;
    private String email;
    private int hierarquia;
    private String senha;
    private String username;

    public UsuarioGeral(){

    }

    public UsuarioGeral(String cpf_cnpj, String email, int hierarquia, String senha, String username){
        this.cpf_cnpj = cpf_cnpj;
        this.email = email;
        this.hierarquia = hierarquia;
        this.senha = senha;
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getCpfCnpj() {
        return cpf_cnpj;
    }

    public void setCpfCnpj(String cpf_cnpj) {
        this.cpf_cnpj = cpf_cnpj;
    }

    public int isHierarquia() {
        return hierarquia;
    }

    public void setHierarquia(int hierarquia) {
        this.hierarquia = hierarquia;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
