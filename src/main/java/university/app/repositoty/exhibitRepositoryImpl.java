package university.app.repositoty;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import university.app.Interfaces.exhibitRepository;
import university.app.dao.Artist;
import university.app.dao.Exhibit;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Collection;
import java.util.GregorianCalendar;


@Repository
@RequiredArgsConstructor
public class exhibitRepositoryImpl implements exhibitRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Collection<Exhibit> findOlderThenDate(Date date) {
        TypedQuery<Exhibit> query = em.createQuery("select s from Exhibit s " +
                "where s.dateofcreation > :date", Exhibit.class);
        query.setParameter("date",date);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Collection<Exhibit> findAll() {
        TypedQuery<Exhibit> query = em.createQuery("select s from Exhibit s", Exhibit.class);
        return query.getResultList();
    }

    @Transactional
    @Override
    public Collection<Exhibit> findAllByArtist(Artist artist) {
        TypedQuery<Exhibit> query = em.createQuery("select s from Exhibit s " +
                "where s.artist = :artist", Exhibit.class);
        query.setParameter("artist",artist);
        return query.getResultList();
    }

    @Override
    public Exhibit findById(long id) {
        return em.find(Exhibit.class, id);
    }

    @Transactional
    @Override
    public void insert(String name, Date dateofcreation, String type, Artist artist) {
        Exhibit exhibit = new Exhibit(name,dateofcreation,type,artist);
        em.persist(exhibit);
    }

    @Transactional
    @Override
    public void update(long id, String name, Date dateofcreation, String type, Artist artist) {
        Exhibit exhibit = new Exhibit(name,dateofcreation,type,artist);
        if ((exhibit.getName()== null)){
            exhibit.setName(findById(id).getName());
        }
        if (exhibit.getDateofcreation().equals((new Date(new GregorianCalendar(9999, 0,31).getTimeInMillis())))){
            exhibit.setDateofcreation(findById(id).getDateofcreation());
        }
        if (exhibit.getType() == null)
            exhibit.setType(findById(id).getType());
        if (exhibit.getArtist() == null){
            exhibit.setArtist(findById(id).getArtist());
        }
        Query query = em.createQuery("update Exhibit e " +
                "set e.name = :name," +
                "e.dateofcreation = :dateofcreation," +
                "e.type = :type_ ," +
                "e.artist = :artist " +
                "where e.id = :id");
        query.setParameter("id",id);
        query.setParameter("name",exhibit.getName());
        query.setParameter("dateofcreation",exhibit.getDateofcreation());
        query.setParameter("type_",exhibit.getType());
        query.setParameter("artist",exhibit.getArtist());
        query.executeUpdate();
    }

    @Transactional
    @Override
    public void deletebyId(long id) {
        Query query = em.createQuery("delete from Exhibit s " +
                "where s.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
