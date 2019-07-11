/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InventoryMenu;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Mokito
 */
public class LootInventory implements Listener{
    private final static Main PLUGIN = Main.getPlugin(Main.class);
    public static Inventory loot = Bukkit.createInventory(null, 36, "Loot");
    static {
        
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = exit.getItemMeta();
        itemMeta.setDisplayName("Exit");
        exit.setItemMeta(itemMeta);
        loot.setItem(35, exit);
    }
    
    
    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked(); // The player that clicked the item
        ItemStack clicked = event.getCurrentItem(); // The item that was clicked
        Inventory inventory = event.getInventory(); // The inventory that was clicked in
        
        if (inventory.getClass().getName().equals(loot.getClass().getName())) {
            if(event.getSlot() == 35){
                player.closeInventory();
            }
            if(event.getSlot()>=0 && event.getSlot()<=26){
                if(player.getInventory().firstEmpty()!=-1){
                    getItem(player,event.getSlot());
                    displayLoot(player);
                }else{
                    player.sendMessage(ChatColor.DARK_RED+"Inventory full :<");
                }
                
            }
            
            
            event.setCancelled(true);
            player.updateInventory();
        }
    }
    
    private static void setItem(ItemStack item,int Slot) {
        loot.setItem(Slot, item);
    }
    
    public static void displayLoot(Player p){
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,p.getUniqueId().toString()+".yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<Map<?, ?>> itemlist = items.getMapList("inventory.items");
                clear();
                for(int i=0;i<itemlist.size();i++){
                    setItem(ItemStack.deserialize((Map<String, Object>) itemlist.get(i)),i);
                }
                p.openInventory(loot);
            }
        }
    }
    
    private static void getItem(Player p, int slot) {
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,p.getUniqueId().toString()+".yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<Map<?, ?>> itemlist = items.getMapList("inventory.items");
                if(slot<=itemlist.size()-1)
                    if(p.getInventory().firstEmpty()!=-1){
                        p.getInventory().addItem(ItemStack.deserialize((Map<String, Object>) itemlist.get(slot)));
                        itemlist.remove(slot);
                        items.set("inventory.items",itemlist);
                        
                        try {
                            items.save(itemsFile);
                        } catch (IOException ex) {
                            Logger.getLogger(LootInventory.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }else{
                        if(slot<=itemlist.size()-1)
                            p.sendMessage(ChatColor.DARK_RED+"Inventory full :<");
                    }
            }
        }
    }
    
    private static void clear(){
        loot = Bukkit.createInventory(null, 36, "Loot");
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = exit.getItemMeta();
        itemMeta.setDisplayName("Exit");
        exit.setItemMeta(itemMeta);
        loot.setItem(35, exit);
    }
    
}
