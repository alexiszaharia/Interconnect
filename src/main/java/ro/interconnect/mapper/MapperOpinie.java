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
import ro.interconnect.db.Opinie;
import ro.interconnect.db.User;
import ro.interconnect.enums.RoluriUtilizatori;

/**
 *
 * @author Alexis
 */
@Component
public class MapperOpinie implements RowMapper<Opinie> {

    @Override
    public Opinie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Opinie opinie = new Opinie();
        User user = new User();
        opinie.setId(rs.getInt(1));
        opinie.setData(rs.getTimestamp(2));
        opinie.setDataFormatata();
        opinie.setComentariu(rs.getString(3));

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
        opinie.setUserOpinie(user);
        
        return opinie;
    }

}
