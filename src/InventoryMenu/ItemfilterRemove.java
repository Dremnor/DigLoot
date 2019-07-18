/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package InventoryMenu;



import Language.Locale;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
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
            if(event.getRawSlot() < event.getView().getTopInventory().getSize()){
                if(PLUGIN.debug)System.out.println("Slot: "+event.getSlot()+" Title: "+event.getView().getTitle());
                if(event.getSlot() == 35){
                    player.closeInventory();
                }
                if(event.getSlot() == 32){
                    ItemStack clicked = event.getCurrentItem();
                    if(clicked!=null)
                        if(clicked.getType().equals(Material.GREEN_STAINED_GLASS_PANE)){
                            int page = getLoreInt(clicked,0);
                            if(page!=-1){
                                displayFilters(player,page);
                            }
                        }
                }
                if(event.getSlot() == 30){
                    ItemStack clicked = event.getCurrentItem();
                    if(clicked!=null)
                        if(clicked.getType().equals(Material.RED_STAINED_GLASS_PANE)){
                            int page = getLoreInt(clicked,0);
                            if(page!=-1){
                                displayFilters(player,page);
                            }
                        }
                }
                if(event.getSlot()>=0 && event.getSlot()<=26){
                    removeItem(event.getSlot());
                    displayFilters(player,0);
                }
            }
            event.setCancelled(true);
            
        }
    }
    
    public static void displayFilters(Player p, int from){
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<String> itemlist = items.getStringList("filter.blocks");
                clear();
                int x=0;
                for(int i=from;i<itemlist.size();i++){
                    ItemStack item = new ItemStack(Material.getMaterial(itemlist.get(i)));
                    setItem(item,x);
                    x++;
                    if(x==27) break;
                }
                if(from==0){
                    if(itemlist.size()>27){
                        addNext("27");
                    }
                }else
                    if(from!=0){
                        if(from+27<itemlist.size()){
                            addNext(Integer.toString(from+27));
                        }
                        if(from-27>=0){
                            addBack(Integer.toString(from-27));
                        }
                    }
                p.openInventory(itemfilterinventory);
            }else{
                p.sendMessage(ChatColor.DARK_RED+Locale.you_need_to_add_some_items);
            }
        }
    }
    private static int getLoreInt(ItemStack item,int slot){
        ItemMeta im = item.getItemMeta();
        List<String> lo = im.getLore();
        if(lo.size()>=slot){
            return Integer.parseInt(lo.get(slot));
        }
        return -1;
    }
    
    private static void addNext(String page){
        ItemStack next = new ItemStack(Material.GREEN_STAINED_GLASS_PANE,1);
        ItemMeta mt = next.getItemMeta();
        List<String> lo = new ArrayList<>();
        lo.add(page);
        mt.setLore(lo);
        mt.setDisplayName(ChatColor.GREEN+Locale.next_page);
        next.setItemMeta(mt);
        setItem(next,32);
    }
    
    private static void addBack(String page){
        ItemStack next = new ItemStack(Material.RED_STAINED_GLASS_PANE,1);
        ItemMeta mt = next.getItemMeta();
        List<String> lo = new ArrayList<>();
        lo.add(page);
        mt.setLore(lo);
        mt.setDisplayName(ChatColor.RED+Locale.prev_page);
        next.setItemMeta(mt);
        setItem(next,30);
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
    
    public static void removeItem(Player p, Block item) {
        if(!PLUGIN.mysql){
            File itemsFile = new File(PLUGIN.getDataFolder(),"itemfilter.yml");
            if(itemsFile.exists()){
                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                List<String> itemlist = items.getStringList("filter.blocks");
                if(itemlist.contains(item.getType().name())){
                    itemlist.remove(item.getType().name());
                    items.set("filter.blocks",itemlist);
                    try {
                        items.save(itemsFile);
                        p.sendMessage(ChatColor.DARK_GREEN+"Item Removed! "+item.getType().name());
                    } catch (IOException ex) {
                        Logger.getLogger(LootInventory.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    p.sendMessage(ChatColor.DARK_RED+Locale.not_on_the_list);
                }
            }else{
                p.sendMessage(ChatColor.DARK_RED+Locale.you_need_to_add_some_items);
            }
        }
    }
    
    
    private static void clear(){
        itemfilterinventory = Bukkit.createInventory(null, 36, "Remove");
        ItemStack exit = new ItemStack(Material.BARRIER, 1);
        ItemMeta itemMeta = exit.getItemMeta();
        itemMeta.setDisplayName(Locale.exit);
        exit.setItemMeta(itemMeta);
        itemfilterinventory.setItem(35, exit);
    }
    
    private static void setItem(ItemStack item,int Slot) {
        itemfilterinventory.setItem(Slot, item);
    }
    
}
