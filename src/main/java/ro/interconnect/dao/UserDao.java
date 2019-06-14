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
    
    public List<User> getListaUseriPePagina(int nrStart, int nrFinal) {
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role "
                + "FROM users u JOIN user_roles r ON u.id = r.userid "
                + "WHERE role LIKE 'ROLE_CETATEAN' OR role LIKE 'ROLE_ADMINISTRATIE_PUBLICA' "
                + "ORDER BY u.username ASC";
        List<User> listaUseri = new ArrayList<User>();
        List<User> listaUseriFinal = new ArrayList<User>();
        try {
            listaUseri = jdbcTemplate.query(sql, mapperUser);

            for (int i = nrStart - 1; i <= nrFinal - 1; i++) {
                if (i > listaUseri.size() - 1)
                    break;
                listaUseriFinal.add(listaUseri.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaUseriPePagina: " + e.getMessage());
            return new ArrayList<User>();
        }

        return listaUseriFinal;
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
    
    public int getNrCetateniAdministratie() {
        String sql = "SELECT COUNT(*) FROM user_roles "
                + "WHERE role LIKE 'ROLE_CETATEAN' OR role LIKE 'ROLE_ADMINISTRATIE_PUBLICA'";
        int nrUtilizatori = 0;
        
        try {
            nrUtilizatori = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch(Exception e) {
            System.out.println("Eroare la metoda getNrCetateniAdministratie: " + e.getMessage());
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
    
    public boolean activareDezactivareUser(int enabled, int idUser) {
        String sql = "UPDATE users "
                + "SET enabled = ? "
                + "WHERE id = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, enabled, idUser);
            if (rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda activareDezactivareUser: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
    
    public boolean updateUserCuParola (User user) {
        String sql = "UPDATE users "
                + "SET username = ?, password = ? "
                + "WHERE id = ?";
        String sql2 = "UPDATE user_roles "
                + "SET username = ?, role = ? "
                + "WHERE userid = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), user.getId());
            if (rows == 0) {
                return false;
            }
            
            rows = jdbcTemplate.update(sql2, user.getUserName(), user.getRole().getRol(), 
                    user.getId());
            if (rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda updateUserCuParola: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
    
    public boolean updateUserFaraParola (User user) {
        String sql = "UPDATE users "
                + "SET username = ? "
                + "WHERE id = ?";
        String sql2 = "UPDATE user_roles "
                + "SET username = ?, role = ? "
                + "WHERE userid = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getId());
            if (rows == 0) {
                return false;
            }
            
            rows = jdbcTemplate.update(sql2, user.getUserName(), user.getRole().getRol(), 
                    user.getId());
            if (rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda updateUserCuParola: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
