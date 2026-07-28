package com.template.model;

import com.template.model.dto.CarroDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO {

    public ArrayList<CarroDTO> listarCarro() {
        ArrayList<CarroDTO> listaCarro = new ArrayList<>();
        String sql = "SELECT * FROM carro";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CarroDTO carro = new CarroDTO();
                carro.setId(rs.getInt("id"));
                carro.setNome(rs.getString("nome"));
                carro.setCor(rs.getString("cor"));
                carro.setPt_cavalos(rs.getInt("pt_cavalos"));
                listaCarro.add(carro);
            }
        } catch(SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaCarro;
    }

    public void inserirCarro(CarroDTO carro) {
        String sql = "INSERT INTO carro (nome, pt_cavalos, cor) VALUES (?, ?, ?)";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.executeUpdate();

        } catch(SQLException ex) {
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void excluirCarro(CarroDTO carro) {
        String sql = "DELETE FROM carro WHERE id = ?";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, carro.getId());
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void salvarCarro(CarroDTO carro) {
        String sql = "UPDATE carro SET nome = ?, pt_cavalos = ?, cor = ? WHERE id = ?";

        try (Connection c = new ConexaoBD().conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getId());
            ps.executeUpdate(); // Corrigido para executeUpdate

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

