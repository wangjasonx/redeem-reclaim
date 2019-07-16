package me.Tsyrac.redeem;
import me.Tsyrac.commands.CommandManager;
import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.customConfig.userList;
import org.bukkit.plugin.java.JavaPlugin;

public class main extends JavaPlugin {

    private static main instance;
    public CommandManager commandManager;

    @Override
    public void onEnable(){

        setInstance(this);
        commandManager = new CommandManager();

        commandManager.setup();
        // Plugin startup logic

        //Setup config
        getConfig().options().copyDefaults();
        saveDefaultConfig();

        //Setup reclaimConfig
        customConfig.setup();
        customConfig.getFile().options().copyDefaults(true);
        customConfig.save();

        //setup userList
        userList.setup();
        userList.getFile().options().copyDefaults(true);
        userList.save();

        //adds the reclaim command

    }

    @Override
    public void onDisable(){
        System.out.println("The plugin is shutting down!");
        customConfig.save();
        userList.save();
        // Plugin shutdown logic
    }

    public static main getInstance(){
        return instance;
    }

    public static void setInstance(main instance){
        main.instance = instance;
    }

}
