package me.Tsyrac.redeem;
import me.Tsyrac.commands.reclaimCommand;
import me.Tsyrac.customConfig.customConfig;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    @Override
    public void onEnable(){
        // Plugin startup logic

        //Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Setup reclaimConfig
        customConfig.setup();
        customConfig.getFile().options().copyDefaults(true);
        customConfig.save();
        getCommand("reclaim").setExecutor(new reclaimCommand());

    }

    @Override
    public void onDisable(){
        System.out.println("The plugin is shutting down!");
        customConfig.save();
        // Plugin shutdown logic
    }

}
