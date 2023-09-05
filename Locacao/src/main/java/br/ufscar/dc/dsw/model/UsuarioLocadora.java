package br.ufscar.dc.dsw.model;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "UsuarioLocadora")
public class UsuarioLocadora extends UsuarioGeral{
    // @Id
    private String cidade;

    public UsuarioLocadora(){

    }

    public UsuarioLocadora(String cnpj, String email, int hierarquia, String senha, String username, String cidade){
        super(cnpj, email, hierarquia, senha, username);
        this.setCidade(cidade);
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }
}
