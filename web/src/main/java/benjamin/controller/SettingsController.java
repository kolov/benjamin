package benjamin.controller;

import benjamin.model.Settings;
import benjamin.persistence.SettingsRepository;
import benjamin.persistence.SettingsRepositoryExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/v1/settings")
public class SettingsController {

    @Autowired
    private SettingsRepository settingsRepository;

    @Autowired
    private SettingsRepositoryExt settingsRepositoryExt;

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public Settings list() {

        final Settings found = settingsRepositoryExt.getTheSettings();
        if (found != null) {
            return found;
        }

        return new Settings();
    }


    @RequestMapping(method = RequestMethod.PUT)
    @ResponseBody
    public void save(@RequestBody final Settings settings) {
        final Settings existing = settingsRepositoryExt.getTheSettings();
        if (existing != null) {
            settings.setId(existing.getId());
        }
        settingsRepository.save(settings);
    }


}