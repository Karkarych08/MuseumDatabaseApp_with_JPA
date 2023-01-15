package university.app.Services;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import university.app.Interfaces.Locale_If;
import university.app.Interfaces.artistRepository;
import university.app.Services.Exceptions.LocaleNotSupportedException;
import university.app.dao.artistDAO;

import java.sql.Date;
import java.util.*;

@ShellComponent
@RequiredArgsConstructor
public class UserInterface {

    private final artistRepository artistService;
    private final Massage_localization message;
    private final Locale_If locale;
    private final Scanner in = new Scanner(System.in);

    @ShellMethod("Find all persons")
    public void findAll() {
        for (artistDAO artist : artistService.findAll()) {
            System.out.println(artist);
        }
    }

    @ShellMethod("Find by parameter")
    public void find(@ShellOption(defaultValue = "id") @NotNull String parameter){
        switch (parameter){
            case "country" -> {
                    System.out.print(message.localize("countryENTER"));
                    String country = in.next();
                    if (!Objects.equals(artistService.findAllByCountry(country).toString(), "[]"))
                        for (artistDAO artist: artistService.findAllByCountry((country))){
                            System.out.println(artist);
                        }
                    else System.out.println(message.localize("NoMatchingData"));
            }
            case "date" -> {
                System.out.print(message.localize("dateENTER"));
                Date date = new Date(new GregorianCalendar(in.nextInt(), in.nextInt(),in.nextInt()).getTimeInMillis());
                if (!Objects.equals(artistService.findOlderThenDate(date).toString(), "[]"))
                    for (artistDAO artist: artistService.findOlderThenDate(date)){
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

    @ShellMethod("Change Language")
    public void changeLanguage(){
        System.out.print(message.localize("ChangeLanguageMSG"));
        try {
            locale.set(in.next().toLowerCase());
        }catch (LocaleNotSupportedException e) {
            e.printStackTrace();
        }
    }

    @ShellMethod("Insert artist")
    public void insert(@ShellOption(defaultValue = "null") String firstname,
                       @ShellOption(defaultValue = "null") String secondname,
                       @ShellOption(defaultValue = "null") String familyname,
                       @ShellOption(defaultValue = "null") Integer dayofbirth,
                       @ShellOption(defaultValue = "null") Integer mounthofbirth,
                       @ShellOption(defaultValue = "null") Integer yearofbirth,
                       @ShellOption(defaultValue = "null") String country,
                       @ShellOption(defaultValue = "null") Integer dayofdeath,
                       @ShellOption(defaultValue = "null") Integer mounthofdeath,
                       @ShellOption(defaultValue = "null") Integer yearofdeath){
        Date datebirth =new Date(new GregorianCalendar(yearofbirth,mounthofbirth,dayofbirth).getTimeInMillis());
        Date datedeath = new Date(new GregorianCalendar(yearofdeath,mounthofdeath,dayofdeath).getTimeInMillis());
        try {
            artistService.insert(firstname,secondname,familyname,datebirth,country,datedeath);
            System.out.println(message.localize("InsertComplete"));
        }catch (Exception e){
            System.out.println(message.localize("InsertError"));
        }
    }
    @ShellMethod("Insert artist manually")
    public void insertManually(){
        System.out.println(message.localize("firstnameEnter"));
        String firstname = in.next();
        System.out.println(message.localize("secondnameEnter"));
        String secondname = in.next();
        System.out.println(message.localize("familynameEnter"));
        String familyname = in.next();
        System.out.println(message.localize("dateofbirthENTER"));
        Date datebirth =new Date(new GregorianCalendar(in.nextInt(), in.nextInt(),in.nextInt()).getTimeInMillis());
        System.out.println(message.localize("dateofdeathENTER"));
        Date datedeath =new Date(new GregorianCalendar(in.nextInt(), in.nextInt(),in.nextInt()).getTimeInMillis());
        System.out.println(message.localize("countryENTER"));
        String country = in.next();
        try {
            artistService.insert(firstname,secondname,familyname,datebirth,country,datedeath);
            System.out.println(message.localize("InsertComplete"));
        }catch (Exception e){
            System.out.println(message.localize("InsertError"));
            e.printStackTrace();
        }
    }

    @ShellMethod("Update artist")
    public void update(@ShellOption long id,
                       @ShellOption(defaultValue = ShellOption.NULL) String firstname,
                       @ShellOption(defaultValue = ShellOption.NULL) String secondname,
                       @ShellOption(defaultValue = ShellOption.NULL) String familyname,
                       @ShellOption(defaultValue = "31") Integer dayofbirth,
                       @ShellOption(defaultValue = "0") Integer mounthofbirth,
                       @ShellOption(defaultValue = "9999") Integer yearofbirth,
                       @ShellOption(defaultValue = ShellOption.NULL) String country,
                       @ShellOption(defaultValue = "31") Integer dayofdeath,
                       @ShellOption(defaultValue = "0") Integer mounthofdeath,
                       @ShellOption(defaultValue = "9999") Integer yearofdeath){
        try {
            artistService.update(id,firstname,secondname,familyname,new Date(new GregorianCalendar(yearofbirth,mounthofbirth,dayofbirth).getTimeInMillis()),country,new Date(new GregorianCalendar(yearofdeath,mounthofdeath,dayofdeath).getTimeInMillis()));
            System.out.println(message.localize("UpdateComplete"));
        }catch (Exception e){
            System.out.println(message.localize("UpdateError"));
            e.printStackTrace();
        }
    }

    @ShellMethod("Update artist manually")
    public void updateManually(){
        System.out.println(message.localize("EnterID"));
        long id = in.nextLong();
        System.out.println(message.localize("firstnameEnter"));
        String firstname = in.next();
        System.out.println(message.localize("secondnameEnter"));
        String secondname = in.next();
        System.out.println(message.localize("familynameEnter"));
        String familyname = in.next();
        System.out.println(message.localize("dateofbirthENTER"));
        Calendar datebirth = new GregorianCalendar(in.nextInt(), in.nextInt(),in.nextInt());
        Date dateofbirth = new Date(datebirth.getTimeInMillis());
        System.out.println(message.localize("dateofdeathENTER"));
        Calendar datedeath = new GregorianCalendar(in.nextInt(), in.nextInt(),in.nextInt());
        Date dateofdeath = new Date(datedeath.getTimeInMillis());
        System.out.println(message.localize("countryENTER"));
        String country = in.next();
        try {
            artistService.update(id,firstname,secondname,familyname,dateofbirth,country,dateofdeath);
            System.out.println(message.localize("UpdateComplete"));
        }catch (Exception e){
            System.out.println(message.localize("UpdateError"));
            e.printStackTrace();
        }
    }

    @ShellMethod("Delete artist")
    public void delete(@ShellOption long id){
        try{
            artistService.deletebyId(id);
            System.out.println(message.localize("DeleteComplete"));
        }catch (Exception e){
            System.out.println(message.localize("DeleteError"));
            e.printStackTrace();
        }
    }
}
