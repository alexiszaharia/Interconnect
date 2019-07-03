/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import java.text.DecimalFormat;
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
    
    public double getTotalParticiparePeReferendum(int idReferendum) {
        String sql = "SELECT COUNT(*) FROM " 
                + "(SELECT DISTINCT id_referendum, id_user FROM optiuni_useri_referendum "
                + "WHERE id_referendum = ?)";
        double totalVoturi = 0;
        
        try {
            totalVoturi = jdbcTemplate.queryForObject(sql, Double.class, idReferendum);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getTotalParticiparePeReferendum: " + e.getMessage());
            return 0.0;
        }
        
        return totalVoturi;
    }
    
    public double getTotalVoturiPeIntrebare(int idReferendum, int idIntrebare) {
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_referendum = ? AND id_intrebare = ?";
        double totalVoturi = 0;
        
        try {
            totalVoturi = jdbcTemplate.queryForObject(sql, Double.class, idReferendum, idIntrebare);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getTotalVoturiPeIntrebare: " + e.getMessage());
            return 0.0;
        }
        
        return totalVoturi;
    }
    
    public double getProcentVotOptiune(int idOptiune, int idReferendum, int idIntrebare) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_optiune = ? AND id_referendum = ? AND id_intrebare = ?";
        double totalOptiuni = 0.0;
        double totalVoturiPeIntrebare = 0.0;
        double procentVot = 0.0;
        
        try {
            totalOptiuni = jdbcTemplate.queryForObject(sql, Double.class, idOptiune, idReferendum, 
                    idIntrebare);
            totalVoturiPeIntrebare = getTotalVoturiPeIntrebare(idReferendum, idIntrebare);
            if (totalVoturiPeIntrebare != 0) {
                procentVot = totalOptiuni / totalVoturiPeIntrebare;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda getProcentVotOptiune: " + e.getMessage());
            return 0.0;
        }
        
        return Double.valueOf(decimalFormat.format(procentVot*100));
    }
    
    public double getProcentPrezentaReferendum(int idReferendum) {
        DecimalFormat decimalFormat = new DecimalFormat("#.00");
        
        double procentPrezenta = 0.0;
        double totalPrezentaReferendum = getTotalParticiparePeReferendum(idReferendum);
        int totalCetateni = userDao.getNrUseri(RoluriUtilizatori.CETATEAN);
        double totalCetateniDouble = Integer.valueOf(totalCetateni).doubleValue();
        
        procentPrezenta = totalPrezentaReferendum / totalCetateniDouble;
        
        return Double.valueOf(decimalFormat.format(procentPrezenta*100));
    }
    
    public boolean insertOptiune(User user, int idReferendum, int idOptiune, int idIntrebare) {
        String sql = "INSERT INTO optiuni_useri_referendum (id_user, id_referendum, id_optiune, id_intrebare) "
                + "VALUES (?, ?, ?, ?)";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getId(), idReferendum, idOptiune, idIntrebare);
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
