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
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Component
public class MapperUser implements RowMapper<User>{

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt(1));
        user.setUserName(rs.getString(2));
        user.setPassword(rs.getString(3));
        user.setEnabled(rs.getInt(4) != 0);
        switch (rs.getString(5)) {
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
        
        return user;
    }
    
}
