package university.app.dao;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@Table(name = "artist")
public class artistDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "firstname")
    private String firstname;
    @Column(name = "secondname")
    private String secondname;
    @Column(name = "familyname")
    private String familyname;
    @Column(name = "dateofbirth")
    private Date dateofbirth;
    @Column(name = "country")
    private String country;

    public artistDAO(String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.familyname = familyname;
        this.dateofbirth = dateofbirth;
        this.country = country;
        this.dateofdeath = dateofdeath;
    }

    @Column(name = "dateofdeath")
    private Date dateofdeath;

    public artistDAO() {

    }
}
