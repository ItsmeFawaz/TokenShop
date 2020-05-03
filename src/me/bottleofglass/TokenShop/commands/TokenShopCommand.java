package me.bottleofglass.TokenShop.commands;

import life.savag3.supremehoes.Harvesters;
import me.bottleofglass.TokenShop.TokenShopConfig;
import me.bottleofglass.TokenShop.shop.TokenShop;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TokenShopCommand implements CommandExecutor {
    Harvesters harvesterAPI;
    public TokenShopCommand()  {
        harvesterAPI = Harvesters.getInstance();
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if(!(commandSender instanceof Player)) {
            commandSender.sendMessage(ChatColor.translateAlternateColorCodes('&',TokenShopConfig.getConsoleMsg()));
            return true;
        }
        Player p  = (Player) commandSender;
        if(strings.length == 0) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&',TokenShopConfig.getHelpMsg()));
            return true;
        }
        if(strings[0].equalsIgnoreCase("bal")) {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getBalanceMsg().replace("{token}",String.valueOf(harvesterAPI.getByPlayer(p).getTokens()))));
            return true;
        }else if(strings[0].equalsIgnoreCase("shop")) {
            TokenShop.openShop(p);
            return true;
        } else if(strings[0].equalsIgnoreCase("pay")) {
            if(strings.length < 4) {
                p.sendMessage(ChatColor.RED + "/token pay <playername> <amount>");
                return true;
            } else {
                if(Bukkit.getPlayer(strings[1]) != null) {
                    Player targetPlayer = Bukkit.getPlayer(strings[1]);
                    double amount;
                    try {
                        amount = Double.parseDouble(strings[2]);
                    } catch(NumberFormatException e) {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getInvalidAmount()));
                        return true;
                    }
                    if(harvesterAPI.getByPlayer(p).hasTokens(amount)) {
                        harvesterAPI.getByPlayer(p).takeTokens(amount);
                        harvesterAPI.getByPlayer(targetPlayer).addTokens(amount);
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getPayMsg().replace("{token}", String.valueOf(amount)).replace("{receiver}",targetPlayer.getName())));
                        targetPlayer.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getReceiveMsg().replace("{token}", String.valueOf(amount)).replace("{sender}", p.getName())));
                        return true;
                    } else {
                        p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getInsufficientFunds()));
                    }
                } else {
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getInvalidPlayer()));
                }
            }
        } else {
            p.sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getInvalidArgument()));
        }
        return true;
    }

}
