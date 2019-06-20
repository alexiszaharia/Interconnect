/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import ro.interconnect.mapper.MapperStire;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.interconnect.db.Stire;

/**
 *
 * @author Alexis
 */
@Repository
public class StireDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperStire mapperStire;

    public List<Stire> getListaStiriPePagina(int nrStart, int nrFinal) {
        String sql = "SELECT * FROM stiri ORDER BY data_publicare DESC";
        List<Stire> listaStiri = new ArrayList<Stire>();
        List<Stire> listaStiriFinal = new ArrayList<Stire>();
        try {
            listaStiri = jdbcTemplate.query(sql, mapperStire);

            for (int i = nrStart - 1; i <= nrFinal - 1; i++) {
                if (i > listaStiri.size() - 1)
                    break;
                listaStiriFinal.add(listaStiri.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaStiriPePagina: " + e.getMessage());
            return new ArrayList<Stire>();
        }

        return listaStiriFinal;
    }
    
    public List<Stire> getListaAnunturi() {
        String sql = "SELECT * FROM stiri WHERE anunt = 1 ORDER BY data_publicare DESC";
        List<Stire> listaStiri = new ArrayList<Stire>();
        try {
            listaStiri = jdbcTemplate.query(sql, mapperStire);
        } catch (Exception e) {
            System.out.println("Eroare getListaAnunturi: " + e.getMessage());
            return new ArrayList<Stire>();
        }

        return listaStiri;
    }

    public Stire getStire(int id) {
        String sql = "SELECT * FROM stiri WHERE id = ?";
        Stire stire = null;

        try {
            stire = jdbcTemplate.queryForObject(sql, mapperStire, id);
        } catch (Exception e) {
            System.out.println("Eroare getStire: " + e.getMessage());
            return null;
        }

        return stire;
    }

    public int getNumarStiri() {
        String sql = "SELECT COUNT(*) FROM stiri";
        int nrStiri;
        try {
            nrStiri = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Eroare getNumarStiri: " + e.getMessage());
            return 0;
        }

        return nrStiri;
    }
    
    public boolean insertStire(Stire stire) {
        String sql = "INSERT INTO stiri (continut_stire, data_publicare, user_publicare, tip_stire, "
                + "preview, titlu_stire, anunt) "
                + "VALUES (?, CURRENT_TIMESTAMP, ?, ?, ?, ?, ?)";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, stire.getContinutStire(), stire.getUserPublicare(), 
                    stire.getTipStire(), stire.getPreviewStire(), stire.getTitluStire(), 
                    stire.getAnunt());
            if (rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare insertStire: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
