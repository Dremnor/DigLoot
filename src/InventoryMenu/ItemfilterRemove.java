/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InventoryMenu;


import static InventoryMenu.LootInventory.loot;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.Bukkit;
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
public class ItemfilterRemove implements Listener {
    private final static Main PLUGIN = Main.getPlugin(Main.class);
    public static Inventory itemfilterinventory = Bukkit.createInventory(null, 36, "Remove");
    
    static {
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = exit.getItemMeta();
        itemMeta.setDisplayName("Exit");
        exit.setItemMeta(itemMeta);
        itemfilterinventory.setItem(35, exit);
    }
    
    @EventHandler
    public static void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
  
        if (event.getView().getTitle().equals("Remove")) {
            if(event.getSlot() == 35){
                player.closeInventory();
            }
            if(event.getSlot()>=0 && event.getSlot()<=26){
                removeItem(event.getSlot());
                displayFilters(player);
            }
            event.setCancelled(true);
        }
    }
    
    public static void displayFilters(Player p){
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<String> itemlist = items.getStringList("filter.blocks");
                clear();
                for(int i=0;i<itemlist.size();i++){
                    ItemStack item = new ItemStack(Material.getMaterial(itemlist.get(i)));
                    setItem(item,i);
                }
                p.openInventory(itemfilterinventory);
            }
        }
    }
    
    private static void removeItem(int slot) {
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<String> itemlist = items.getStringList("filter.blocks");
                if(slot<=itemlist.size()-1){
                    itemlist.remove(slot);
                    items.set("filter.blocks",itemlist);
                    try {
                        items.save(itemsFile);
                    } catch (IOException ex) {
                        Logger.getLogger(LootInventory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
    }
    
    
    private static void clear(){
        itemfilterinventory = Bukkit.createInventory(null, 36, "Remove");
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = exit.getItemMeta();
        itemMeta.setDisplayName("Exit");
        exit.setItemMeta(itemMeta);
        itemfilterinventory.setItem(35, exit);
    }
    
    private static void setItem(ItemStack item,int Slot) {
        itemfilterinventory.setItem(Slot, item);
    }
    
}
