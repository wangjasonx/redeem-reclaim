package me.Tsyrac.commands;

import me.Tsyrac.customConfig.userList;
import me.Tsyrac.redeem.main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class reclaimClearPlayers extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {
        userList.reset();
        player.sendMessage(ChatColor.DARK_RED + "All players have been cleared from the list.");
    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.clearPlayers;
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
