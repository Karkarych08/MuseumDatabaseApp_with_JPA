package university.app.Services;

import org.springframework.stereotype.Service;
import university.app.Interfaces.Locale_If;
import university.app.Services.Exceptions.LocaleNotSupportedException;

import java.util.Locale;
import java.util.Map;
@Service
public class LocaleIfService implements Locale_If {

    private final static Map<String, Locale> SUPPORTED = Map.of(
            "en", Locale.forLanguageTag("en"),
            "ru", Locale.forLanguageTag("ru")
    );

    private Locale current = SUPPORTED.get("ru");

    @Override
    public void set(String locale) throws LocaleNotSupportedException {
        var loc = SUPPORTED.get(locale);
        if (loc == null)
            throw new LocaleNotSupportedException();
        current = loc;

    }

    @Override
    public Locale get() {
        return current;
    }
    
}
