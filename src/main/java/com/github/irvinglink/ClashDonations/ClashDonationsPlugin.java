package com.github.irvinglink.ClashDonations;

import com.github.irvinglink.ClashDonations.commands.ConfirmCommand;
import com.github.irvinglink.ClashDonations.commands.StoreAdminCommand;
import com.github.irvinglink.ClashDonations.database.DatabaseManager;
import com.github.irvinglink.ClashDonations.database.DatabaseType;
import com.github.irvinglink.ClashDonations.database.MySQL;
import com.github.irvinglink.ClashDonations.database.SQLite;
import com.github.irvinglink.ClashDonations.gui.manager.GuiManager;
import com.github.irvinglink.ClashDonations.gui.manager.IMenu;
import com.github.irvinglink.ClashDonations.models.Package;
import com.github.irvinglink.ClashDonations.monitors.LoaderMonitor;
import com.github.irvinglink.ClashDonations.utils.Chat;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public final class ClashDonationsPlugin extends JavaPlugin {

    private File configFile, langFile;
    private FileConfiguration config, lang;

    private GuiManager guiManager;
    private Chat chat;
    private DatabaseManager database;

    private final Map<UUID, IMenu> playerMenus = Collections.synchronizedMap(new HashMap<>());
    private LoaderMonitor loader;

    @Override
    public void onLoad() {

        createFiles();

        this.guiManager = new GuiManager();
        this.loader = new LoaderMonitor();

        this.chat = new Chat();
        chat.registerHook();


    }

    @Override
    public void onEnable() {

        setupDatabase(config.getString("Database.host"), config.getString("Database.database"), config.getString("Database.username"), config.getString("Database.password"));

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            System.out.println("[ChatFilter] PlaceHolderAPI enabled");

        } else System.out.println("[ChatFilter] PlaceHolderAPI has not been found");

        new StoreAdminCommand("storeadmin", "clashstore.admin", true);
        new ConfirmCommand("confirm", "", false);

    }

    @Override
    public void onDisable() {


    }

    public final Map<UUID, IMenu> getPlayerMenus() {
        return playerMenus;
    }

    public GuiManager getGuiManager() {
        return this.guiManager;
    }

    public Chat getChat() {
        return this.chat;
    }

    void createFiles() {

        File mainFolder = getDataFolder();

        if (!mainFolder.exists()) mainFolder.mkdirs();

        this.configFile = new File(mainFolder, "config.yml");
        this.langFile = new File(mainFolder, "lang.yml");

        if (!this.configFile.exists())
            try {
                Files.copy(getResource(configFile.getName()), configFile.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }


        if (!this.langFile.exists())
            try {
                Files.copy(getResource(langFile.getName()), langFile.toPath());
            } catch (IOException exception) {
                exception.printStackTrace();
            }

        this.config = getConfig();
        this.lang = getLang();

        updateFiles(configFile, langFile);

    }

    void setupDatabase(String host, String database, String username, String password) {

        DatabaseType databaseType = DatabaseType.valueOf(getConfig().getString("Database.type").toUpperCase());

        switch (databaseType) {

            case MYSQL:
            case MARIADB:
                MySQL mySQL = new MySQL();
                mySQL.setup(host, database, username, password);

                this.database = mySQL;
                break;

            case SQLITE:
                SQLite sqLite = new SQLite();
                sqLite.setup(host, database, username, password);

                this.database = sqLite;
                break;

        }

    }

    void updateFiles(File... files) {

        getServer().getLogger().info("[ClashDonations] Updating files...");

        for (File file : files) {

            YamlConfiguration externalYamlConfig = YamlConfiguration.loadConfiguration(file);
            externalYamlConfig.options().copyDefaults(true);

            InputStreamReader internalConfig = new InputStreamReader(Objects.requireNonNull(getResource(file.getName())), StandardCharsets.UTF_8);
            YamlConfiguration internalYamlConfig = YamlConfiguration.loadConfiguration(internalConfig);

            for (String line : internalYamlConfig.getKeys(true))
                if (!externalYamlConfig.contains(line)) externalYamlConfig.set(line, internalYamlConfig.get(line));

            try {
                externalYamlConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        getServer().getLogger().info("[ClashDonations] Files are now updated!");

    }

    public void reloadConfig() {
        if (this.config != null)
            this.config = YamlConfiguration.loadConfiguration(this.configFile);

        if (this.lang != null)
            this.lang = YamlConfiguration.loadConfiguration(this.langFile);

    }

    public void saveConfig() {
        if (this.config != null)
            try {
                this.config.save(this.configFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        if (this.lang != null)
            try {
                this.lang.save(this.langFile);
            } catch (IOException e) {
                e.printStackTrace();
            }

    }

    @Override
    public FileConfiguration getConfig() {

        if (this.config != null)
            return this.config;

        this.config = new YamlConfiguration();

        try {
            this.config.load(this.configFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.config;

    }

    public FileConfiguration getLang() {
        if (this.lang != null)
            return this.lang;

        this.lang = new YamlConfiguration();

        try {
            this.lang.load(this.langFile);
        } catch (IOException | org.bukkit.configuration.InvalidConfigurationException e) {
            e.printStackTrace();
        }

        return this.lang;
    }

    public DatabaseManager getDatabaseManager() {
        return database;
    }

    public LoaderMonitor getLoader() {
        return loader;
    }
}
