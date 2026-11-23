package com.template.model;

import com.template.model.dto.CarroDTO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Data Access Object (DAO) responsável exclusivamente pela persistência e recuperação de dados de Carro no BD (SRP).
 */
public class CarroDAO {

    private static final Logger LOGGER = Logger.getLogger(CarroDAO.class.getName());

    // Mensagem repassada ao Controller, que a exibe no label de erro (em vermelho)
    private static final String MSG_ERRO_BD = "não foi possível acessar o banco de dados.";
    private final ConexaoBD conexaoBD;
    private static final Logger logger = Logger.getLogger(CarroDAO.class.getName());
    ArrayList<CarroDTO> listaUsuarios = new ArrayList<>();

    public CarroDAO() {
        this.conexaoBD = new ConexaoBD();
    }

    public CarroDAO(ConexaoBD conexaoBD) {
        this.conexaoBD = conexaoBD;
    }

    /**
     * Lista todos os carros cadastrados no banco de dados.
     *
     * @return Lista contendo todos os carros cadastrados
     */
    public ArrayList<CarroDTO> listarCarro() {
        ArrayList<CarroDTO> listaCarro = new ArrayList<>();
        String sql = "SELECT id, nome, pt_cavalos, cor FROM carro ORDER BY id ASC";

        try (Connection c = conexaoBD.conectaBD();
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CarroDTO carro = new CarroDTO();
                carro.setId(rs.getInt("id"));
                carro.setNome(rs.getString("nome"));
                carro.setPt_cavalos(rs.getInt("pt_cavalos"));
                carro.setCor(rs.getString("cor"));
                listaCarro.add(carro);
            }
        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao listar carros", ex);
            throw new RuntimeException(MSG_ERRO_BD, ex);
        }
        return listaCarro;
    }

    /**
     * Insere um novo registro de carro no banco de dados.
     *
     * @param carro Dados do carro a ser inserido
     */
    public void inserirCarro(CarroDTO carro) {
        String sql = "INSERT INTO carro (nome, pt_cavalos, cor) VALUES (?, ?, ?)";

        try (Connection c = conexaoBD.conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao inserir carro: " + carro, ex);
            throw new RuntimeException(MSG_ERRO_BD, ex);
        }
    }

    /**
     * Atualiza os dados de um carro existente no banco de dados.
     *
     * @param carro Objeto CarroDTO contendo os dados atualizados e o ID
     */
    public void salvarCarro(CarroDTO carro) {
        String sql = "UPDATE carro SET nome = ?, pt_cavalos = ?, cor = ? WHERE id = ?";

        try (Connection c = conexaoBD.conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getId());
            ps.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar carro: " + carro, ex);
            throw new RuntimeException(MSG_ERRO_BD, ex);
        }
    }

    /**
     * Exclui um carro do banco de dados pelo ID contido no DTO.
     *
     * @param carro Objeto CarroDTO a ser excluído
     */
    public void excluirCarro(CarroDTO carro) {
        if (carro == null) {
            return;
        }
        excluirCarroPorId(carro.getId());
    }

    /**
     * Exclui um carro do banco de dados a partir do seu identificador único.
     *
     * @param id Identificador do carro
     */
    public void excluirCarroPorId(int id) {
        String sql = "DELETE FROM carro WHERE id = ?";

        try (Connection c = conexaoBD.conectaBD();
             PreparedStatement ps = c.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();

        } catch (SQLException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao excluir carro com ID: " + id, ex);
            throw new RuntimeException(MSG_ERRO_BD, ex);
        }
    }
}
