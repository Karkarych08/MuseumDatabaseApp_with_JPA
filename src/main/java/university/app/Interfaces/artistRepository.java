package university.app.Interfaces;

import university.app.dao.artistDAO;

import java.sql.SQLException;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

public interface artistRepository {
    Collection<artistDAO> findOlderThenDate(Calendar date);
    Collection<artistDAO> findAll() throws SQLException;
    Collection<artistDAO> findAllByCountry(String country);
    Collection<artistDAO> findById(long id);

    void insert (String firstname, String secondname, String familyname, Calendar dateofbirth, String country, Calendar dateofdeath);

    void update (long id, String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath);

    void deletebyId (long id);
}
