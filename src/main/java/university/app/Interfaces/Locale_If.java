package university.app.Interfaces;

import university.app.Services.Exceptions.LocaleNotSupportedException;

public interface Locale_If {
    void set(String locale) throws LocaleNotSupportedException;
    java.util.Locale get();
}
