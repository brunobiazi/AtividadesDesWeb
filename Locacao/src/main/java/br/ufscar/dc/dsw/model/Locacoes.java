package br.ufscar.dc.dsw.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.tomcat.jni.Local;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "Locacoes")
public class Locacoes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cpf;
    private String cnpj;
    private LocalDateTime horalocacao;
    private LocalDateTime horadevolucao;

    // Constructors, Getters, and Setters

    public Locacoes() {
        // Default constructor (required by JPA)
    }

    public Locacoes(String cpf, String cnpj, String horalocacao, String horadevolucao) {
        this.cpf = cpf;
        this.cnpj = cnpj;
        this.setHoralocacao(horalocacao);
        this.setHoradevolucao(horadevolucao);;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public void setHoralocacao(String horalocacao) {
        // Parse the horalocacao string using DateTimeFormatter and store it as LocalDateTime
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAA");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.horalocacao = LocalDateTime.parse(horalocacao, formatter);
    }
    
    public String getHoralocacao() {
        // Format the LocalDateTime as a string using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(horalocacao);
    }

     public void setHoradevolucao(String horadevolucao) {
        // Parse the horadevolucao string using DateTimeFormatter and store it as LocalDateTime
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        this.horadevolucao = LocalDateTime.parse(horadevolucao, formatter);
    }
    
    public String getHoraDevolucao() {
        // Format the LocalDateTime as a string using DateTimeFormatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return formatter.format(horadevolucao);
    }

}
