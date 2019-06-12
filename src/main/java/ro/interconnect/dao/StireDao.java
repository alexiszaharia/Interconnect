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
                listaStiriFinal.add(listaStiri.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaStiriPePagina: " + e.getMessage());
            return new ArrayList<Stire>();
        }

        return listaStiriFinal;
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
}
