package com.github.irvinglink.ClashDonations.utils;

import com.github.irvinglink.ClashDonations.ClashDonationsPlugin;
import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Chat {

    private final ClashDonationsPlugin plugin = ClashDonationsPlugin.getPlugin(ClashDonationsPlugin.class);

    private final Pattern pattern = Pattern.compile("[%]([^%]+)[%]");
    private ReplacementHook replacementHook;

    private static final Map<String, IReplacementHook> hookMap = new HashMap<>();

    public String str(String textToTranslate) {
        return ChatColor.translateAlternateColorCodes('&', textToTranslate);
    }

    public void registerHook() {

        if (this.replacementHook == null) this.replacementHook = new ReplacementHook();

    }

    public void unRegisterHook(String key) {
        hookMap.remove(key);
    }

    public Map<String, IReplacementHook> getHooks() {
        return hookMap;
    }

    public String getPluginPrefix() {
        return "&8&l[&aClashDonations&8&l] ";
    }

    public String getPrefix() {
        return this.plugin.getLang().getString("Prefix");
    }

    public String replace(String text, boolean color) {
        return replace(null, null, null, text, color);
    }

    public String replace(OfflinePlayer player, String text, boolean color) {
        return replace(player, null, null, text, color);
    }

    public String replace(OfflinePlayer player, OfflinePlayer target, String text, boolean color) {
        return replace(player, target, null, text, color);
    }

    public String replace(OfflinePlayer player, String str, String text, boolean color) {
        return replace(player, null, str, text, color);
    }

    public String replace(String str, String text, boolean color) {
        return replace(null, null, str, text, color);
    }



    public String replace(OfflinePlayer player, OfflinePlayer target, String str, String text, boolean color) {

        if (text == null) return null;

        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) text = PlaceholderAPI.setPlaceholders(player, text);

        Matcher matcher = this.pattern.matcher(text);

        while (matcher.find()) {

            String var = matcher.group(1);
            if (var == null) return null;

            String value = replacementHook.replace(player, target, str, var);

            if (value != null) text = text.replaceAll(Pattern.quote(matcher.group()), Matcher.quoteReplacement(value));

        }

        return (color) ? str(text) : text;
    }


}