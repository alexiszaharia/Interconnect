/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Repository
public class VoturiReferendumDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDao userDao;
    
    public double getTotalVoturiPeReferendum(int idReferendum) {
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_referendum = ?";
        double totalVoturi = 0;
        
        try {
            totalVoturi = jdbcTemplate.queryForObject(sql, Double.class, idReferendum);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getTotalVoturiPeReferendum: " + e.getMessage());
            return 0.0;
        }
        
        return totalVoturi;
    }
    
    public double getProcentVotOptiune(int idOptiune, int idReferendum) {
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_optiune = ? AND id_referendum = ?";
        double totalOptiuni = 0.0;
        double procentVot = 0.0;
        
        try {
            totalOptiuni = jdbcTemplate.queryForObject(sql, Double.class, idOptiune, idReferendum);
            procentVot = totalOptiuni / getTotalVoturiPeReferendum(idReferendum);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getProcentVotOptiune: " + e.getMessage());
            return 0.0;
        }
        
        return procentVot*100;
    }
    
    public double getProcentPrezentaReferendum(int idReferendum) {
        double procentPrezenta = 0.0;
        double totalVoturiReferendum = getTotalVoturiPeReferendum(idReferendum);
        int totalCetateni = userDao.getNrUseri(RoluriUtilizatori.CETATEAN);
        double totalCetateniDouble = Integer.valueOf(totalCetateni).doubleValue();
        
        procentPrezenta = totalVoturiReferendum / totalCetateniDouble;
        
        return procentPrezenta*100;
    }
    
    public boolean insertOptiune(User user, int idReferendum, int idOptiune) {
        String sql = "INSERT INTO optiuni_useri_referendum (id_user, id_referendum, id_optiune) "
                + "VALUES (?, ?, ?)";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getId(), idReferendum, idOptiune);
            if (rows == 0) {
                ok = false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda insertOptiune: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
