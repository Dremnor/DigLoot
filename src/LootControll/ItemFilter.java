/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package LootControll;

import Commands.CommandDl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Mokito
 */
public class ItemFilter {
    private static final Main PLUGIN = Main.getPlugin(Main.class);
    
    public static void add(Player p){
        if(!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
            File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
            if(!itemsFile.exists()){
                try {
                    itemsFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
            
            List<String> itemlist = items.getStringList("filter.blocks");
            if(!itemlist.contains(p.getInventory().getItemInMainHand().getType().name())){
                itemlist.add(p.getInventory().getItemInMainHand().getType().name());
                items.set("filter.blocks", itemlist);
                try {
                    items.save(itemsFile);
                } catch (IOException ex) {
                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                }
                p.sendMessage(ChatColor.DARK_GREEN+"Item added to filter table \\0/");
            }else{
                p.sendMessage(ChatColor.DARK_RED+"Item already in filter table :o");
            }
            
        }else{
            p.sendMessage(ChatColor.DARK_RED+"You need to hold item in hand :(");
        }
    }
    
    public static boolean isRewardable(String itemname){
        File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
        if(itemsFile.exists()){
            FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
            List<String> itemlist = items.getStringList("filter.blocks");
            
            if(itemlist.contains(itemname)){
                if(PLUGIN.debug)System.out.println("Filter zawiera blok");
                return PLUGIN.is_filter_whitelist;                
            }else{
                if(PLUGIN.debug)System.out.println("Filter nie zawiera bloku");
                return !PLUGIN.is_filter_whitelist;
            }
        }
        return false;
    }
    
}
