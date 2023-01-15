package university.app.repositoty;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import university.app.Interfaces.artistRepository;
import university.app.dao.artistDAO;

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
    public Collection<artistDAO> findOlderThenDate(Date date) {
        TypedQuery<artistDAO> query = em.createQuery("select s from artistDAO s " +
                "where s.dateofbirth > :date", artistDAO.class);
        query.setParameter("date",date);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Collection<artistDAO> findAll(){
        TypedQuery<artistDAO> query = em.createQuery("select s from artistDAO s", artistDAO.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public Collection<artistDAO> findAllByCountry(String country){
        TypedQuery<artistDAO> query = em.createQuery("select s from artistDAO s " +
                "where s.country = :country", artistDAO.class);
        query.setParameter("country",country);
        return query.getResultList();
    }

    @Override
    @Transactional
    public artistDAO findById(long id){
        return em.find(artistDAO.class, id);
    }

    @Override
    @Transactional
    public void insert(String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath) {
        artistDAO artist = new artistDAO(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
        em.persist(artist);
    }

    @Override
    @Transactional
    public void update(long id, String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath){
        artistDAO artist = new artistDAO(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
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
        Query query = em.createQuery("update artistDAO e " +
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
        Query query = em.createQuery("delete from artistDAO s " +
                "where s.id = :id");
        query.setParameter("id",id);
        query.executeUpdate();
    }
}
