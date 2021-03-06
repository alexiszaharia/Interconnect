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
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role, u.data_ultima_notificare, "
                + "u.nume, u.prenume, u.varsta, u.judet, u.localitate, u.adresa, u.sex "
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
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role, u.data_ultima_notificare, "
                + "u.nume, u.prenume, u.varsta, u.judet, u.localitate, u.adresa, u.sex "
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
    
    public List<User> getListaUseriPePagina(int nrStart, int nrFinal, String continut) {
        String sql = "SELECT u.id, u.username, u.password, u.enabled, r.role, u.data_ultima_notificare, "
                + "u.nume, u.prenume, u.varsta, u.judet, u.localitate, u.adresa, u.sex "
                + "FROM users u JOIN user_roles r ON u.id = r.userid "
                + "WHERE (role LIKE 'ROLE_CETATEAN' OR role LIKE 'ROLE_ADMINISTRATIE_PUBLICA') "
                + "AND u.username LIKE '%" + continut + "%' "
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
    
    public int getNrCetateniAdministratie(String continut) {
        String sql = "SELECT COUNT(*) FROM user_roles "
                + "WHERE (role LIKE 'ROLE_CETATEAN' OR role LIKE 'ROLE_ADMINISTRATIE_PUBLICA') "
                + "AND username LIKE '%" + continut + "%'";
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
        String sql = "INSERT INTO users (username, password, enabled, nume, prenume, varsta, "
                + "judet, localitate, adresa, sex) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String sql2 = "INSERT INTO user_roles (userid, role, username) "
                + "VALUES (?, ?, ?)";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), 
                    (user.isEnabled()) ? 1 : 0, user.getNumePersoana(), user.getPrenumePersoana(), 
                    user.getVarsta(), user.getJudet(), user.getLocalitate(), user.getAdresa(), 
                    user.getSex());
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
                + "SET username = ?, password = ?, nume = ?, prenume = ?, varsta = ?, judet = ?, "
                + "localitate = ?, adresa = ?, sex = ? "
                + "WHERE id = ?";
        String sql2 = "UPDATE user_roles "
                + "SET username = ?, role = ? "
                + "WHERE userid = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getPassword(), 
                    user.getNumePersoana(), user.getPrenumePersoana(), user.getVarsta(), 
                    user.getJudet(), user.getLocalitate(), user.getAdresa(), user.getSex(), 
                    user.getId());
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
                + "SET username = ?, nume = ?, prenume = ?, varsta = ?, judet = ?, "
                + "localitate = ?, adresa = ?, sex = ? "
                + "WHERE id = ?";
        String sql2 = "UPDATE user_roles "
                + "SET username = ?, role = ? "
                + "WHERE userid = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getUserName(), user.getNumePersoana(), 
                    user.getPrenumePersoana(), user.getVarsta(), user.getJudet(), 
                    user.getLocalitate(), user.getAdresa(), user.getSex(), user.getId());
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
    
    public boolean updatePasswordUser (User user, String nouaParola) {
        String sql = "UPDATE users "
                + "SET password = ? "
                + "WHERE id = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, nouaParola, user.getId());
            if (rows == 0) {
                ok = false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda updatePasswordUser: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
    
    public boolean updateUltimaNotificare (User user) {
        String sql = "UPDATE users "
                + "SET data_ultima_notificare = CURRENT_TIMESTAMP "
                + "WHERE id = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, user.getId());
            if (rows == 0) {
                ok = false;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda updateUltimaNotificare: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
    
    public boolean deleteUser(int idUser) {
        String sql = "DELETE FROM user_roles "
                + "WHERE userid = ?";
        String sql2 = "DELETE FROM users "
                + "WHERE id = ?";
        boolean ok = true;
        
        try {
            int rows = jdbcTemplate.update(sql, idUser);
            if (rows == 0) {
                return false;
            }
            
            rows = jdbcTemplate.update(sql2, idUser);
            if(rows == 0) {
                ok = false;
            } else {
                ok = true;
            }
        } catch(Exception e) {
            System.out.println("Eroare la metoda deleteUser: " + e.getMessage());
            return false;
        }
        
        return ok;
    }
}
