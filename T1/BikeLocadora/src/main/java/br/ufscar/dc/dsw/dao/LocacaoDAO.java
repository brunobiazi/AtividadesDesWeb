package br.ufscar.dc.dsw.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import br.ufscar.dc.dsw.domain.Locacao;

public class LocacaoDAO extends GenericDAO{

    public boolean validarLocacao(Locacao locacao) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = this.getConnection();

            // Verificar se já existe uma locação conflitante
            String sql = "SELECT COUNT(*) FROM Locacao " +
                         "WHERE (locadora_cnpj = ? OR pessoa_cpf = ?) " +
                         "AND data_locacao = ? AND horario_locacao = ?" +
                         "AND ativo = 1";
            
            ps = conn.prepareStatement(sql);
            ps.setLong(1, locacao.getLocadoraCnpj());
            ps.setString(2, locacao.getClienteCpf());
            ps.setString(3, locacao.getDataLocacao());
            ps.setString(4, locacao.getHorarioLocacao());
            rs = ps.executeQuery();
            
            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    // Já existe uma locação conflitante
                    return false;
                }
            }

            // Se não houver conflito, a locação é válida
            return true;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            // Fechar conexões e recursos
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insert(Locacao locacao) {
        String sql = "INSERT INTO Locacao (locadora_cnpj, pessoa_cpf, data_locacao, horario_locacao, ativo)";
        sql += "VALUES (?, ?, ?, ?, ?)";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setLong(1, locacao.getLocadoraCnpj());
            statement.setString(2, locacao.getClienteCpf());
            statement.setString(3, locacao.getDataLocacao());
            statement.setString(4, locacao.getHorarioLocacao());
            statement.setInt(5, locacao.getAtivo());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Locacao> getAll() {
        
        List<Locacao> listaLocacao = new ArrayList<>();

        String sql = "SELECT * FROM Locacao";

        try {
            Connection conn = this.getConnection();
            Statement statement = conn.createStatement();

            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()) {
                Long id = resultSet.getLong("id");
                Long locadoraCnpj = Long.parseLong(resultSet.getString("locadora_cnpj"));
                String clienteCpf = resultSet.getString("pessoa_cpf");
                String dataLocacao = resultSet.getString("data_locacao");
                String horarioLocacao = resultSet.getString("horario_locacao");
                int ativo = resultSet.getInt("ativo");
                

                Locacao locacao = new Locacao(id, locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
                listaLocacao.add(locacao);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocacao;
    }

    public List<Locacao> getPorLocadora(Long locadoraCnpj){
        List<Locacao> listaLocacao = new ArrayList<>();

        String sql = "SELECT * FROM Locacao WHERE locadora_cnpj = ?";

        try{
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, locadoraCnpj);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                String clienteCpf = resultSet.getString("pessoa_cpf");
                String dataLocacao = resultSet.getString("data_locacao");
                String horarioLocacao = resultSet.getString("horario_locacao");
                int ativo = resultSet.getInt("ativo");
                

                Locacao locacao = new Locacao(id, locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
                listaLocacao.add(locacao);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocacao;
    }

    public List<Locacao> getPorCliente(String clienteCpf){
        List<Locacao> listaLocacao = new ArrayList<>();

        String sql = "SELECT * FROM Locacao WHERE pessoa_cpf = ?";

        try{
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, clienteCpf);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Long id = resultSet.getLong("id");
                Long locadoraCnpj = Long.parseLong(resultSet.getString("locadora_cnpj"));
                String dataLocacao = resultSet.getString("data_locacao");
                String horarioLocacao = resultSet.getString("horario_locacao");
                int ativo = resultSet.getInt("ativo");
                

                Locacao locacao = new Locacao(id, locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
                listaLocacao.add(locacao);
            }
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listaLocacao;
    }

    public void delete(Locacao locacao) {
        String sql = "DELETE FROM Locacao where id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, locacao.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sql = "DELETE FROM Imagem WHERE locacao_id = ?";
        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, locacao.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void update(Locacao locacao) {
        String sql = "UPDATE Locacao SET locadora_cnpj = ?, pessoa_cpf = ?, data_locacao = ?, horario_locacao = ?, ativo = ?";
        sql += "WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, locacao.getLocadoraCnpj());
            statement.setString(2, locacao.getClienteCpf());
            statement.setString(3, locacao.getDataLocacao());
            statement.setString(4, locacao.getHorarioLocacao());
            statement.setInt(5, locacao.getAtivo());
            statement.setLong(6, locacao.getId());
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Locacao get(Long id) {
        Locacao locacao = null;

        String sql = "SELECT * from Locacao where id = ? ";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Long locadoraCnpj = Long.parseLong(resultSet.getString("locadora_cnpj"));
                String clienteCpf = resultSet.getString("pessoa_cpf");
                String dataLocacao = resultSet.getString("data_locacao");
                String horarioLocacao = resultSet.getString("horario_locacao");
                int ativo = resultSet.getInt("ativo");


                locacao = new Locacao(id, locadoraCnpj, clienteCpf, dataLocacao, horarioLocacao, ativo);
            }
//Long id, Long locadoraCnpj, String clienteCpf, String dataLocacao, String horarioLocacao, int ativo
            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return locacao;
    }

    public Long getMaxId() {
        Long id = null;

        String sql = "SELECT MAX(id) from Locacao;";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                id = resultSet.getLong("MAX(id)");
            }

            resultSet.close();
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    public void updateAtivo(Long id, int ativo) {
        String sql = "UPDATE Locacao SET ativo = ? WHERE id = ?";

        try {
            Connection conn = this.getConnection();
            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setInt(1, ativo);
            statement.setLong(2, id);
            statement.executeUpdate();

            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
