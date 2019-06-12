/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ro.interconnect.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ro.interconnect.db.Initiativa;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Component
public class MapperInitiativa implements RowMapper<Initiativa>{

    @Override
    public Initiativa mapRow(ResultSet rs, int rowNum) throws SQLException {
        Initiativa initiativa = new Initiativa();
        User user = new User();
        initiativa.setId(rs.getInt(1));
        initiativa.setTitluInitiativa(rs.getString(2));
        initiativa.setTextInitiativa(rs.getString(3));
        initiativa.setDataPublicare(rs.getTimestamp(4));
        initiativa.setDataPublicareFormatata();
        
        user.setId(rs.getInt(5));
        user.setUserName(rs.getString(6));
        switch (rs.getString(7)) {
            case "ROLE_ADMINISTRATIE_PUBLICA":
                user.setRole(RoluriUtilizatori.ADMINISTRATIE_PUBLICA);
                break;
            case "ROLE_ADMINISTRATOR":
                user.setRole(RoluriUtilizatori.ADMINISTRATOR);
                break;
            case "ROLE_CETATEAN":
                user.setRole(RoluriUtilizatori.CETATEAN);
                break;
        }
        initiativa.setUserPublicare(user);
        
        return initiativa;
    }
    
}
