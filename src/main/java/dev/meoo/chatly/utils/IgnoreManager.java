package dev.meoo.chatly.utils;

import dev.meoo.chatly.Chatly;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class IgnoreManager {

    private static final Map<UUID, Set<UUID>> ignoredPlayers = new HashMap<>();

    private static File file;
    private static YamlConfiguration cfg;

    public static void initialize() {
        file = new File(Chatly.getInstance().getDataFolder(), "ignore.yml");

        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }

        cfg = YamlConfiguration.loadConfiguration(file);

        if (!cfg.contains("ignored")) {
            return;
        }

        for (String uuidString : cfg.getConfigurationSection("ignored").getKeys(false)) {

            UUID playerUUID = UUID.fromString(uuidString);

            List<String> ignoredList =
                    cfg.getStringList("ignored." + uuidString);

            Set<UUID> ignored = new HashSet<>();

            for (String ignoredUUID : ignoredList) {
                ignored.add(UUID.fromString(ignoredUUID));
            }

            ignoredPlayers.put(playerUUID, ignored);
        }
    }

    public static void save() {

        cfg.set("ignored", null);

        for (Map.Entry<UUID, Set<UUID>> entry : ignoredPlayers.entrySet()) {

            List<String> uuids = entry.getValue()
                    .stream()
                    .map(UUID::toString)
                    .toList();

            cfg.set(
                    "ignored." + entry.getKey(),
                    uuids
            );
        }

        try {
            cfg.save(file);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static boolean isIgnoring(UUID player, UUID target) {

        Set<UUID> ignored = ignoredPlayers.get(player);

        return ignored != null && ignored.contains(target);
    }

    public static void ignore(UUID player, UUID target) {

        ignoredPlayers
                .computeIfAbsent(player, k -> new HashSet<>())
                .add(target);

        save();
    }

    public static void unignore(UUID player, UUID target) {

        Set<UUID> ignored = ignoredPlayers.get(player);

        if (ignored == null) {
            return;
        }

        ignored.remove(target);

        if (ignored.isEmpty()) {
            ignoredPlayers.remove(player);
        }

        save();
    }
}