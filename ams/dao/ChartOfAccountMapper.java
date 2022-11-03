package WEB

-INF.classes.com.ams.dao;

import com.ams.model.ChartOfAccountModel;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;


public class ChartOfAccountMapper
        implements RowMapper<Object> {
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        ChartOfAccountModel customer = new ChartOfAccountModel();
        customer.setFundCode(rs.getString("FUNDCODE"));
        customer.setGlCode(rs.getString("GL_CODE"));
        customer.setDescription(rs.getString("DESCRIPTION"));
        customer.setTransDate(rs.getString("TRANS_DATE"));
        return customer;
    }
}
