/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ro.interconnect.db.IntrebareReferendum;
import ro.interconnect.mapper.MapperIntrebariReferendum;

/**
 *
 * @author Alexis
 */
@Repository
public class IntrebareReferendumDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperIntrebariReferendum mapperIntrebariReferendum;
    @Autowired
    private OptiuneReferendumDao optiuneReferendumDao;
    
    public List<IntrebareReferendum> getIntrebari(int idReferendum) {
        String sql = "SELECT id, text_intrebare FROM intrebari "
                + "WHERE id_referendum = ?";
        List<IntrebareReferendum> listaIntrebari = new ArrayList<>();
        
        try {
            listaIntrebari = jdbcTemplate.query(sql, mapperIntrebariReferendum, idReferendum);
            
            for (IntrebareReferendum intrebareReferendum: listaIntrebari) {
                intrebareReferendum.setListaOptiuniReferendum(
                        optiuneReferendumDao.getOptiuni(intrebareReferendum.getIdIntrebare()));
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda getIntrebari: " + e.getMessage());
            return new ArrayList<IntrebareReferendum>();
        }
        
        return listaIntrebari;
    }
    
    public boolean insertListaIntrebariReferendum(List<IntrebareReferendum> listaIntrebari, 
            int idReferendum) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO intrebari (text_intrebare, id_referendum) "
                + "VALUES (?, ?)";
        boolean ok = true;
        
        try {
            for(IntrebareReferendum intrebareReferendum: listaIntrebari) {
                int rows = jdbcTemplate.update((con) -> {
                    PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
                    ps.setString(1, intrebareReferendum.getTextIntrebare());
                    ps.setInt(2, idReferendum);
                    return ps;
                }, keyHolder);
                
                if (rows == 0) {
                    ok = false;
                } else {
                    optiuneReferendumDao.insertListaOptiuniReferendum(
                            intrebareReferendum.getListaOptiuniReferendum(), 
                            keyHolder.getKey().intValue());
                }
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda insertListaIntrebariReferendum: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
