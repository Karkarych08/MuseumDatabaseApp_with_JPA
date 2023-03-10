package university.app.Services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import university.app.Interfaces.Locale_If;
import university.app.Interfaces.artistRepository;
import university.app.Interfaces.exhibitRepository;
import university.app.Services.Exceptions.LocaleNotSupportedException;
import university.app.dao.Artist;
import university.app.dao.Exhibit;

import java.sql.Date;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class UserInterface {

    private final artistRepository artistService;

    private final exhibitRepository exhibitService;

    private final Massage_localization message;
    private final Locale_If locale;
    private final Scanner in = new Scanner(System.in);

    @ShellMethod("Find all persons")
    public void findAll(@ShellOption(defaultValue = "all") String entity) {
        switch (entity) {
            case "artist" -> {
                for (Artist artist : artistService.findAll()) {
                    System.out.println(artist);
                }
            }
            case "exhibit" -> {
                for (Exhibit exhibit : exhibitService.findAll()) {
                    System.out.println(exhibit);
                }
            }
            case  "all" ->{
                for (Artist artist : artistService.findAll()) {
                    System.out.println(artist);
                }
                for (Exhibit exhibit : exhibitService.findAll()) {
                    System.out.println(exhibit);
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Find by parameter")
    public void find(@ShellOption(defaultValue = "artist") String entity,
                     @ShellOption(defaultValue = "id") @NotNull String parameter){
        switch (entity) {
            case "artist" -> {
                switch (parameter) {
                    case "country" -> {
                        System.out.print(message.localize("countryENTER"));
                        String country = in.next();
                        if (!Objects.equals(artistService.findAllByCountry(country).toString(), "[]"))
                            for (Artist artist : artistService.findAllByCountry((country))) {
                                System.out.println(artist);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "date" -> {
                        System.out.print(message.localize("dateENTER"));
                        Date date = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                        if (!Objects.equals(artistService.findOlderThenDate(date).toString(), "[]"))
                            for (Artist artist : artistService.findOlderThenDate(date)) {
                                System.out.println(artist);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "id" -> {
                        System.out.print(message.localize("EnterID"));
                        long id = in.nextLong();
                        if (!Objects.equals(artistService.findById(id).toString(), "[]"))
                            System.out.println(artistService.findById(id).toString());
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    default -> System.out.println(message.localize("defaultFindByMSG"));
                }
            }
            case "exhibit" ->{
                switch (parameter) {
                    case "artist" -> {
                        System.out.print(message.localize("EnterArtistID"));
                        long id = in.nextLong();
                        Artist artist = artistService.findById(id);
                        exhibitService.findAllByArtist(artist);
                        if (!Objects.equals(exhibitService.findAllByArtist(artist).toString(), "[]"))
                            for (Exhibit exhibit : exhibitService.findAllByArtist(artist)) {
                                System.out.println(exhibit);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "date" -> {
                        System.out.print(message.localize("dateENTER"));
                        Date date = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                        if (!Objects.equals(exhibitService.findOlderThenDate(date).toString(), "[]"))
                            for (Exhibit exhibit : exhibitService.findOlderThenDate(date)) {
                                System.out.println(exhibit);
                            }
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    case "id" -> {
                        System.out.print(message.localize("EnterID"));
                        long id = in.nextLong();
                        if (!Objects.equals(exhibitService.findById(id).toString(), "[]"))
                            System.out.println(exhibitService.findById(id).toString());
                        else System.out.println(message.localize("NoMatchingData"));
                    }
                    default -> System.out.println(message.localize("defaultFindByMSG"));
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Change Language")
    public void changeLanguage(){
        System.out.print(message.localize("ChangeLanguageMSG"));
        try {
            locale.set(in.next().toLowerCase());
        }catch (LocaleNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @ShellMethod("Insert")
    public void insert(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption(defaultValue = "null") String firstname,
                       @ShellOption(defaultValue = "null") String secondname,
                       @ShellOption(defaultValue = "null") String familyname,
                       @ShellOption(defaultValue = "null") Integer dayofbirth,
                       @ShellOption(defaultValue = "null") Integer mounthofbirth,
                       @ShellOption(defaultValue = "null") Integer yearofbirth,
                       @ShellOption(defaultValue = "null") String country,
                       @ShellOption(defaultValue = "null") Integer dayofdeath,
                       @ShellOption(defaultValue = "null") Integer mounthofdeath,
                       @ShellOption(defaultValue = "null") Integer yearofdeath,
                       @ShellOption(defaultValue = "null") Integer dayofcreation,
                       @ShellOption(defaultValue = "null") Integer mounthofcreation,
                       @ShellOption(defaultValue = "null") String name,
                       @ShellOption(defaultValue = "null") String type,
                       @ShellOption(defaultValue = "null") Integer yearofcreation,
                       @ShellOption(defaultValue = "0") long artist){
        switch (entity) {
            case "artist" -> {
                Date datebirth = new Date(new GregorianCalendar(yearofbirth, mounthofbirth, dayofbirth).getTimeInMillis());
                Date datedeath = new Date(new GregorianCalendar(yearofdeath, mounthofdeath, dayofdeath).getTimeInMillis());
                try {
                    artistService.insert(firstname, secondname, familyname, datebirth, country, datedeath);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                }
            }
            case "exhibit" -> {
                Date datecreation = new Date(new GregorianCalendar(yearofcreation, mounthofcreation, dayofcreation).getTimeInMillis());
                Artist artist_for_insert = null;
                if (artist!=0)
                    artist_for_insert = artistService.findById(artist);
                try {
                    exhibitService.insert(name,datecreation,type,artist_for_insert);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Insert artist manually")
    public void insertManually(@ShellOption(defaultValue = "artist") String entity){
        switch (entity) {
            case "exhibit" ->{
                System.out.println(message.localize("EnterName"));
                String name = in.next();
                System.out.println(message.localize("EnterType"));
                String type = in.next();
                System.out.println(message.localize("dateENTER"));
                Date datecreation = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.print(message.localize("EnterArtistID"));
                Artist artist_for_insert = artistService.findById(in.nextLong());
                try {
                    exhibitService.insert(name, datecreation, type, artist_for_insert);
                    System.out.println(message.localize("InsertComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("InsertError"));
                    e.printStackTrace();
                }
            }
            case "artist" -> {
                System.out.println(message.localize("firstnameEnter"));
                String firstname = in.next();
                System.out.println(message.localize("secondnameEnter"));
                String secondname = in.next();
                System.out.println(message.localize("familynameEnter"));
                String familyname = in.next();
                System.out.println(message.localize("dateofbirthENTER"));
                Date dateofbirth =  new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.println(message.localize("dateofdeathENTER"));
                Date dateofdeath =  new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.println(message.localize("countryENTER"));
                String country = in.next();
                try {
                    artistService.insert(firstname,secondname,familyname,dateofbirth,country,dateofdeath);
                    System.out.println(message.localize("InsertComplete"));
                }catch (Exception e){
                    System.out.println(message.localize("InsertError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Update")
    public void update(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption long id,
                       @ShellOption(defaultValue = ShellOption.NULL) String firstname,
                       @ShellOption(defaultValue = ShellOption.NULL) String secondname,
                       @ShellOption(defaultValue = ShellOption.NULL) String familyname,
                       @ShellOption(defaultValue = "31") Integer dayofbirth,
                       @ShellOption(defaultValue = "0") Integer mounthofbirth,
                       @ShellOption(defaultValue = "9999") Integer yearofbirth,
                       @ShellOption(defaultValue = ShellOption.NULL) String country,
                       @ShellOption(defaultValue = "31") Integer dayofdeath,
                       @ShellOption(defaultValue = "0") Integer mounthofdeath,
                       @ShellOption(defaultValue = "9999") Integer yearofdeath,
                       @ShellOption(defaultValue = "31") Integer dayofcreation,
                       @ShellOption(defaultValue = "0") Integer mounthofcreation,
                       @ShellOption(defaultValue = ShellOption.NULL) String name,
                       @ShellOption(defaultValue = ShellOption.NULL) String type,
                       @ShellOption(defaultValue = "9999") Integer yearofcreation,
                       @ShellOption(defaultValue = "0") long artist){
        switch (entity) {
            case "artist" -> {
                try {
                    artistService.update(id, firstname, secondname, familyname, new Date(new GregorianCalendar(yearofbirth, mounthofbirth, dayofbirth).getTimeInMillis()), country, new Date(new GregorianCalendar(yearofdeath, mounthofdeath, dayofdeath).getTimeInMillis()));
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" -> {
                try {
                    long id_for_insert = id;
                    if (artist!=0)
                        id_for_insert = artist;
                    Artist artist_for_insert = artistService.findById(id_for_insert);
                    exhibitService.update(id, name, new Date(new GregorianCalendar(yearofcreation, mounthofcreation, dayofcreation).getTimeInMillis()), type,artist_for_insert);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Update artist manually")
    public void updateManually(@ShellOption(defaultValue = "artist") String entity){
        switch (entity) {
            case "artist" -> {
                System.out.println(message.localize("EnterID"));
                long id = in.nextLong();
                System.out.println(message.localize("firstnameEnter"));
                String firstname = in.next();
                System.out.println(message.localize("secondnameEnter"));
                String secondname = in.next();
                System.out.println(message.localize("familynameEnter"));
                String familyname = in.next();
                System.out.println(message.localize("dateofbirthENTER"));
                Calendar datebirth = new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt());
                Date dateofbirth = new Date(datebirth.getTimeInMillis());
                System.out.println(message.localize("dateofdeathENTER"));
                Calendar datedeath = new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt());
                Date dateofdeath = new Date(datedeath.getTimeInMillis());
                System.out.println(message.localize("countryENTER"));
                String country = in.next();
                try {
                    artistService.update(id, firstname, secondname, familyname, dateofbirth, country, dateofdeath);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" ->{
                System.out.println(message.localize("EnterID"));
                long id = in.nextLong();
                System.out.println(message.localize("EnterName"));
                String name = in.next();
                System.out.println(message.localize("EnterType"));
                String type = in.next();
                System.out.println(message.localize("dateENTER"));
                Date datecreation = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(), in.nextInt()).getTimeInMillis());
                System.out.print(message.localize("EnterArtistID"));
                Artist artist_for_insert = artistService.findById(in.nextLong());
                try {
                    exhibitService.update(id,name,datecreation,type,artist_for_insert);
                    System.out.println(message.localize("UpdateComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("UpdateError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }

    @ShellMethod("Delete artist")
    public void delete(@ShellOption(defaultValue = "artist") String entity,
                       @ShellOption long id){
        switch(entity) {
            case "artist" -> {
                try {
                    artistService.deletebyId(id);
                    System.out.println(message.localize("DeleteComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("DeleteError"));
                    e.printStackTrace();
                }
            }
            case "exhibit" -> {
                try {
                    exhibitService.deletebyId(id);
                    System.out.println(message.localize("DeleteComplete"));
                } catch (Exception e) {
                    System.out.println(message.localize("DeleteError"));
                    e.printStackTrace();
                }
            }
            default -> System.out.println(message.localize("defaultFindAllMSG"));
        }
    }
}
