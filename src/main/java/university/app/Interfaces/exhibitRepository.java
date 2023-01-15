package university.app.Interfaces;

import university.app.dao.Artist;
import university.app.dao.Exhibit;

import java.sql.Date;
import java.util.Collection;


public interface exhibitRepository {
    Collection<Exhibit> findOlderThenDate(Date date);
    Collection<Exhibit> findAll();
    Collection<Exhibit> findAllByArtist(Artist artist);
    Exhibit findById(long id);

    void insert (String name, Date dateofcreation, String type, Artist artist);
    void update (long id, String name, Date dateofcreation, String type, Artist artist);

    void deletebyId (long id);
}
