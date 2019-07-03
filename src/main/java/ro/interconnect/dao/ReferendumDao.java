/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.dao;

import java.io.File;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
import ro.interconnect.config.ConfigurareDetalii;
import ro.interconnect.db.IntrebareReferendum;
import ro.interconnect.db.Referendum;
import ro.interconnect.db.User;
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
    private IntrebareReferendumDao intrebareReferendumDao;
    @Autowired
    private ConfigurareDetalii configurare;

    public int getNrReferendumuriTrecute() {
        String sql = "SELECT COUNT(*) FROM referendumuri "
                + "WHERE TRUNC(data_referendum) < TRUNC(SYSDATE)";
        int nrReferendumuri = 0;

        try {
            nrReferendumuri = jdbcTemplate.queryForObject(sql, Integer.class);
        } catch (Exception e) {
            System.out.println("Eroare la metoda getNrReferendumuriTrecute");
            return 0;
        }

        return nrReferendumuri;
    }

    public List<Referendum> getListaReferendumuriTrecutePePagina(int nrStart, int nrFinal) {
        String sql = "SELECT r.id, r.prezentare, r.data_referendum, u.id, u.username, ur.role "
                + "FROM referendumuri r "
                + "JOIN users u ON r.user_creare = u.id "
                + "JOIN user_roles ur ON u.id = ur.userid "
                + "WHERE TRUNC(r.data_referendum) < TRUNC(SYSDATE) "
                + "ORDER BY r.data_referendum DESC";
        List<Referendum> listaReferendumuri = new ArrayList<Referendum>();
        List<Referendum> listaReferendumuriFinal = new ArrayList<Referendum>();
        try {
            listaReferendumuri = jdbcTemplate.query(sql, mapperReferendum);

            for (int i = nrStart - 1; i <= nrFinal - 1; i++) {
                if (i > listaReferendumuri.size() - 1) {
                    break;
                }
                listaReferendumuriFinal.add(listaReferendumuri.get(i));
            }
        } catch (Exception e) {
            System.out.println("Eroare getListaReferendumuriTrecutePePagina: " + e.getMessage());
            return new ArrayList<Referendum>();
        }

        return listaReferendumuriFinal;
    }

    public Referendum getReferendum(int id) {
        String sql = "SELECT r.id, r.prezentare, r.data_referendum, u.id, u.username, ur.role "
                + "FROM referendumuri r "
                + "JOIN users u ON r.user_creare = u.id "
                + "JOIN user_roles ur ON u.id = ur.userid "
                + "WHERE r.id = ?";
        Referendum referendum = null;

        try {
            referendum = jdbcTemplate.queryForObject(sql, mapperReferendum, id);
            List<IntrebareReferendum> listaIntrebari = intrebareReferendumDao.getIntrebari(id);
            referendum.setListaIntrebari(listaIntrebari);
        } catch (Exception e) {
            System.out.println("Eroare getReferendum: " + e.getMessage());
            return null;
        }

        return referendum;
    }

    public boolean insertReferendum(Referendum referendum, MultipartFile[] files) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        String sql = "INSERT INTO referendumuri (prezentare, data_referendum, user_creare) "
                + "VALUES (?, TO_DATE(?, 'dd.MM.yyyy'), ?)";
        boolean ok = true;

        try {
            int rows = jdbcTemplate.update((con) -> {
                PreparedStatement ps = con.prepareStatement(sql, new String[]{"ID"});
                ps.setString(1, referendum.getPrezentare());
                ps.setString(2, referendum.getDataReferendumFormatata());
                ps.setInt(3, referendum.getUserCreare().getId());
                return ps;
            }, keyHolder);
            
            if (rows == 0) {
                return false;
            }
            
            ok = intrebareReferendumDao.insertListaIntrebariReferendum(
                    referendum.getListaIntrebari(), keyHolder.getKey().intValue());
            
            
            if (files != null && files.length != 0) {
                int idReferendum = keyHolder.getKey().intValue();

                if (idReferendum != 0) {
                    File director = new File(configurare.getCaleFisiere() + "\\referendum\\" + idReferendum);
                    director.mkdirs();

                    for (MultipartFile fileUpload : files) {
                        File fisierDestinatie = new File(configurare.getCaleFisiere() + "\\referendum\\"
                                + idReferendum + "\\" + fileUpload.getOriginalFilename());
                        try {
                            fileUpload.transferTo(fisierDestinatie);
                        } catch (IOException ex) {
                            ok = false;
                        }
                    }

                } else {
                    ok = false;
                }
            }        
        } catch (Exception e) {
            System.out.println("Eroare insertReferendum: " + e.getMessage());
            return false;
        }

        return ok;
    }

    public int getIdReferendum(String prezentare) {
        String sql = "SELECT id FROM referendumuri "
                + "WHERE prezentare LIKE ?";
        int idRef = 0;

        try {
            idRef = jdbcTemplate.queryForObject(sql, Integer.class, prezentare);
        } catch (Exception e) {
            System.out.println("Eroare getIdReferendum: " + e.getMessage());
            return 0;
        }

        return idRef;
    }

    public boolean verificareReferendumActiv() {
        String sql = "SELECT COUNT(*) FROM referendumuri "
                + "WHERE TRUNC(data_referendum) = TRUNC(SYSDATE)";
        int nr = 0;
        boolean exista = false;

        try {
            nr = jdbcTemplate.queryForObject(sql, Integer.class);
            if (nr > 0) {
                exista = true;
            }
        } catch (Exception e) {
            System.out.println("Eroare verificareReferendumActiv: " + e.getMessage());
            return false;
        }

        return exista;
    }

    public Referendum getReferendumActiv() {
        String sql = "SELECT r.id, r.prezentare, r.data_referendum, u.id, u.username, ur.role "
                + "FROM referendumuri r "
                + "JOIN users u ON r.user_creare = u.id "
                + "JOIN user_roles ur ON u.id = ur.userid "
                + "WHERE TRUNC(data_referendum) = TRUNC(SYSDATE)";
        Referendum referendum = null;

        try {
            referendum = jdbcTemplate.queryForObject(sql, mapperReferendum);
            List<IntrebareReferendum> listaIntrebari = intrebareReferendumDao.getIntrebari(
                    referendum.getIdReferendum());
            referendum.setListaIntrebari(listaIntrebari);
        } catch (Exception e) {
            System.out.println("Eroare getReferendumActiv: " + e.getMessage());
            return null;
        }

        return referendum;
    }

    public boolean referendumVotatDeUtilizator(User user, Referendum referendum) {
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_referendum = ? AND id_user = ?";
        boolean votat = true;
        int nr = 1;

        try {
            nr = jdbcTemplate.queryForObject(sql, Integer.class,
                    referendum.getIdReferendum(), user.getId());
            if (nr == 0) {
                votat = false;
            } else {
                votat = true;
            }
        } catch (Exception e) {
            System.out.println("Eroare referendumVotatDeUtilizator: " + e.getMessage());
            return true;
        }

        return votat;
    }
    
    public boolean referendumVotatDeUtilizator(User user, int idReferendum) {
        String sql = "SELECT COUNT(*) FROM optiuni_useri_referendum "
                + "WHERE id_referendum = ? AND id_user = ?";
        boolean votat = true;
        int nr = 1;

        try {
            nr = jdbcTemplate.queryForObject(sql, Integer.class,
                    idReferendum, user.getId());
            if (nr == 0) {
                votat = false;
            } else {
                votat = true;
            }
        } catch (Exception e) {
            System.out.println("Eroare referendumVotatDeUtilizator: " + e.getMessage());
            return true;
        }

        return votat;
    }
    
    public boolean existaReferendum(String data) {
        String sql = "SELECT COUNT(*) FROM referendumuri "
                + "WHERE data_referendum = TO_DATE(?, 'dd.MM.yyyy')";
        boolean exista = true;
        int nr = 1;

        try {
            nr = jdbcTemplate.queryForObject(sql, Integer.class, data);
            if (nr == 0) {
                exista = false;
            } else {
                exista = true;
            }
        } catch (Exception e) {
            System.out.println("Eroare existaReferendum: " + e.getMessage());
            return true;
        }

        return exista;
    }
}
