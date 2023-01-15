package university.app.Interfaces;

import university.app.dao.artistDAO;

import java.sql.Date;
import java.util.Collection;

public interface artistRepository {
    Collection<artistDAO> findOlderThenDate(Date date);
    Collection<artistDAO> findAll();
    Collection<artistDAO> findAllByCountry(String country);
    artistDAO findById(long id);

    void insert (String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath);
    void update (long id, String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath);

    void deletebyId (long id);
}
