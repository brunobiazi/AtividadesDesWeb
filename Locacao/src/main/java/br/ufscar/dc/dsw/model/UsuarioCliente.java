package br.ufscar.dc.dsw.model;
import java.time.LocalDate;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import java.sql.Date;

@Entity
@Table(name = "UsuarioCliente")
public class UsuarioCliente extends UsuarioGeral{

    // @Id
    private String sexo;
    private String telefone;
    @Column(name = "data_de_nascimento")
    private Date dataDeNascimento;

    public UsuarioCliente(){

    }

    public UsuarioCliente(String cpf, String email, int hierarquia, String senha, String username, String sexo, String telefone, String dataDeNascimento){
        super(cpf, email, hierarquia, senha, username);
        this.setSexo(sexo);
        this.setTelefone(telefone);
        this.setDataDeNascimento(dataDeNascimento);
    }


    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public Date getDataDeNascimento() {
        return dataDeNascimento;
    }

    public void setDataDeNascimento(String dataDeNascimento) {
        LocalDate localDate = LocalDate.parse(dataDeNascimento);

        this.dataDeNascimento = Date.valueOf(localDate);
    }
}
