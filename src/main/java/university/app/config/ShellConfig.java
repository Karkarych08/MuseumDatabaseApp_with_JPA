package university.app.config;


import lombok.RequiredArgsConstructor;
import org.jline.utils.AttributedString;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;
import university.app.Services.Massage_localization;


@Configuration
@RequiredArgsConstructor
public class ShellConfig implements PromptProvider {

    private final Massage_localization message;

    @Override
    public final AttributedString getPrompt() {

        return new AttributedString(message.localize("startMENU"));

    }

}
