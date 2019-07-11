/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Events;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Random;
import me.Dremnor.DigLoot.Main;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Mokito
 */
public class BreakBlock implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    
    @EventHandler
    public void onBreak(BlockBreakEvent event){
        
        Random rn = new Random();
        int los = rn.nextInt(plugin.chance);        
        if(plugin.debug)event.getPlayer().sendMessage("Zniszczyles Blok los: "+Integer.toString(los));

        if(los>=1 && los<=50){
            File itemsFile = new File(plugin.getDataFolder(),"loot.yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<Map<?, ?>> itemlist = items.getMapList("loot.items");
                if(itemlist.size()>0){
                    los = rn.nextInt(itemlist.size());
                    event.getPlayer().getInventory().addItem(ItemStack.deserialize((Map<String, Object>) itemlist.get(los)));
                    event.getPlayer().sendMessage(ChatColor.GOLD+"WoW! You got free loot! Check your inventory.");
                }
            }
            
        }
        
        
    }
}
