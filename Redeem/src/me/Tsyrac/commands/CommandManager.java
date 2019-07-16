package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.customConfig.userList;
import me.Tsyrac.redeem.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;


public class CommandManager implements CommandExecutor{

    private ArrayList<SubCommand> commands = new ArrayList<SubCommand>();
    private main plugin = main.getInstance();

    public CommandManager(){}

    //Sub Commands
    public String mainCommand = "reclaim";
    public String help = "help";
    public String listGroups = "listgroups";
    public String viewGroup = "viewgroup";
    public String clearPlayers = "clearplayers";
    public String addPlayer = "addplayer";
    public String removePlayer = "removeplayer";
    public String reload = "reload";
    public String add = "add";
    public String addCommand = "addcommand";


    public void setup(){
        plugin.getCommand(mainCommand).setExecutor(this);
        this.commands.add(new reclaimHelp());
        this.commands.add(new reclaimListGroups());
        this.commands.add(new reclaimViewGroup());
        this.commands.add(new reclaimClearPlayers());
        this.commands.add(new reclaimAddPlayer());
        this.commands.add(new reclaimRemovePlayer());
        this.commands.add(new reclaimReload());
        this.commands.add(new reclaimAdd());
        this.commands.add(new reclaimAddCommand());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use these commands!");
            return true;
        }

        Player player = (Player) sender;

        if(command.getName().equalsIgnoreCase(mainCommand)){
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "Please add arguments to your command. Type /reclaim help for info");
                return true;
            }

            if(args.length == 1 && cycleFile(args[0])){

                if(sender.hasPermission(getPermission(args[0])) && searchPlayer(player)){
                    ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
                    List<String> runCommands = customConfig.getFile().getStringList(getPath(args[0]) + ".Commands");
                    for(int i = 0; i < runCommands.size(); i++){
                        String swap = replacePlayerInstance(runCommands.get(i), (Player) sender);
                        swap = swap.trim();
                        Bukkit.dispatchCommand(console, swap);
                    }
                    player.sendMessage(ChatColor.DARK_RED + "Successfully claimed: " + ChatColor.GOLD + getPath(args[0]));
                }
                else {
                    player.sendMessage(ChatColor.DARK_RED + "You do not have access to: " + ChatColor.GOLD + getPath(args[0]));
                }

            }
            else if(!(player.hasPermission("reclaim.admin"))){
                ArrayList<String> helpCommand = new ArrayList<String>();
                helpCommand.addAll(Arrays.asList(args));
                helpCommand.remove(0);
                commands.get(0).onCommand(player, helpCommand.toArray(new String[0]));
            }
            else{
                SubCommand target = this.get(args[0], player);

                if(target == null){
                    player.sendMessage(ChatColor.RED + "You cannot do that!");
                    return true;
                }

                ArrayList<String> arrayList = new ArrayList<String>();

                arrayList.addAll(Arrays.asList(args));
                arrayList.remove(0);

                try
                {
                    target.onCommand(player, arrayList.toArray(new String[0]));
                }
                catch(Exception e)
                {
                    player.sendMessage(ChatColor.RED + "An error has occurred");
                    e.printStackTrace();
                }
            }
        }

        return true;
    }

    private SubCommand get(String name, Player p){

        Iterator<SubCommand> subcommands = this.commands.iterator();

        while(subcommands.hasNext()){
            SubCommand sc = subcommands.next();
            if(sc.name().equalsIgnoreCase(name)){
                return sc;
            }

            String[] aliases;
            int length = (aliases = sc.aliases()).length;

            for(int i = 0; i < length; i++){
                String alias = aliases[i];
                if(name.equalsIgnoreCase(alias)){
                    return sc;
                }

            }
        }
        return null;
    }

    //Searching through list of UUIDs for the player, if found returns false, if not found adds player and returns true
    public boolean searchPlayer(Player player){
        if(userList.getFile().contains(player.getUniqueId().toString())){
            return false;
        }
        userList.getFile().createSection(player.getUniqueId().toString());
        userList.save();
        return true;
    }

    public boolean cycleFile(String argument) {

        for(String s : getPathNames()){
            if(s.equalsIgnoreCase(argument)){
                return true;
            }
        }
        return false;
        //return(redeemConfig.contains(argument));
    }

    public String getPath(String check){
        for(String get : getPathNames()){
            if(get.equalsIgnoreCase(check)){
                return get;
            }
        }
        return "";
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
        return customConfig.getFile().getString(getPath(path) + ".Permission");
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

