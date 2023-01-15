package university.app.repositoty;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import university.app.Interfaces.artistRepository;
import university.app.dao.Artist;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.sql.Date;
import java.util.Collection;
import java.util.GregorianCalendar;


@Repository
@RequiredArgsConstructor
public class artistRepositoryImpl implements artistRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Collection<Artist> findOlderThenDate(Date date) {
        TypedQuery<Artist> query = em.createQuery("select s from Artist s " +
                "where s.dateofbirth > :date", Artist.class);
        query.setParameter("date",date);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Collection<Artist> findAll(){
        TypedQuery<Artist> query = em.createQuery("select s from Artist s", Artist.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Collection<Artist> findAllByCountry(String country){
        TypedQuery<Artist> query = em.createQuery("select s from Artist s " +
                "where s.country = :country", Artist.class);
        query.setParameter("country",country);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Artist findById(long id){
        return em.find(Artist.class, id);
    }

    @Override
    @Transactional
    public void insert(String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath) {
        Artist artist = new Artist(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
        em.persist(artist);
    }

    @Override
    @Transactional
    public void update(long id, String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath){
        Artist artist = new Artist(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
        if ((artist.getFirstname()== null)){
            artist.setFirstname(findById(id).getFirstname());
        }
        if (artist.getSecondname() == null){
            artist.setSecondname(findById(id).getSecondname());
        }
        if (artist.getFamilyname() == null){
            artist.setFamilyname(findById(id).getFamilyname());
        }
        if (artist.getDateofbirth().equals((new Date(new GregorianCalendar(9999, 0,31).getTimeInMillis())))){
            artist.setDateofbirth(findById(id).getDateofbirth());
        }
        if (artist.getCountry() == null)
            artist.setCountry(findById(id).getCountry());
        if (artist.getDateofdeath().equals(new Date(new GregorianCalendar(9999, 0,31).getTimeInMillis()))){
            artist.setDateofdeath(findById(id).getDateofdeath());
        }
        Query query = em.createQuery("update Artist e " +
                "set e.firstname = :firstname," +
                "e.secondname = :secondname," +
                "e.familyname = :familyname," +
                "e.dateofbirth = :dateofbirth," +
                "e.dateofdeath = :dateofdeath," +
                "e.country = : country " +
                "where e.id = :id");
        query.setParameter("id",id);
        query.setParameter("firstname",artist.getFirstname());
        query.setParameter("secondname",artist.getSecondname());
        query.setParameter("familyname",artist.getFamilyname());
        query.setParameter("dateofbirth",artist.getDateofbirth());
        query.setParameter("country",artist.getCountry());
        query.setParameter("dateofdeath",artist.getDateofdeath());
        query.executeUpdate();
    }

    @Override
    @Transactional
    public void deletebyId(long id) {
        Query forexhibit = em.createQuery("update Exhibit e set e.artist = null where e.artist.id = :id");
        forexhibit.setParameter("id",id);
        forexhibit.executeUpdate();
        Query query = em.createQuery("delete from Artist s " +
                "where s.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
