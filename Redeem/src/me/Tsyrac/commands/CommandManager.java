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
    private Player toCheck;

    public CommandManager(){}

    //Sub Commands
    public String mainCommand = "reclaim";
    public String help = "help";

    public void setup(){
        plugin.getCommand(mainCommand).setExecutor(this);
        this.commands.add(new reclaimHelp(allowed(toCheck)));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args){

        if(!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Only players can use these commands!");
            return true;
        }

        Player player = (Player) sender;
        toCheck = player;

        if(command.getName().equalsIgnoreCase(mainCommand)){
            if(args.length == 0){
                player.sendMessage(ChatColor.RED + "Please add arguments to your command. Type /reclaim help for info");
                return true;
            }

            SubCommand target = this.get(args[0]);

            if(target == null){
                player.sendMessage(ChatColor.RED + "Invalid subcommand");
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

    private SubCommand get(String name){
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

    public boolean allowed(Player player) {
        return(player.hasPermission("reclaim.admin"));
    }



}

