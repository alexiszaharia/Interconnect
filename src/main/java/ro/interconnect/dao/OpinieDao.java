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
import ro.interconnect.db.Opinie;
import ro.interconnect.mapper.MapperOpinie;

/**
 *
 * @author Alexis
 */
@Repository
public class OpinieDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperOpinie mapperOpinie;
    
    public List<Opinie> getOpiniiInitiativa(int idInitiativa) {
        String sql = "SELECT o.id, o.data, o.comentariu, u.id, u.username, r.role "
                + "FROM opinii o "
                + "JOIN users u ON o.user_opinie = u.id "
                + "JOIN user_roles r ON u.id = r.userid "
                + "WHERE o.initiativa = ? "
                + "ORDER BY o.data ASC";
        List<Opinie> listaOpinii = null;
        
        try {
            listaOpinii = jdbcTemplate.query(sql, mapperOpinie, idInitiativa);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getOpiniiInitiativa(idInitiativa): " 
                    + e.getMessage());
            return new ArrayList<Opinie>();
        }
        
        return listaOpinii;
    }
    
    public boolean adaugareOpinieLaInitiativa(int idUserCurent, int idInitiativa, String comentariu) {
        String sql = "INSERT INTO opinii (user_opinie, initiativa, comentariu) "
                + "VALUES (?, ?, ?)";
        
        try {
            int rows = jdbcTemplate.update(sql, idUserCurent, idInitiativa, comentariu);
            if(rows == 0) {
                return false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda adaugareOpinieLaInitiativa: " 
                    + e.getMessage());
            return false;
        }
        
        return true;
    }
}
