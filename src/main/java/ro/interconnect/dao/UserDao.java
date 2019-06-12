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
}
