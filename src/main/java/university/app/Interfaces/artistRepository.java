package university.app.Interfaces;

import org.springframework.stereotype.Repository;
import university.app.dao.Artist;

import java.sql.Date;
import java.util.Collection;

@Repository
public interface artistRepository {

    Collection<Artist> findOlderThenDate(Date date);
    Collection<Artist> findAll();
    Collection<Artist> findAllByCountry(String country);
    Artist findById(long id);

    void insert (String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath);
    void update (long id, String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath);

    void deletebyId (long id);
}
