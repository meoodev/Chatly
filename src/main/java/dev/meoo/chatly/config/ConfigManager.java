package dev.meoo.chatly.config;

import dev.meoo.chatly.Chatly;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ConfigManager {

    private static final String[] LANGUAGE_FILES = {
            "en.yml",
            "ru.yml"
    };

    public static FileConfiguration getConfig(String cfgName)
            throws IOException, InvalidConfigurationException {

        File file = new File(Chatly.getInstance().getDataFolder(), cfgName);

        FileConfiguration configuration = new YamlConfiguration();
        configuration.options().parseComments(true);
        configuration.load(file);

        return configuration;
    }

    private static void createConfigs() {

        File dataFolder = Chatly.getInstance().getDataFolder();

        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File langFolder = new File(dataFolder, "langs");

        if (!langFolder.exists()) {
            langFolder.mkdirs();
        }

        File config = new File(dataFolder, "config.yml");

        if (!config.exists()) {
            Chatly.getInstance().saveResource("config.yml", false);
        }

        List<String> languages = Arrays.asList(LANGUAGE_FILES);

        for (String lang : languages) {

            File langFile = new File(langFolder, lang);

            if (!langFile.exists()) {
                Chatly.getInstance().saveResource(
                        "langs/" + lang,
                        false
                );
            }
        }
    }

    public static void loadConfig() {
        createConfigs();

        MainConfigStorage.loadData();
        LanguageConfigStorage.loadData();
    }
}