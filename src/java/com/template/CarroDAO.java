package com.template;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CarroDAO {
    Connection c;
    PreparedStatement ps;
    ResultSet rs;

    public ArrayList<CarroDTO> selecionarCarro(){
        rs = null;
        ps = null;
        c = new ConexaoBD().conectaBD();
        try{
            ps = c.prepareStatement("select * from carro");
            rs = ps.executeQuery();

            while(rs.next()){
                //System.out.println("#" + rs.getInt("id")+ " # "+rs.getString("nome"));
            }
        }catch(SQLException ex){
            Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally{
            try{
                if(rs != null) rs.close();
                if(ps != null) ps.close();
                if(c != null) c.close();
            }catch (SQLException ex){
                Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void inserirCarro(CarroDTO carro){
        String sql = "INSERT into carro (nome, pt_cavalos, cor) values (?,?,?)";
        ps = null;
        c = new ConexaoBD().conectaBD();
        try{
            ps = c.prepareStatement(sql);
            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.execute();
        }catch (SQLException e){
            e.printStackTrace();
        }
        finally{
            try{
                if(ps != null) ps.close();
                if(c != null) c.close();
            }catch(SQLException ex){
                Logger.getLogger(CarroDAO.class.getName()).log(Level.SEVERE,null,ex);
            }
        }
    }
    public void excluirCarro(CarroDTO carro) {
        String sql = "DELETE FROM carro WHERE id = ?";

        ps = null;
        c = new ConexaoBD().conectaBD();

        try {
            ps = c.prepareStatement(sql);

            ps.setInt(1, carro.getId());

            ps.executeUpdate(); // mais adequado para DELETE

            //System.out.println("Registro excluído com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    public void atualizarCarro(CarroDTO carro) {
        String sql = "UPDATE carro SET nome = ?, pt_cavalos = ?, cor = ? WHERE id = ?";

        ps = null;
        c = new ConexaoBD().conectaBD();

        try {
            ps = c.prepareStatement(sql);

            ps.setString(1, carro.getNome());
            ps.setInt(2, carro.getPt_cavalos());
            ps.setString(3, carro.getCor());
            ps.setInt(4, carro.getId());

            int linhasAfetadas = ps.executeUpdate();

            if (linhasAfetadas > 0) {
                //System.out.println("Registro atualizado com sucesso!");
            } else {
                //System.out.println("Nenhum registro encontrado com esse ID.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (ps != null) ps.close();
                if (c != null) c.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}
