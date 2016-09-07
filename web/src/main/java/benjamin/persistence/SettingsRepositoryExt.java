package benjamin.persistence;


import benjamin.exception.ApplicationException;
import benjamin.model.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SettingsRepositoryExt {

    @Autowired
    private SettingsRepository settingsRepository;

    public Settings getTheSettings() {
        final List<Settings> found = settingsRepository.findAll();
        if (found.size() == 1) {
            return found.get(0);
        } else if (found.size() > 1) {
            throw new ApplicationException("More than 1 settings found, abort.");
        }
        return null;
    }
}
