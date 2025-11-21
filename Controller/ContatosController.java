package Controller;

import Db.Database;
import Model.Contatos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ContatosController {

    private Contatos mapearResultado(ResultSet rs) throws SQLException {
        return new Contatos(
                rs.getInt("id"),
                rs.getString("nome"),
                rs.getString("telefone"),
                rs.getString("email")
        );
    }

    private void preencherStatement(PreparedStatement stmt, Contatos c, boolean incluirId) throws SQLException {
        stmt.setString(1, c.getNome());
        stmt.setString(2, c.getTelefone());
        stmt.setString(3, c.getEmail());

        if (incluirId) {
            stmt.setInt(4, c.getId());
        }
    }

    public List<Contatos> listarContatos() {
        List<Contatos> contatos = new ArrayList<>();
        String sql = "SELECT * FROM contatos ORDER BY id";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                contatos.add(mapearResultado(rs));
            }

        } catch (SQLException e) {
            System.err.println("Erro ao listar contatos: " + e.getMessage());
        }

        return contatos;
    }

    public boolean adicionarContato(Contatos c) {
        String sql = "INSERT INTO contatos (nome, telefone, email) VALUES (?, ?, ?)";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherStatement(stmt, c, false);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Falha ao adicionar contato: " + e.getMessage());
            return false;
        }
    }

    public boolean editarContato(Contatos c) {
        String sql = "UPDATE contatos SET nome = ?, telefone = ?, email = ? WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            preencherStatement(stmt, c, true);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao editar contato: " + e.getMessage());
            return false;
        }
    }

    public boolean excluirContato(int id) {
        String sql = "DELETE FROM contatos WHERE id = ?";

        try (Connection conn = Database.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            System.err.println("Erro ao excluir contato: " + e.getMessage());
            return false;
        }
    }
}
