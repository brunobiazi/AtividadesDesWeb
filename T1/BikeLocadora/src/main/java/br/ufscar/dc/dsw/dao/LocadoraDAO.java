package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.ufscar.dc.dsw.domain.Locadora;

public class LocadoraDAO extends GenericDAO {

    public void insert(Locadora locadora) {

        String sql = "INSERT INTO Locadora (email, senha, cnpj, nome, cidade) VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, locadora.getEmail());
            statement.setString(2, locadora.getSenha());
            statement.setLong(3, locadora.getCnpj());
            statement.setString(4, locadora.getNome());
            statement.setString(5, locadora.getCidade());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Locadora> getAll() {

        List<Locadora> listaLocadora = new ArrayList<>();

        String sql = "SELECT * FROM Locadora";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                Long cnpj = resultSet.getLong("cnpj");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");

                Locadora locadora = new Locadora(id, email, senha, cnpj, nome, cidade);
                listaLocadora.add(locadora);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocadora;
    }

    public void delete(Locadora locadora) {
        String sql = "DELETE FROM Locadora where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, locadora.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Locadora locadora) {
        String sql = "UPDATE Locadora SET email = ?, senha = ?, cnpj = ?, nome = ?, cidade = ? WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, locadora.getEmail());
            statement.setString(2, locadora.getSenha());
            statement.setLong(3, locadora.getCnpj());
            statement.setString(4, locadora.getNome());
            statement.setString(5, locadora.getCidade());
            statement.setLong(6, locadora.getId());
            
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Locadora get(Long id) {
        Locadora locadora = null;

        String sql = "SELECT * from Locadora where id = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                Long cnpj = resultSet.getLong("cnpj");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, email, senha, cnpj, nome, cidade);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locadora;
    }

    public Locadora getByCnpj(Long cnpj) {
        Locadora locadora = null;

        String sql = "SELECT * from Locadora where cnpj = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, cnpj);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String email = resultSet.getString("email");
                String senha = resultSet.getString("senha");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, email, senha, cnpj, nome, cidade);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locadora;
    }

    public Locadora getByEmail(String email){
        Locadora locadora = null;

        String sql = "SELECT * from Locadora where email = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String senha = resultSet.getString("senha");
                Long cnpj = resultSet.getLong("cnpj");
                String nome = resultSet.getString("nome");
                String cidade = resultSet.getString("cidade");

                locadora = new Locadora(id, email, senha, cnpj, nome, cidade);
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locadora;
    }

    public boolean verificaEmailDuplicado(String email){
        boolean duplicado = false;

        String sql = "SELECT * from Locadora where email = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            duplicado = resultSet.next();
            
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return duplicado;
    }

    public boolean verificaCnpjDuplicado(Long cnpj){
        boolean duplicado = false;

        String sql = "SELECT * from Locadora where cnpj = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, cnpj);
            ResultSet resultSet = statement.executeQuery();
            duplicado = resultSet.next();
            
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return duplicado;
    }
}
