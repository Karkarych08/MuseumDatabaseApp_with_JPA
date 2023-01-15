package university.app.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@Table(name = "exhibit")
public class Exhibit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "exhibitname")
    private String name;

    @Column(name = "dateofcreateon")
    private Date dateofcreation;

    @Column(name = "typeofexhibit")
    private String type;

    @ManyToOne(targetEntity = Artist.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "artistid")
    private Artist artist;

    public Exhibit(String name, Date dateofcreation, String type, Artist artist) {
        this.name = name;
        this.dateofcreation = dateofcreation;
        this.type = type;
        this.artist = artist;
    }

}
