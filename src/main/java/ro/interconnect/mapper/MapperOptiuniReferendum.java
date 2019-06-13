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
import ro.interconnect.db.OptiuneReferendum;

/**
 *
 * @author Alexis
 */
@Component
public class MapperOptiuniReferendum implements RowMapper<OptiuneReferendum>{

    @Override
    public OptiuneReferendum mapRow(ResultSet rs, int rowNum) throws SQLException {
        OptiuneReferendum optiuneReferendum = new OptiuneReferendum();
        optiuneReferendum.setIdOptiune(rs.getInt(1));
        optiuneReferendum.setTextOptiune(rs.getString(2));
        
        return optiuneReferendum;
    }
    
}
