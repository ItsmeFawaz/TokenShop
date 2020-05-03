package me.bottleofglass.TokenShop;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

public class TokenShopConfig {
    private static FileConfiguration config;
    private static String consoleMsg;
    private static String balanceMsg;
    private static String invalidAmount;
    private static String payMsg;
    private static String receiveMsg;
    private static String insufficientFunds;
    private static String invalidPlayer;
    private static String helpMsg;
    private static String invalidArgument;
    private static String insufficientArguments;
    private static String purchaseSuccess;
    public static void initializeConfig(FileConfiguration config1) {
        config = config1;
        ConfigurationSection messageSection = config.getConfigurationSection("messages");
        consoleMsg = messageSection.getString("consoleMsg");
        balanceMsg = messageSection.getString("balanceMsg");
        invalidAmount = messageSection.getString("invalidAmount");
        payMsg = messageSection.getString("payMsg");
        receiveMsg = messageSection.getString("receiveMsg");
        insufficientFunds = messageSection.getString("insufficientFunds");
        invalidPlayer = messageSection.getString("invalidPlayer");
        helpMsg = messageSection.getString("helpMsg");
        invalidArgument = messageSection.getString("invalidArgument");
        insufficientArguments = messageSection.getString("insufficientArguments");
        purchaseSuccess = messageSection.getString("purchaseSuccess");
    }

    public static String getConsoleMsg() {
        return consoleMsg;
    }

    public static String getBalanceMsg() {
        return balanceMsg;
    }

    public static String getInvalidAmount() {
        return invalidAmount;
    }

    public static String getPayMsg() {
        return payMsg;
    }

    public static String getReceiveMsg() {
        return receiveMsg;
    }

    public static String getInsufficientFunds() {
        return insufficientFunds;
    }

    public static String getInvalidPlayer() {
        return invalidPlayer;
    }

    public static String getHelpMsg() {
        return helpMsg;
    }

    public static String getInvalidArgument() {
        return invalidArgument;
    }

    public static String getInsufficientArguments() {
        return insufficientArguments;
    }

    public static String getPurchaseSuccess() {
        return purchaseSuccess;
    }
}
