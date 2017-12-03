/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.dao;

/**
 *
 * @author Mikko
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import tikape.runko.database.Database;
import tikape.runko.domain.AnnosRaakaAine;

public class AnnosRaakaAineDao implements Dao<AnnosRaakaAine, Integer> {

    private Database database;
    private int jarjestys;

    public AnnosRaakaAineDao(Database database) {
        this.database = database;
    }

    @Override
    public AnnosRaakaAine findOne(Integer key) throws SQLException {
        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine WHERE id = ?");
        stmt.setObject(1, key);

        ResultSet rs = stmt.executeQuery();
        boolean hasOne = rs.next();
        if (!hasOne) {
            return null;
        }

        Integer annos_id = rs.getInt("annos_id");
        Integer raaka_aine_id = rs.getInt("raaka_aine_id");
        Integer jarjestys = rs.getInt("jarjestys");
        String maara = rs.getString("maara");
        String ohje = rs.getString("ohje");

        AnnosRaakaAine ara = new AnnosRaakaAine(annos_id, raaka_aine_id, jarjestys, maara, ohje);

        rs.close();
        stmt.close();
        connection.close();

        return ara;
    }

    @Override
    public List<AnnosRaakaAine> findAll() throws SQLException {

        Connection connection = database.getConnection();
        PreparedStatement stmt = connection.prepareStatement("SELECT * FROM AnnosRaakaAine");

        ResultSet rs = stmt.executeQuery();
        List<AnnosRaakaAine> annosRaakaAineet = new ArrayList<>();
        while (rs.next()) {
            Integer annos_id = rs.getInt("annos_id");
            Integer raaka_aine_id = rs.getInt("raaka_aine_id");
            Integer jarjestys = rs.getInt("jarjestys");
            String maara = rs.getString("maara");
            String ohje = rs.getString("ohje");

            annosRaakaAineet.add(new AnnosRaakaAine(annos_id, raaka_aine_id, jarjestys, maara, ohje));
        }

        rs.close();
        stmt.close();
        connection.close();

        return annosRaakaAineet;
    }
    
    
    public List<AnnosRaakaAine> raakaAineetAnnokseen(Integer annosId) throws SQLException {
        
        String query = "SELECT * FROM AnnosRaakaAine WHERE annos_id = ?";
        List<AnnosRaakaAine> aRa = new ArrayList<>();

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setInt(1, annosId);
            ResultSet result = stmt.executeQuery();

            while (result.next()) {
                aRa.add(new AnnosRaakaAine(result.getInt("annos_id"), result.getInt("raaka_aine_id"), result.getInt("jarjestys"), result.getString("maara"), result.getString("ohje")));
            }          
        }
        Collections.sort(aRa, AnnosRaakaAine.araComp);
        return aRa;
    }
        

    @Override
    public AnnosRaakaAine saveOrUpdate(AnnosRaakaAine ara) throws SQLException {

        try (Connection conn = database.getConnection()) {
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO AnnosRaakaAine VALUES (?, ?, ?, ?, ?)");
            stmt.setInt(1, ara.getRaakaAineId());
            stmt.setInt(2, ara.getAnnosId());
            stmt.setInt(3, ara.getJarjestys());
            stmt.setString(3, ara.getMaara());
            stmt.setString(4, ara.getOhje());
            stmt.executeUpdate();
        }

        return null;
    }

    
    @Override
    public void delete(Integer key) throws SQLException {
        Connection conn = database.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM AnnosRaakaAine WHERE id = ?");

        stmt.setInt(1, key);
        stmt.executeUpdate();

        stmt.close();
        conn.close();
    }
    

    @Override
    public int compareTo(AnnosRaakaAine object) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
                 
 
}
