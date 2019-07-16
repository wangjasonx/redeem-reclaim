package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.List;

public class reclaimViewGroup extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Missing arguments:" + ChatColor.DARK_RED + " <group name>");
        }
        else if(args.length > 1){
            player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
        }
        else{
            viewGroup(args[0], player);
        }

    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.viewGroup;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    public void viewGroup(String group, Player p){
        List<String> commands = customConfig.getFile().getStringList(group + ".Commands");
        p.sendMessage(ChatColor.GOLD + group + ":");
        for(String x : commands){
            p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + x);
        }
    }
}
