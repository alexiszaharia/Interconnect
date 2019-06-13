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
import ro.interconnect.mapper.MapperUser;

/**
 *
 * @author Alexis
 */
@Repository
public class UserDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private MapperUser mapperUser;
    
    public User getUser(int id) {
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role "
                + "FROM users u JOIN user_roles r ON u.id = r.userid "
                + "WHERE u.id = ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, mapperUser, id);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getUser(id): " + e.getMessage());
            return null;
        }
        
        return user;
    }
    
    public User getUser(String username) {
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role "
                + "FROM users u JOIN user_roles r ON u.id = r.userid "
                + "WHERE u.username LIKE ?";
        User user = null;
        try {
            user = jdbcTemplate.queryForObject(sql, mapperUser, username);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getUser(username): " + e.getMessage());
            return null;
        }
        
        return user;
    }
    
    public int getNrUseri(RoluriUtilizatori roluriUtilizatori) {
        String sql = "SELECT COUNT(*) FROM user_roles "
                + "WHERE role LIKE ?";
        int nrUtilizatori = 0;
        
        try {
            nrUtilizatori = jdbcTemplate.queryForObject(sql, Integer.class, roluriUtilizatori.getRol());
        } catch(Exception e) {
            System.out.println("Eroare la metoda getNrUseri(rol): " + e.getMessage());
            return 0;
        }
        
        return nrUtilizatori;
    }
    
    public boolean insertUser(User user) {
        String sql = "INSERT INTO users (username, password, enabled) "
                + "VALUES (?, ?, ?)";
        String sql2 = "INSERT INTO user_roles (userid, role, username) "
                + "VALUES (?, ?, ?)";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), 
                    (user.isEnabled()) ? 1 : 0);
            if (rows == 0) {
                return false;
            }
            
            int idUserGenerat = getIdUser(user.getUserName());
            rows = jdbcTemplate.update(sql2, idUserGenerat, user.getRole().getRol(),
                    user.getUserName());
            if(rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda insertUser: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
    
    public int getIdUser(String username) {
        String sql = "SELECT id FROM users "
                + "WHERE username LIKE ?";
        int id = 0;
        
        try {
            id = jdbcTemplate.queryForObject(sql, Integer.class, username);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getIdUser: " + e.getMessage());
            return 0;
        }
        
        return id;
    }
}
