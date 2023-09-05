package br.ufscar.dc.dsw.domain;

public class Cliente {

    private Long id;
    private String email;
    private String nome;
    private String senha;
    private String cpf;
    private String telefone;
    private String sexo;
    private String nascimento;
    private int admin;

    public Cliente(Long id) {
        this.id = id;
    }

    public Cliente(String email, String nome, String senha, String cpf, String telefone,
            String sexo, String nascimento, int admin) {
        this.email = email;
        this.nome = nome;
        this.senha = senha;
        this.cpf = cpf;
        this.telefone = telefone;
        this.sexo = sexo;
        this.nascimento = nascimento;
        this.admin = admin;
    }

    public Cliente(Long id, String email, String nome, String senha, String cpf, String telefone,
    String sexo, String nascimento, int admin) {
        this(email, nome, senha, cpf, telefone, sexo, nascimento, admin);
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getNascimento() {
        return nascimento;
    }

    public void setNascimento(String nascimento) {
        this.nascimento = nascimento;
    }

    public int getAdmin() {
        return admin;
    }

    public void setNascimento(int admin) {
        this.admin = admin;
    }
}
