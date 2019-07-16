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
import ro.interconnect.db.IntrebareReferendum;

/**
 *
 * @author Alexis
 */
@Component
public class MapperIntrebariReferendum implements RowMapper<IntrebareReferendum>{

    @Override
    public IntrebareReferendum mapRow(ResultSet rs, int rowNum) throws SQLException {
        IntrebareReferendum intrebareReferendum = new IntrebareReferendum();
        intrebareReferendum.setIdIntrebare(rs.getInt(1));
        intrebareReferendum.setTextIntrebare(rs.getString(2));
        
        return intrebareReferendum;
    }
    
}
