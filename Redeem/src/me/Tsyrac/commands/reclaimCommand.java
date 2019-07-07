package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.Tsyrac.customConfig.userList;
import java.util.ArrayList;
import java.util.List;


public class reclaimCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            if(command.getName().equalsIgnoreCase("reclaim")){
                Player player = (Player) sender;
                if(args.length == 0 && !allowed(player)) {
                    player.sendMessage(ChatColor.GRAY + "Developer: Tsyrac");
                    player.sendMessage(ChatColor.GOLD + "Commands:");
                    player.sendMessage(ChatColor.RED + "/reclaim <rank name>");
                }
                else if(args.length == 0 && allowed(player)){
                    player.sendMessage(ChatColor.GRAY + "Developer: Tsyrac");
                    player.sendMessage(ChatColor.GOLD + "Commands:");
                    player.sendMessage(ChatColor.RED + "/reclaim <rank name>");
                    player.sendMessage(ChatColor.RED + "/reclaim reload");
                    player.sendMessage(ChatColor.RED + "/reclaim add <rank>");
                    player.sendMessage(ChatColor.RED + "/reclaim addCommand <rank> <command>");
                }
                else if(args[0].equalsIgnoreCase("reload") && allowed(player)) {
                    customConfig.reload();
                    player.sendMessage(ChatColor.RED + "The config has been reloaded!");
                }
                else if(args[0].equalsIgnoreCase("add") && allowed(player)){
                    if(args.length > 2) {
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                    else if(args.length < 2){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <rank>");
                    }
                    else{
                        customConfig.getFile().createSection(args[1]);
                        customConfig.getFile().getConfigurationSection(args[1]).set("Permission", "reclaim." + args[1]);
                        customConfig.getFile().getConfigurationSection(args[1]).set("Commands", new ArrayList<String>());
                        customConfig.save();
                        player.sendMessage(ChatColor.RED + args[1] + " has been added!");
                    }
                }
                else if(args[0].equalsIgnoreCase("addCommand") && allowed(player)){
                    if(args.length < 2) {
                        player.sendMessage(ChatColor.RED + "Missing arguments: <rank> <command>");
                    }
                    else if(args.length < 3){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <command>");
                    }
                    else if(cycleFile(args[1])){
                        String addedCommand = "";
                        List<String> commandAdd = customConfig.getFile().getStringList(args[1] + ".Commands");
                        for (int i = 2; i < args.length; i++) {
                            if(i + 1 == args.length) {
                                addedCommand += args[i];
                            }
                            else{
                                addedCommand += args[i] + " ";
                            }
                        }
                        commandAdd.add(addedCommand);
                        customConfig.getFile().getConfigurationSection(args[1]).set("Commands", commandAdd);
                        customConfig.save();
                        player.sendMessage(ChatColor.RED + "Added the command: " + ChatColor.YELLOW + addedCommand);
                    }
                    else{
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                }
                else {
                    if(!cycleFile(args[0])){
                        player.sendMessage(ChatColor.RED + args[0] + " does not exist. Please try again.");
                    }
                    else if(sender.hasPermission(getPermission(args[0])) && searchPlayer(player)){
                        ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                        List<String> runCommands = customConfig.getFile().getStringList(args[0] + ".Commands");
                        for(int i = 0; i < runCommands.size(); i++){
                            String swap = replacePlayerInstance(runCommands.get(i), (Player) sender);
                            swap = swap.trim();
                            Bukkit.dispatchCommand(console, swap);
                        }
                        player.sendMessage(ChatColor.DARK_RED + "Successfully claimed: " + ChatColor.GOLD + args[0]);
                    }
                    else {
                        player.sendMessage(ChatColor.DARK_RED + "You do not have access to: " + ChatColor.GOLD + args[0]);
                    }
                }
            }
        }
        return true;
    }

    //Cycles through the config.yml and checks if the rank exists
    public boolean cycleFile(String argument) {
        return(customConfig.getFile().contains(argument));
    }

    //Checks for instances of <player> in config.yml commands
    public String replacePlayerInstance(@NotNull String argument, Player p){
        String[] toFix = argument.split(" ");
        String toReturn = "";
        for(String x : toFix) {
            if(x.compareToIgnoreCase("<player>") == 0) {
                toReturn += p.getName() + " ";
            } else{
                toReturn += x + " ";
            }
        }
        return toReturn;
    }

    //Grabs the permission from the config.yml file
    public String getPermission(String path){
        return customConfig.getFile().getString(path + ".Permission");
    }

    //Checks if the player has admin permissions or not
    public boolean allowed(Player player) {
        return(player.hasPermission("reclaim.admin"));
    }

    //Searching through list of UUIDs for the player, if found returns false, if not found adds player and returns true
    public boolean searchPlayer(Player player){
        if(userList.getFile().contains(player.getUniqueId().toString())){
            player.sendMessage(ChatColor.DARK_RED + "You have already reclaimed your items");
            return false;
        }
        userList.getFile().createSection(player.getUniqueId().toString());
        userList.save();
        return true;
    }

}
