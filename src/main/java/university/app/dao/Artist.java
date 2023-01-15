package university.app.dao;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Table(name = "artist")
public class Artist {
    @Getter
    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

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

    @Getter
    @Setter
    @ToString.Exclude
    @OneToMany(mappedBy = "artist", cascade = CascadeType.REMOVE)
    private Set<Exhibit> exhibitss = new LinkedHashSet<>();

    @Column(name = "dateofdeath")
    private Date dateofdeath;


    public Artist(String firstname, String secondname, String familyname, Date dateofbirth, String country, Date dateofdeath) {
        this.firstname = firstname;
        this.secondname = secondname;
        this.familyname = familyname;
        this.dateofbirth = dateofbirth;
        this.country = country;
        this.dateofdeath = dateofdeath;
    }
}
