package br.ufscar.dc.dsw.domain;

public class Locacao {
    
    private Long id;
    private Long locadoraCnpj;
    private String clienteCpf;
    private String dataLocacao;
    private String horarioLocacao;
    private int ativo;

    public Locacao(Long id) {
        this.id = id;
    }

    public Locacao(Long locadoraCnpj, String clienteCpf, String dataLocacao, String horarioLocacao, int ativo) {
        this.locadoraCnpj  = locadoraCnpj;
        this.clienteCpf = clienteCpf;
        this.dataLocacao = dataLocacao;
        this.horarioLocacao = horarioLocacao;
        this.ativo = ativo;
    }

    public Locacao(Long id, Long locadoraCnpj, String clienteCpf, String dataLocacao, String horarioLocacao, int ativo){
        this(locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
        this.id = id;
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Long getLocadoraCnpj(){
        return locadoraCnpj;
    }

    public void setLocadoraCnpj(Long locadoraCnpj){
        this.locadoraCnpj = locadoraCnpj;
    }

    public String getClienteCpf(){
        return clienteCpf;
    }

    public void setClienteCpf(String clienteCpf){
        this.clienteCpf = clienteCpf;
    }

    public String getDataLocacao(){
        return dataLocacao;
    }

    public void setDataLocacao(String dataLocacao){
        this.dataLocacao = dataLocacao;
    }

    public String getHorarioLocacao() {
        return horarioLocacao;
    }

    public void setHorarioLocacao(String horarioLocacao) {
        this.horarioLocacao = horarioLocacao;
    }


    public int getAtivo(){
        return ativo;
    }

    public void setAtivo(int ativo){
        this.ativo = ativo;
    }

}
