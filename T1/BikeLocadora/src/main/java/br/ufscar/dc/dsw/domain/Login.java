package br.ufscar.dc.dsw.domain;

public class Login {
    private Locadora locadora;
    private Cliente cliente;
    private String tipo;

    public Login(Locadora locadora, Cliente cliente, String tipo) {
        this.locadora = locadora;
        this.cliente = cliente;
        this.tipo = tipo;
    }

    public Locadora getLocadora() {
        return locadora;
    }

    public void setLocadora(Locadora locadora) {
        this.locadora = locadora;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
