package university.app.dao;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class artistDAO {
    long id;
    String firstname;
    String secondname;
    String familyname;
    Date dateofbirth;
    String country;
    Date dateofdeath;
}
