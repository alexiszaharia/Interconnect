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
import ro.interconnect.db.Referendum;
import ro.interconnect.mapper.MapperReferendum;

/**
 *
 * @author Alexis
 */
@Repository
public class ReferendumDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperReferendum mapperReferendum;
    @Autowired
    private OptiuneReferendumDao optiuneReferendumDao;
    
    public int getNrReferendumuriTrecute() {
        String sql = "SELECT COUNT(*) FROM referendumuri "
                + "WHERE data_referendum < CURRENT_TIMESTAMP";
        int nrReferendumuri = 0;
        
        try {
            nrReferendumuri = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getNrReferendumuriTrecute");
            return 0;
        }
        
        return nrReferendumuri;
    }
    
    public List<Referendum> getListaReferendumuriTrecutePePagina(int nrStart, int nrFinal) {
        String sql = "SELECT r.id, r.intrebare, r.data_referendum, u.id, u.username, ur.role "
                + "FROM referendumuri r "
                + "JOIN users u ON r.user_creare = u.id "
                + "JOIN user_roles ur ON u.id = ur.userid "
                + "WHERE r.data_referendum < CURRENT_DATE "
                + "ORDER BY r.data_referendum DESC";
        List<Referendum> listaReferendumuri = new ArrayList<Referendum>();
        List<Referendum> listaReferendumuriFinal = new ArrayList<Referendum>();
        try {
            listaReferendumuri = jdbcTemplate.query(sql, mapperReferendum);

            for (int i = nrStart - 1; i <= nrFinal - 1; i++) {
                listaReferendumuriFinal.add(listaReferendumuri.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaReferendumuriTrecutePePagina: " + e.getMessage());
            return new ArrayList<Referendum>();
        }

        return listaReferendumuriFinal;
    }
    
    public Referendum getReferendum(int id) {
        String sql = "SELECT r.id, r.intrebare, r.data_referendum, u.id, u.username, ur.role "
                + "FROM referendumuri r "
                + "JOIN users u ON r.user_creare = u.id "
                + "JOIN user_roles ur ON u.id = ur.userid "
                + "WHERE r.id = ?";
        Referendum referendum = null;

        try {
            referendum = jdbcTemplate.queryForObject(sql, mapperReferendum, id);
            List<OptiuneReferendum> listaOptiuni = optiuneReferendumDao.getOptiuni(id);
            referendum.setListaOptiuni(listaOptiuni);
        } catch (Exception e) {
            System.out.println("Eroare getReferendum: " + e.getMessage());
            return null;
        }

        return referendum;
    }
}
