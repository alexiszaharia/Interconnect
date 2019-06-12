/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Alexis
 */
@Repository
public class VoturiInitiativeDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    public int getNrVoturiPozitiveNegative(boolean tipVot, int idInitiativa) {
        String sql = "SELECT COUNT(*) FROM voturi_initiative "
                + "WHERE vot = ? AND id_initiativa = ?";
        int vot = (tipVot) ? 1 : 0;
        int nrVoturi = 0;
        
        try {
            nrVoturi = jdbcTemplate.queryForObject(sql, Integer.class, vot, idInitiativa);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getNrVoturiPozitiveNegative: " + e.getMessage());
            return 0;
        }
        
        return nrVoturi;
    }
    
    public int getModVotareUserPeInitiativa(int idUser, int idInitiativa) {
        String sql = "SELECT vot FROM voturi_initiative "
                + "WHERE id_user = ? AND id_initiativa = ?";
        int modVotare = -1;
        
        try {
            if (jdbcTemplate.queryForObject(sql, Integer.class, idUser, idInitiativa) != null) {
                modVotare = jdbcTemplate.queryForObject(sql, Integer.class, idUser, idInitiativa);
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda getModVotareUserPeInitiativa: " + e.getMessage());
            return -1;
        }
        
        return modVotare;
    }
    
    public boolean adaugareVotLaInitiativa(int idUserCurent, int idInitiativa, int tipVot) {
        String sql = "INSERT INTO voturi_initiative (id_user, id_initiativa, vot) "
                + "VALUES (?, ?, ?)";
        
        try {
            int rows = jdbcTemplate.update(sql, idUserCurent, idInitiativa, tipVot);
            if(rows == 0) {
                return false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda adaugareVotLaInitiativa: " 
                    + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean modificareVotLaInitiativa(int idUserCurent, int idInitiativa, int tipVot) {
        String sql = "UPDATE voturi_initiative "
                + "SET vot = ? "
                + "WHERE id_initiativa = ? AND id_user = ?";
        
        try {
            int rows = jdbcTemplate.update(sql, tipVot, idInitiativa, idUserCurent);
            if(rows == 0) {
                return false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda adaugareVotLaInitiativa: " 
                    + e.getMessage());
            return false;
        }
        
        return true;
    }
    
    public boolean scoatereVotLaInitiativa(int idUserCurent, int idInitiativa) {
        String sql = "DELETE FROM voturi_initiative "
                + "WHERE id_initiativa = ? AND id_user = ?";
        
        try {
            int rows = jdbcTemplate.update(sql, idInitiativa, idUserCurent);
            if(rows == 0) {
                return false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda scoatereVotLaInitiativa: " 
                    + e.getMessage());
            return false;
        }
        
        return true;
    }
}
