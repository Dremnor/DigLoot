/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Language;

import java.io.File;
import me.Dremnor.DigLoot.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 *
 * @author Mokito
 */
public class Locale {
    private final static Main PLUGIN = Main.getPlugin(Main.class);
    public static String created_by;
    public static String item_added_loot;
    public static String item_already_in_loot;
    public static String you_need_to_hold_item_in_hand;
    public static String wrong_cmd_param_rem;
    public static String you_need_to_add_some_items;
    public static String debug_enable;
    public static String debug_disable;
    public static String repair;
    public static String tools;
    public static String no_perms;
    public static String no_player;
    public static String got_loot;
    public static String full_loot;
    public static String item_drop;
    public static String tool_filter_add;
    public static String tool_filter_remove;
    public static String next_page;
    public static String prev_page;
    public static String not_on_the_list;
    public static String inv_full;
    public static String added_filter;
    public static String already_in_filter;
    public static String no_slot_for_tools;
    public static String wrong_cmd_rate;
    public static String locale_reload;
    public static String exit;
    
    
    public static void reloadLocale(String lan){
        File itemsFile = new File(PLUGIN.getDataFolder(),"locale.yml");
        FileConfiguration strings = YamlConfiguration.loadConfiguration(itemsFile);
        
        created_by = strings.getString(lan+".created_by");
        item_added_loot = strings.getString(lan+".item_added_loot");
        item_already_in_loot =strings.getString(lan+".item_already_in_loot");
        you_need_to_hold_item_in_hand =strings.getString(lan+".you_need_to_hold_item_in_hand");
        wrong_cmd_param_rem =strings.getString(lan+".wrong_cmd_param_rem");
        you_need_to_add_some_items =strings.getString(lan+".you_need_to_add_some_items");
        debug_enable =strings.getString(lan+".debug_enable");
        debug_disable =strings.getString(lan+".debug_disable");
        repair =strings.getString(lan+".repair");
        tools =strings.getString(lan+".tools");
        no_perms =strings.getString(lan+".no_perms");
        no_player =strings.getString(lan+".no_player");
        got_loot =strings.getString(lan+".got_loot");
        full_loot =strings.getString(lan+".full_loot");
        item_drop =strings.getString(lan+".item_drop");
        tool_filter_add =strings.getString(lan+".tool_filter_add");
        tool_filter_remove =strings.getString(lan+".tool_filter_remove");
        next_page =strings.getString(lan+".next_page");
        prev_page =strings.getString(lan+".prev_page");
        not_on_the_list =strings.getString(lan+".not_on_the_list");
        inv_full =strings.getString(lan+".inv_full");
        added_filter =strings.getString(lan+".added_filter");
        already_in_filter =strings.getString(lan+".already_in_filter");
        no_slot_for_tools =strings.getString(lan+".no_slot_for_tools");
        wrong_cmd_rate = strings.getString(lan+".wrong_cmd_rate");
        locale_reload = strings.getString(lan+".locale_reload");
        exit = strings.getString(lan+".exit");

    }
}
