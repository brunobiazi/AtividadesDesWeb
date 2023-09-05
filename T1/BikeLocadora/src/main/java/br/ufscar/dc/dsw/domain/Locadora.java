package br.ufscar.dc.dsw.domain;

public class Locadora {

    private Long id;
    private String email;
    private String senha;
    private Long cnpj;
    private String nome;
    private String cidade;

    public Locadora(Long id) {
        this.id = id;
    }

    public Locadora(String email, String senha, Long cnpj, String nome,
            String cidade) {
        this.email = email;
        this.senha = senha;
        this.cnpj = cnpj;
        this.nome = nome;
        this.cidade = cidade;
    }

    public Locadora(Long id, String email, String senha, Long cnpj, String nome,
    String cidade) {
        this(email, senha, cnpj, nome, cidade);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

}
