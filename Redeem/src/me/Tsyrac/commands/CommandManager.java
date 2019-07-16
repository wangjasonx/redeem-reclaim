package me.Tsyrac.commands;

import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;


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

}

