package com.cesur.dam.data;

import com.cesur.dam.model.Dispositivo;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DispositivoDaoImpl implements DispositivoDAO {

    private final DataSource dataSource;

    public DispositivoDaoImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Dispositivo mapRow(ResultSet rs) throws SQLException {
        return new Dispositivo(
                rs.getLong("id"),
                rs.getString("nombre"),
                rs.getString("categoria"),
                rs.getBoolean("disponible")
        );
    }

    @Override
    public List<Dispositivo> findAll() {
        String sql = "SELECT id, nombre, categoria, disponible FROM Equipo";
        List<Dispositivo> lista = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                lista.add(mapRow(rs));
            }
            return lista;

        } catch (SQLException e) {
            throw new DatabaseException("Error al consultar dispositivos en la BD", e);
        }
    }

    @Override
    public Dispositivo save(Dispositivo entity) {
        String sql = "INSERT INTO Equipo (nombre, categoria, disponible) VALUES (?, ?, ?)";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, entity.getNombre());
            pstmt.setString(2, entity.getCategoria());
            pstmt.setBoolean(3, entity.getDisponible());
            pstmt.executeUpdate();

            try (ResultSet keys = pstmt.getGeneratedKeys()) {
                if (keys.next()) {
                    entity.setId(keys.getLong(1));
                }
            }
            return entity;

        } catch (SQLException e) {
            throw new DatabaseException("Error al insertar dispositivo en la BD", e);
        }
    }

    @Override
    public Optional<Dispositivo> findById(Long id) {
        String sql = "SELECT id, nombre, categoria, disponible FROM Equipo WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? Optional.of(mapRow(rs)) : Optional.empty();
            }

        } catch (SQLException e) {
            throw new DatabaseException("Error al buscar dispositivo por ID", e);
        }
    }

    @Override
    public void update(Dispositivo entity) {
        String sql = "UPDATE Equipo SET nombre = ?, categoria = ?, disponible = ? WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, entity.getNombre());
            pstmt.setString(2, entity.getCategoria());
            pstmt.setBoolean(3, entity.getDisponible());
            pstmt.setLong(4, entity.getId());
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al actualizar dispositivo", e);
        }
    }

    @Override
    public void delete(Long id) {
        String sql = "DELETE FROM Equipo WHERE id = ?";
        try (Connection conn = dataSource.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Error al eliminar dispositivo", e);
        }
    }
}