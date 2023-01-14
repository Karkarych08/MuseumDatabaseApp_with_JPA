package university.app.repositoty;


import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import university.app.Interfaces.artistRepository;
import university.app.Services.JDBConnect;
import university.app.dao.artistDAO;
import university.app.dao.artistMapper;

import java.sql.Date;
import java.util.Calendar;
import java.util.Collection;
import java.util.Map;


@Repository
@RequiredArgsConstructor
public class artistRepositoryImpl implements artistRepository {


    private final JDBConnect jdbConnect;

    private final artistMapper artistMapper;


    @Override
    public Collection<artistDAO> findOlderThenDate(Calendar date) {
        return jdbConnect.getJdbc().query("SELECT * FROM artist where dateofbirth>:date", Map.of("date",date),artistMapper);
    }

    @Override
    public Collection<artistDAO> findAll(){
        return jdbConnect.getJdbc().query("SELECT * FROM artist", artistMapper);
    }

    @Override
    public Collection<artistDAO> findAllByCountry(String country){
        return jdbConnect.getJdbc().query("SELECT * FROM artist where country=:country", Map.of("country",country),artistMapper);
    }

    @Override
    public Collection<artistDAO> findById(long id){
        return jdbConnect.getJdbc().query("SELECT * FROM artist where id=:id", Map.of("id",id),artistMapper);
    }

    @Override
    public void insert(String firstname, String secondname, String familyname, Calendar dateofbirth, String country, Calendar dateofdeath) {
        KeyHolder kh = new GeneratedKeyHolder();
        MapSqlParameterSource map = new MapSqlParameterSource(Map.of("secondname",secondname,
                "firstname",firstname,
                "familyname",familyname,
                "dateofbirth",new Date(dateofbirth.getTimeInMillis()),
                "country",country,
                "dateofdeath",new Date(dateofdeath.getTimeInMillis())));
        jdbConnect.getJdbc().update("INSERT INTO artist (secondname,firstname,familyname,dateofbirth,country,dateofdeath) " +
                        "VALUES (:secondname,:firstname,:familyname,:dateofbirth,:country,:dateofdeath)",map,kh);
    }

    @Override
    public void update(long id, String firstname, String secondname, String familyname, java.util.Date dateofbirth, String country, java.util.Date dateofdeath){
        MapSqlParameterSource map = new MapSqlParameterSource(Map.of(
                "id",id,
                "secondname",secondname,
                "firstname",firstname,
                "familyname",familyname,
                "dateofbirth",dateofbirth,
                "country",country,
                "dateofdeath",dateofdeath));
        jdbConnect.getJdbc().update("UPDATE artist set (secondname,firstname,familyname,dateofbirth,country,dateofdeath) =" +
                " (:secondname,:firstname,:familyname,:dateofbirth,:country,:dateofdeath) where \"id\"=:id ",map);
    }

    @Override
    public void deletebyId(long id) {
        jdbConnect.getJdbc().update("DELETE FROM artist where \"id\" = :id",Map.of("id",id));
    }
}
