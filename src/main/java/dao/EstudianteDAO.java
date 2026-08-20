package dao;

import model.Estudiante;
import util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EstudianteDAO {
    public List<Estudiante> listarTodos() throws SQLException {
        List<Estudiante> estudiantes = new ArrayList<>();
        String sql = "SELECT * FROM estudiante WHERE activo = 1";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Estudiante e = new Estudiante();
                e.setId(rs.getInt("id"));
                e.setRut(rs.getString("rut"));
                e.setNombre(rs.getString("nombre"));
                e.setEmail(rs.getString("email"));
                e.setActivo(rs.getBoolean("activo"));
                estudiantes.add(e);
            }
        }
        return estudiantes;
    }

    /**
     * @return true si el estudiante fue insertado; false si ya existe un estudiante con ese RUT.
     */
    public boolean insertar(Estudiante estudiante) throws SQLException {
        String sql = "INSERT INTO estudiante (rut, nombre, email, activo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estudiante.getRut());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getEmail());
            ps.setBoolean(4, estudiante.isActivo());
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            return false;
        }
    }

    public Estudiante obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM estudiante WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Estudiante e = new Estudiante();
                    e.setId(rs.getInt("id"));
                    e.setRut(rs.getString("rut"));
                    e.setNombre(rs.getString("nombre"));
                    e.setEmail(rs.getString("email"));
                    e.setActivo(rs.getBoolean("activo"));
                    return e;
                }
            }
        }
        return null;
    }

    /**
     * @return true si el estudiante fue actualizado; false si ya existe otro estudiante con ese RUT.
     */
    public boolean actualizar(Estudiante estudiante) throws SQLException {
        String sql = "UPDATE estudiante SET rut = ?, nombre = ?, email = ?, activo = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, estudiante.getRut());
            ps.setString(2, estudiante.getNombre());
            ps.setString(3, estudiante.getEmail());
            ps.setBoolean(4, estudiante.isActivo());
            ps.setInt(5, estudiante.getId());
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            return false;
        }
    }

    /**
     * @return true si el estudiante fue eliminado; false si tiene inscripciones asociadas y no se pudo eliminar.
     */
    public boolean eliminar(int id) throws SQLException {
        String sql = "DELETE FROM estudiante WHERE id = ?";
        try (Connection conn = DatabaseConnection.getInstance();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            return true;
        } catch (SQLIntegrityConstraintViolationException ex) {
            return false;
        }
    }
}
