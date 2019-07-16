package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class reclaimAdd extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {
        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Missing arguments: <group>");
        }
        else if(args.length > 1){
            player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
        }
        else{
            customConfig.getFile().createSection(args[0]);
            customConfig.getFile().getConfigurationSection(args[0]).set("Permission", "reclaim." + args[0]);
            customConfig.getFile().getConfigurationSection(args[0]).set("Commands", new ArrayList<String>());
            customConfig.save();
            player.sendMessage(ChatColor.RED + args[0] + " has been added!");
        }
    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.add;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }
}
