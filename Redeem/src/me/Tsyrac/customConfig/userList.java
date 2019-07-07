package me.Tsyrac.customConfig;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class userList {

    private static File file;
    private static FileConfiguration customFile;
    private static List<String> list = new ArrayList<>();

    //Finds or generates the custom user file
    public static void setup(){

        file = new File(Bukkit.getServer().getPluginManager().getPlugin("Redeem").getDataFolder(), "userList.yml");

        if(!file.exists()){
            try{
                file.createNewFile();
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
