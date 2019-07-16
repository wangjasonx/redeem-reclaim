package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class reclaimAddCommand extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Missing arguments: <group> <command>");
        }
        else if(args.length < 2){
            player.sendMessage(ChatColor.RED + "Missing arguments: <command>");
        }
        else if(cycleFile(args[0])){
            String addedCommand = "";
            List<String> commandAdd = customConfig.getFile().getStringList(args[0] + ".Commands");
            for (int i = 1; i < args.length; i++) {
                if(i + 1 == args.length) {
                    addedCommand += args[i];
                }
                else{
                    addedCommand += args[i] + " ";
                }
            }
            commandAdd.add(addedCommand);
            customConfig.getFile().getConfigurationSection(args[0]).set("Commands", commandAdd);
            customConfig.save();
            player.sendMessage(ChatColor.RED + "Added the command: " + ChatColor.YELLOW + addedCommand);
        }
        else{
            player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
        }
    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.addCommand;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    //Cycles through the config.yml and checks if the group exists
    public boolean cycleFile(String argument) {

        for(String s : getPathNames()){
            if(s.equalsIgnoreCase(argument)){
                return true;
            }
        }
        return false;
        //return(redeemConfig.contains(argument));
    }

    public List<String> getPathNames(){
        List<String> list = new ArrayList<>();
        for(String key : customConfig.getFile().getKeys(false)){
            if(key.indexOf(".") == -1) {
                list.add(key);
            }
        }
        return list;
    }

}
