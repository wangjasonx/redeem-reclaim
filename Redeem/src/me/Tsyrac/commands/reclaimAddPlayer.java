package me.Tsyrac.commands;

import me.Tsyrac.customConfig.userList;
import me.Tsyrac.redeem.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.UUID;

public class reclaimAddPlayer extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {

        if(args.length == 0){
            player.sendMessage(ChatColor.RED + "Missing arguments: <player>");
        }
        else if(args.length > 1){
            player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
        }
        else{
            addPlayer(player, args[0]);
        }

    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.addPlayer;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
    }

    public boolean addPlayer(Player player, String p){
        UUID unique = getUniquePlayer(p, player);
        if(checkUserList(player, p)){
            player.sendMessage(ChatColor.DARK_RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " already exists");
            return false;
        }
        userList.getFile().createSection(unique.toString());
        userList.save();
        player.sendMessage(ChatColor.RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " has been added");
        return true;
    }


    public boolean checkUserList(Player p, String player){
        UUID unique = getUniquePlayer(player, p);
        if(userList.getFile().contains(unique.toString())){
            return true;
        }
        return false;
    }


    public UUID getUniquePlayer(String player, Player p){
        if(Bukkit.getPlayer(player) != null){
            return(Bukkit.getPlayer(player).getUniqueId());
        }
        else if(Bukkit.getOfflinePlayer(player) != null){
            return(Bukkit.getOfflinePlayer(player).getUniqueId());
        }
        else{
            p.sendMessage(ChatColor.RED + "Player not found");
            return null;
        }
    }

}
