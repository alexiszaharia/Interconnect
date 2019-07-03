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
import ro.interconnect.db.OptiuneReferendum;
import ro.interconnect.mapper.MapperOptiuniReferendum;

/**
 *
 * @author Alexis
 */
@Repository
public class OptiuneReferendumDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperOptiuniReferendum mapperOptiuniReferendum;
    
    public List<OptiuneReferendum> getOptiuni(int idIntrebare) {
        String sql = "SELECT id, text_optiune FROM optiuni "
                + "WHERE id_intrebare = ?";
        List<OptiuneReferendum> listaOptiuni = new ArrayList<>();
        
        try {
            listaOptiuni = jdbcTemplate.query(sql, mapperOptiuniReferendum, idIntrebare);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getOptiuni: " + e.getMessage());
            return new ArrayList<OptiuneReferendum>();
        }
        
        return listaOptiuni;
    }
    
    public boolean insertListaOptiuniReferendum(List<OptiuneReferendum> listaOptiuni, 
            int idIntrebare) {
        String sql = "INSERT INTO optiuni (text_optiune, id_intrebare) "
                + "VALUES (?, ?)";
        boolean ok = true;
        
        try {
            for(OptiuneReferendum optiuneReferendum: listaOptiuni) {
                int rows = jdbcTemplate.update(sql, optiuneReferendum.getTextOptiune(), 
                        idIntrebare);
                if (rows == 0) {
                    ok = false;
                }
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda insertListaOptiuniReferendum: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
