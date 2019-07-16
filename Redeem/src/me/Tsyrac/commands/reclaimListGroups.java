package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class reclaimListGroups extends SubCommand {

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {

        this.getGroups(player);

    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.listGroups;
    }

    @Override
    public String info() {
        return null;
    }

    @Override
    public String[] aliases() {
        return new String[0];
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


    public void getGroups(Player player){
        String list = String.join(", ", getPathNames());
        player.sendMessage(ChatColor.GOLD + "Groups: ");
        player.sendMessage(ChatColor.GREEN + list);
    }

}
