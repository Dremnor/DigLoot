/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Commands;

import InventoryMenu.ItemfilterRemove;
import InventoryMenu.LootInventory;
import Language.Locale;
import LootControll.ItemFilter;
import Tools.FilterAddTool;
import Tools.FilterRemoveTool;
import YmlData.YmlDataControl;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 *
 * @author Mokito
 */
public class CommandDl implements CommandExecutor {
    Main plugin = Main.getPlugin(Main.class);
    public CommandDl() {
        //plugin.getCommand("dl").setExecutor(new CommandDl());
    }
    
    @Override
    public boolean onCommand(CommandSender cs, Command cmnd, String string, String[] strings) {
        
        if(cs instanceof Player){
            Player p = (Player) cs;
            if(strings.length>0){
                if(p.hasPermission("digloot.admin")){
                    
                    
                    if(strings[0].equals("serialize")){
                        p.sendMessage(p.getInventory().getItemInMainHand().serialize().toString());
                    }
                    
                    if(strings[0].equals("add")){
                        if(!p.getInventory().getItemInMainHand().getType().equals(Material.AIR)){
                            File itemsFile = new File(plugin.getDataFolder(),"loot.yml");
                            if(!itemsFile.exists()){
                                try {
                                    itemsFile.createNewFile();
                                } catch (IOException ex) {
                                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            }
                            FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                            
                            List<Map<?, ?>> itemlist = items.getMapList("loot.items");
                            if(!itemlist.contains(p.getInventory().getItemInMainHand().serialize())){
                                itemlist.add(p.getInventory().getItemInMainHand().serialize());
                                items.set("loot.items", itemlist);
                                try {
                                    items.save(itemsFile);
                                } catch (IOException ex) {
                                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                p.sendMessage(ChatColor.DARK_GREEN+Locale.item_added_loot);
                            }else{
                                p.sendMessage(ChatColor.DARK_RED+Locale.item_already_in_loot);
                            }
                            
                        }else{
                            p.sendMessage(ChatColor.DARK_RED+Locale.you_need_to_hold_item_in_hand);
                        }
                    }
                    
                    if(strings[0].equals("remove")){
                        File itemsFile = new File(plugin.getDataFolder(),"loot.yml");
                        if(itemsFile.exists()){
                            if(strings.length==2){
                                FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                                List<Map<?, ?>> itemlist = items.getMapList("loot.items");
                                if(tryParseInt(strings[1])){
                                    int item = Integer.parseInt(strings[1]);
                                    if(item!=0 && item-1<itemlist.size()){
                                        itemlist.remove(item-1);
                                        items.set("loot.items", itemlist);
                                        try {
                                            items.save(itemsFile);
                                        } catch (IOException ex) {
                                            Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                                        }
                                    }else{
                                        p.sendMessage(ChatColor.DARK_RED+Locale.wrong_cmd_param_rem);
                                    }
                                }else{
                                    p.sendMessage(ChatColor.DARK_RED+Locale.wrong_cmd_param_rem);
                                }
                            }else{
                                p.sendMessage(ChatColor.DARK_RED+Locale.wrong_cmd_param_rem);
                            }
                        }else{
                            p.sendMessage(ChatColor.DARK_RED+Locale.wrong_cmd_param_rem);
                        }
                    }
                    
                    if(strings[0].equals("list")){
                        File itemsFile = new File(plugin.getDataFolder(),"loot.yml");
                        if(itemsFile.exists()){
                            FileConfiguration items = YamlConfiguration.loadConfiguration(itemsFile);
                            List<Map<?, ?>> itemlist = items.getMapList("loot.items");
                            if(itemlist.size()>0){
                                for(int i =0;i<itemlist.size();i++){
                                    p.sendMessage(ChatColor.AQUA+" "+(i+1)+" - "+ItemStack.deserialize((Map<String, Object>) itemlist.get(i)).getType().toString()+" "+ItemStack.deserialize((Map<String, Object>) itemlist.get(i)).getAmount());
                                }
                            }
                        }
                    }
                    
                    if(strings[0].equals("debug")){
                        plugin.debug = !plugin.debug;
                        if(!plugin.debug){
                            p.sendMessage(ChatColor.DARK_RED+Locale.debug_disable);
                        }else{
                            p.sendMessage(ChatColor.DARK_GREEN+Locale.debug_enable);
                        }
                    }
                    
                    if(strings[0].equals("rate")){
                        if(strings.length==2){
                            if(tryParseInt(strings[1])){
                                plugin.chance = Integer.parseInt(strings[1]);
                                p.sendMessage(ChatColor.DARK_GREEN+"Rate changed to "+plugin.chance);
                            }
                        }else{
                            p.sendMessage(ChatColor.DARK_RED+Locale.wrong_cmd_rate);
                        }
                        
                    }
                    
                    if(strings[0].equals("repair")){
                        YmlDataControl.repairOnlinePlayers();
                        p.sendMessage(ChatColor.AQUA+Locale.repair);
                    }
                    
                    if(strings[0].equals("tools")){
                        FilterAddTool.toolGive(p);
                        FilterRemoveTool.toolGive(p);
                        p.sendMessage(ChatColor.GREEN+Locale.tools);
                    }
                    
                    if(strings[0].equals("locale")){
                        Locale.reloadLocale(plugin.getConfig().getString("language"));
                        p.sendMessage(ChatColor.GREEN+Locale.locale_reload);
                    }
                    
                    if(strings[0].equals("filter")){
                        if(strings[1].equals("add")){
                            ItemFilter.add(p);
                        }
                        if(strings[1].equals("remove")){
                            ItemfilterRemove.displayFilters(p,0);
                        }
                        if(strings[1].equals("list")){
                            ItemFilter.add(p);
                        }
                    }
                }else{
                    p.sendMessage(ChatColor.LIGHT_PURPLE+Locale.no_perms);
                }
            }else{
                
                LootInventory.displayLoot(p);
            }
        }else{
            cs.sendMessage(Locale.no_player);
        }
        
        
        return true;
    }
    
    boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
}
