package dao;

import modelo.Pais;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PaisDAO {

    private final Connection conn;

    public PaisDAO(Connection conn) {
        this.conn = conn;
    }

    public void inserir(Pais pais) {
        String sql = "INSERT INTO pais (nome, capital, populacao, area, pib, pontos_turisticos) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement st = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            st.setString(1, pais.getNome());
            st.setString(2, pais.getCapital());
            st.setLong(3, pais.getPopulacao());
            st.setDouble(4, pais.getArea());
            st.setDouble(5, pais.getPib());
            st.setInt(6, pais.getPontosTuristicos());
            st.executeUpdate();

            try (ResultSet rs = st.getGeneratedKeys()) {
                if (rs.next()) {
                    pais.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao inserir país: " + e.getMessage());
        }
    }

    public void atualizar(Pais pais) {
        String sql = "UPDATE pais SET nome = ?, capital = ?, populacao = ?, area = ?, pib = ?, pontos_turisticos = ? WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setString(1, pais.getNome());
            st.setString(2, pais.getCapital());
            st.setLong(3, pais.getPopulacao());
            st.setDouble(4, pais.getArea());
            st.setDouble(5, pais.getPib());
            st.setInt(6, pais.getPontosTuristicos());
            st.setInt(7, pais.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao atualizar país: " + e.getMessage());
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM pais WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DbException("Erro ao deletar país: " + e.getMessage());
        }
    }

    public Pais buscarPorId(int id) {
        String sql = "SELECT * FROM pais WHERE id = ?";
        try (PreparedStatement st = conn.prepareStatement(sql)) {
            st.setInt(1, id);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return instanciarPais(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            throw new DbException("Erro ao buscar país por ID: " + e.getMessage());
        }
    }

    public List<Pais> listarTodos() {
        String sql = "SELECT * FROM pais ORDER BY nome";
        List<Pais> lista = new ArrayList<>();
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            while (rs.next()) {
                lista.add(instanciarPais(rs));
            }
        } catch (SQLException e) {
            throw new DbException("Erro ao listar países: " + e.getMessage());
        }
        return lista;
    }

    public boolean existeDados() {
        String sql = "SELECT COUNT(*) FROM pais";
        try (PreparedStatement st = conn.prepareStatement(sql);
             ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
            return false;
        } catch (SQLException e) {
            throw new DbException("Erro ao verificar dados da tabela: " + e.getMessage());
        }
    }

    private Pais instanciarPais(ResultSet rs) throws SQLException {
        return new Pais(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("capital"),
                rs.getLong("populacao"),
                rs.getDouble("area"),
                rs.getDouble("pib"),
                rs.getInt("pontos_turisticos")
        );
    }
}
