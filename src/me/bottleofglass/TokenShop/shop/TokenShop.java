package me.bottleofglass.TokenShop.shop;

import life.savag3.supremehoes.Harvesters;
import me.bottleofglass.TokenShop.Main;
import me.bottleofglass.TokenShop.TokenShopConfig;
import me.bottleofglass.TokenShop.gui.ConfirmationGUI;
import me.bottleofglass.TokenShop.gui.CustomizedGUI;
import me.bottleofglass.TokenShop.gui.GUIButton;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.stream.Collectors;

public class TokenShop {
    private static final Harvesters harvesterAPI;
    static {
        harvesterAPI = Harvesters.getInstance();
    }
    public static void openShop(Player player) {
        ConfigurationSection shopSection = Main.getInstance().getConfig().getConfigurationSection("shop");
        CustomizedGUI gui = new CustomizedGUI(player, 4, "TokenShop");
        for (int i = 1; i < 37; i++) {
            ConfigurationSection currentSection = shopSection.getConfigurationSection("slot" + i);
            ItemStack stack = stackFromConfig(currentSection, player);
            GUIButton currentButton = new GUIButton(stack);
            if (currentSection.getBoolean("purchasable")) {
                int price = currentSection.getInt("price");
                if (currentSection.getStringList("commandsOnPurchase") != null) {
                    currentButton.setAction(() -> {
                        ConfirmationGUI confirmationGUI = new ConfirmationGUI(gui.getOwner());
                        confirmationGUI.setConfirmAction(() -> {
                            if(harvesterAPI.getByPlayer(gui.getOwner()).hasTokens(price)) {
                                harvesterAPI.getByPlayer(gui.getOwner()).takeTokens(price);
                                currentSection.getStringList("commandsOnPurchase").forEach(x -> Bukkit.dispatchCommand(Bukkit.getConsoleSender(), x.replace("{player}", player.getName())));
                                gui.getOwner().sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getPurchaseSuccess().replace("{itemname}", currentSection.getString("name")).replace("{price}", String.valueOf(price))));
                            } else {
                                gui.getOwner().sendMessage(ChatColor.translateAlternateColorCodes('&', TokenShopConfig.getInsufficientFunds()));
                            }
                        });
                        confirmationGUI.open();
                    });
                }
            }
            gui.addButton(i - 1, currentButton);
        }
        gui.open();
    }

    private static ItemStack stackFromConfig(ConfigurationSection section, Player p) {
        ItemStack stack;
        int price = section.getInt("price");
        if (section.getInt("quantity") != 0) {
            if (Material.getMaterial(section.getString("material")) != null) {
                stack = new ItemStack(Material.getMaterial(section.getString("material")), section.getInt("quantity"));
            } else {
                stack = new ItemStack(Material.AIR, section.getInt("quantity"));
            }

        } else {
            if (Material.getMaterial(section.getString("material")) != null) {
                stack = new ItemStack(Material.getMaterial(section.getString("material")));
            } else {
                stack = new ItemStack(Material.AIR, section.getInt("quantity"));
            }
        }
        if (stack.getType() != Material.AIR) {
            ItemMeta meta;
            if (stack.hasItemMeta()) {
                meta = stack.getItemMeta();
            } else {
                meta = Bukkit.getItemFactory().getItemMeta(stack.getType());
            }
            if (section.getString("name") != null)
                meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', section.getString("name").replace("{token}", String.valueOf(harvesterAPI.getByPlayer(p).getTokens()))));
            if (section.getStringList("lore") != null)
                meta.setLore(section.getStringList("lores").stream().map(x -> ChatColor.translateAlternateColorCodes('&', x.replace("{price}", String.valueOf(price)))).collect(Collectors.toList()));
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            stack.setItemMeta(meta);
            if (section.getBoolean("isEnchanted")) {
                stack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
            }
        }
        return stack;
    }
}