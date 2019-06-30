/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.interconnect.db.Initiativa;
import ro.interconnect.enums.RoluriUtilizatori;
import ro.interconnect.mapper.MapperInitiativa;

/**
 *
 * @author Alexis
 */
@Repository
public class InitiativaDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperInitiativa mapperInitiativa;
    
    public List<Initiativa> getListaInitiativePePagina(int nrStart, int nrFinal, RoluriUtilizatori roluriUtilizatori) {
        String sql = "SELECT i.id, i.titlu_initiativa, i.text_initiativa, i.data_publicare, "
                + "u.id, u.username, r.role "
                + "FROM initiative i "
                + "JOIN users u ON i.user_publicare = u.id "
                + "JOIN user_roles r ON u.id = r.userid "
                + "WHERE r.role LIKE ? "
                + "ORDER BY i.data_publicare DESC";
        List<Initiativa> listaInitiative = new ArrayList<Initiativa>();
        List<Initiativa> listaInitiativeFinal = new ArrayList<Initiativa>();
        try {
            listaInitiative = jdbcTemplate.query(sql, mapperInitiativa, roluriUtilizatori.getRol());

            for (int i = nrStart - 1; i <= nrFinal - 1; i++) {
                if (i > listaInitiative.size() - 1)
                    break;
                listaInitiativeFinal.add(listaInitiative.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaInitiativePePagina: " + e.getMessage());
            return new ArrayList<Initiativa>();
        }

        return listaInitiativeFinal;
    }

    public Initiativa getInitiativa(int id) {
        String sql = "SELECT i.id, i.titlu_initiativa, i.text_initiativa, i.data_publicare, "
                + "u.id, u.username, r.role "
                + "FROM initiative i "
                + "JOIN users u ON i.user_publicare = u.id "
                + "JOIN user_roles r ON u.id = r.userid "
                + "WHERE i.id = ?";
        Initiativa initiativa = null;

        try {
            initiativa = jdbcTemplate.queryForObject(sql, mapperInitiativa, id);
        } catch (Exception e) {
            System.out.println("Eroare getInitiativa: " + e.getMessage());
            return null;
        }

        return initiativa;
    }
    
    public int getIdInitiativa(String titlu, String continut) {
        String sql = "SELECT id "
                + "FROM initiative "
                + "WHERE titlu_initiativa LIKE ? AND text_initiativa LIKE ?";
        int id = 0;
        
        try {
            id = jdbcTemplate.queryForObject(sql, Integer.class, titlu, continut);
        } catch (Exception e) {
            System.out.println("Eroare getIdInitiativa: " + e.getMessage());
            return 0;
        }
        
        return id;
    }

    public int getNumarInitiative(RoluriUtilizatori roluriUtilizatori) {
        String sql = "SELECT COUNT(*) FROM initiative i "
                + "JOIN users u ON i.user_publicare = u.id "
                + "JOIN user_roles r ON u.id = r.userid "
                + "WHERE r.role LIKE ?";
        int nrInitiative;
        try {
            nrInitiative = jdbcTemplate.queryForObject(sql, Integer.class, roluriUtilizatori.getRol());
        } catch (Exception e) {
            System.out.println("Eroare getNumarInitiative: " + e.getMessage());
            return 0;
        }

        return nrInitiative;
    }
    
    public boolean adaugareInitiativa(int idUserPublicare, String titlu, String continut) {
        String sql = "INSERT INTO initiative (user_publicare, titlu_initiativa, text_initiativa, "
                + "data_publicare) "
                + "VALUES (?, ?, ?, CURRENT_TIMESTAMP)";
        boolean ok = false;
        
        try {
            int rows = jdbcTemplate.update(sql, idUserPublicare, titlu, continut);
            if (rows != 0) {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare adaugareInitiativa: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
