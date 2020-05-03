package me.bottleofglass.TokenShop;

import me.bottleofglass.TokenShop.commands.TokenShopCommand;
import me.bottleofglass.TokenShop.gui.GUIListener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    private static Main instance;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        TokenShopConfig.initializeConfig(getConfig());
        getServer().getPluginManager().registerEvents(new GUIListener(), this);
        getCommand("tokenshop").setExecutor(new TokenShopCommand());
    }
    public static Main getInstance() {return instance;}
}
