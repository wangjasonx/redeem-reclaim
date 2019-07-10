package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import me.Tsyrac.customConfig.userList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


public class reclaimCommand implements CommandExecutor {

    private FileConfiguration redeemConfig;
    private FileConfiguration playerConfig;
    private List<String> messages;

    public reclaimCommand() {
        this.redeemConfig = customConfig.getFile();
        this.playerConfig = userList.getFile();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;

            if(command.getName().equalsIgnoreCase("reclaim") && !allowed((Player)sender)){
                if(args.length == 0 && !allowed(player)) {
                    messages = new ArrayList<>();
                    messages.add(ChatColor.GRAY + "Developer: Tsyrac");
                    messages.add(ChatColor.GOLD + "" + ChatColor.BOLD + "Commands:");
                    messages.add(ChatColor.RED + "/reclaim" + ChatColor.DARK_RED + " <group name>");
                    sendPlayerMessages(messages, player);
                }
            }

            if(command.getName().equalsIgnoreCase("reclaim") && allowed((Player)sender)){
                if(args.length == 0){
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
                    sendPlayerMessages(messages, player);
                }
                else if(args[0].equalsIgnoreCase("viewGroup")){
                    if(args.length > 2) {
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                    else if(args.length < 2){
                        player.sendMessage(ChatColor.RED + "Missing arguments:" + ChatColor.DARK_RED + " <group name>");
                    }
                    else{
                        viewGroup(args[1], player);
                    }
                }
                else if(args[0].equalsIgnoreCase("clearPlayers")){
                    userList.reset();
                    player.sendMessage(ChatColor.DARK_RED + "All players have been cleared from the list.");
                }
                else if(args[0].equalsIgnoreCase("addPlayer")){
                    if(args.length > 2){
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                    else if(args.length < 2){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <player>");
                    }
                    else {
                        addPlayer(player, args[1]);
                    }
                }
                else if(args[0].equalsIgnoreCase("removePlayer")){
                    if(args.length > 2){
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                    else if(args.length < 2){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <player>");
                    }
                    else {
                        removePlayer(player, args[1]);
                    }
                }
                else if(args[0].equalsIgnoreCase("reload")) {
                    customConfig.reload();
                    userList.reload();
                    player.sendMessage(ChatColor.RED + "The configs have been reloaded!");
                }
                else if(args[0].equalsIgnoreCase("add")){
                    if(args.length > 2) {
                        player.sendMessage(ChatColor.DARK_RED + "Incorrect usage, please try again.");
                    }
                    else if(args.length < 2){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <group>");
                    }
                    else{
                        redeemConfig.createSection(args[1]);
                        redeemConfig.getConfigurationSection(args[1]).set("Permission", "reclaim." + args[1]);
                        redeemConfig.getConfigurationSection(args[1]).set("Commands", new ArrayList<String>());
                        customConfig.save();
                        player.sendMessage(ChatColor.RED + args[1] + " has been added!");
                    }
                }
                else if(args[0].equalsIgnoreCase("addCommand")){
                    if(args.length < 2) {
                        player.sendMessage(ChatColor.RED + "Missing arguments: <group> <command>");
                    }
                    else if(args.length < 3){
                        player.sendMessage(ChatColor.RED + "Missing arguments: <command>");
                    }
                    else if(cycleFile(args[1])){
                        String addedCommand = "";
                        List<String> commandAdd = redeemConfig.getStringList(args[1] + ".Commands");
                        for (int i = 2; i < args.length; i++) {
                            if(i + 1 == args.length) {
                                addedCommand += args[i];
                            }
                            else{
                                addedCommand += args[i] + " ";
                            }
                        }
                        commandAdd.add(addedCommand);
                        redeemConfig.getConfigurationSection(args[1]).set("Commands", commandAdd);
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
                        List<String> runCommands = redeemConfig.getStringList(args[0] + ".Commands");
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

    //Cycles through the config.yml and checks if the group exists
    public boolean cycleFile(String argument) {
        return(redeemConfig.contains(argument));
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
        return redeemConfig.getString(path + ".Permission");
    }

    //Checks if the player has admin permissions or not
    public boolean allowed(Player player) {
        return(player.hasPermission("reclaim.admin"));
    }

    //Searching through list of UUIDs for the player, if found returns false, if not found adds player and returns true
    public boolean searchPlayer(Player player){
        if(playerConfig.contains(player.getUniqueId().toString())){
            return false;
        }
        playerConfig.createSection(player.getUniqueId().toString());
        userList.save();
        return true;
    }

    //Searching through list of UUIDs for the player
    public boolean checkUserList(Player p, String player){
        UUID unique = getUniquePlayer(player, p);
        if(playerConfig.contains(unique.toString())){
            return true;
        }
        return false;
    }

    //Adds player to the UUID list
    public boolean addPlayer(Player player, String p){
        UUID unique = getUniquePlayer(p, player);
        if(checkUserList(player, p)){
            player.sendMessage(ChatColor.DARK_RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " already exists");
            return false;
        }
        playerConfig.createSection(unique.toString());
        userList.save();
        player.sendMessage(ChatColor.RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " has been added");
        return true;
    }

    //Removes player from the UUID list
    public boolean removePlayer(Player player, String p){
        UUID unique = getUniquePlayer(p, player);
        if(checkUserList(player, p)){
            playerConfig.set(unique.toString(), null);
            userList.save();
            player.sendMessage(ChatColor.RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " has been removed");
            return true;
        }
        player.sendMessage(ChatColor.RED + "Player: " + ChatColor.GOLD + p + ChatColor.RED + " not found");
        return false;
    }

    //Views the group
    public void viewGroup(String group, Player p){
        List<String> commands = redeemConfig.getStringList(group + ".Commands");
        p.sendMessage(ChatColor.GOLD + group + ":");
        for(String x : commands){
            p.sendMessage(ChatColor.LIGHT_PURPLE + "- " + x);
        }
    }


    //Sends list of messages to players
    public void sendPlayerMessages(List<String> toSend, Player player){
        for(String toReturn : toSend){
            player.sendMessage(toReturn);
        }
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
