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
import ro.interconnect.db.Stire;

/**
 *
 * @author Alexis
 */
@Component
public class MapperStire implements RowMapper<Stire> {

    @Override
    public Stire mapRow(ResultSet rs, int rowNum) throws SQLException {
        Stire stire = new Stire();
        stire.setId(rs.getInt(1));
        stire.setContinutStire(rs.getString(2));
        stire.setDataPublicare(rs.getTimestamp(3));
        stire.setUserPublicare(rs.getInt(4));
        stire.setTipStire(rs.getString(5));
        stire.setPreviewStire(rs.getString(6));
        stire.setTitluStire(rs.getString(7));
        stire.setAnunt(rs.getInt(8));
        stire.setDataPublicareFormatata();
        return stire;
    }

}
