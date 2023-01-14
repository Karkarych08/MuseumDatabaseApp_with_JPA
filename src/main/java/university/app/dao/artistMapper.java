package university.app.dao;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

@Component
public class artistMapper implements RowMapper<artistDAO> {

    @Override
    public artistDAO mapRow (ResultSet resultset, int i) throws SQLException {
        Integer id = resultset.getInt("id");
        String firstname = resultset.getString("firstname");
        String secondname = resultset.getString("secondname");
        String familyname = resultset.getString("familyname");
        Date dateofbirth = resultset.getDate("dateofbirth");
        String country = resultset.getString("country");
        Date dateofdeath = resultset.getDate("dateofbirth");
        return new artistDAO(id,firstname,secondname,familyname,dateofbirth,country,dateofdeath);
    }
}
