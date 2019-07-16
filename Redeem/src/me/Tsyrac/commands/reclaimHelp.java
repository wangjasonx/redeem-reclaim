package me.Tsyrac.commands;

import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class reclaimHelp extends SubCommand{

    private main plugin = main.getInstance();
    private List<String> messages;

    @Override
    public void onCommand(Player player, String[] args) {

        if(!(player.hasPermission("reclaim.admin"))){
            messages = new ArrayList<>();
            messages.add(ChatColor.GRAY + "Developer: Tsyrac");
            messages.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Commands:");
            messages.add(ChatColor.RED + "/reclaim" + ChatColor.DARK_RED + " <group name>");
            sendPlayerMessages(messages, player);
        }
        else{
            messages = new ArrayList<>();
            messages.add(ChatColor.GRAY + "Developer: Tsyrac");
            messages.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Commands:");
            messages.add(ChatColor.RED + "/reclaim" + ChatColor.DARK_RED + " <group name>");
            messages.add(ChatColor.RED + "/reclaim reload");
            messages.add(ChatColor.RED + "/reclaim add" + ChatColor.DARK_RED + " <group>");
            messages.add(ChatColor.RED + "/reclaim addCommand" + ChatColor.DARK_RED + " <group> <command>");
            messages.add(ChatColor.RED + "/reclaim clearPlayers");
            messages.add(ChatColor.RED+ "/reclaim addPlayer" + ChatColor.DARK_RED + " <name>");
            messages.add(ChatColor.RED + "/reclaim removePlayer" + ChatColor.DARK_RED + " <name>");
            messages.add(ChatColor.RED + "/reclaim viewGroup" + ChatColor.DARK_RED + " <group name>");
            messages.add(ChatColor.RED + "/reclaim listGroups");
            sendPlayerMessages(messages, player);
        }


    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.help;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }


    public void sendPlayerMessages(List<String> toSend, Player player){
        for(String toReturn : toSend){
            player.sendMessage(toReturn);
        }
    }
}
