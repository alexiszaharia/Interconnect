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
import ro.interconnect.db.Referendum;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Component
public class MapperReferendum implements RowMapper<Referendum>{

    @Override
    public Referendum mapRow(ResultSet rs, int rowNum) throws SQLException {
        Referendum referendum = new Referendum();
        User user = new User();
        referendum.setIdReferendum(rs.getInt(1));
        referendum.setPrezentare(rs.getString(2));
        referendum.setDataReferendum(rs.getDate(3));
        referendum.setDataReferendumFormatata();
        
        user.setId(rs.getInt(4));
        user.setUserName(rs.getString(5));
        switch (rs.getString(6)) {
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
        referendum.setUserCreare(user);
        
        return referendum;
    }
    
}
