package me.Tsyrac.customConfig;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class customConfig {

    private static File file;
    private static FileConfiguration customFile;
    private static List<String> list = new ArrayList<>();

    //Finds or generates the custom config file
    public static void setup(){

        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Redeem").getDataFolder(), "redeemConfig.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
                reload();
                customFile.createSection("Example");
                customFile.getConfigurationSection("Example").set("Permission", "reclaim.Example");
                customFile.getConfigurationSection("Example").set("Commands", list);
                list.add("give <player> flower 1");
                save();
            }
            catch(IOException e) {
                /* nothing. */
            }

        }
        reload();

    }

    public static FileConfiguration getFile() {
        return customFile;
    }

    public static void save() {
        try{
            customFile.save(file);
        }
        catch(IOException e){
            System.out.println("Couldn't save the file");
        }

    }

    public static void reload() {
        customFile = YamlConfiguration.loadConfiguration(file);
    }

}
