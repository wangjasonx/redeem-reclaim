package me.Tsyrac.commands;

import me.Tsyrac.customConfig.customConfig;
import me.Tsyrac.customConfig.userList;
import me.Tsyrac.redeem.main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class reclaimReload extends SubCommand{

    private main plugin = main.getInstance();

    @Override
    public void onCommand(Player player, String[] args) {
        Plugin toChange = Bukkit.getServer().getPluginManager().getPlugin("Redeem");
        customConfig.reload();
        userList.reload();
        Bukkit.getPluginManager().disablePlugin(toChange);
        Bukkit.getPluginManager().enablePlugin(toChange);
        player.sendMessage(ChatColor.GREEN + "Reload complete!");
    }

    @Override
    public void onCommand(String[] args) {

    }

    @Override
    public String name() {
        return plugin.commandManager.reload;
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
