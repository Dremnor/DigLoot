/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Commands;

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
                            p.sendMessage(ChatColor.DARK_GREEN+"Item added to loot table \\0/");
                        }else{
                            p.sendMessage(ChatColor.DARK_RED+"Item already in loot table :o");
                        }

                    }else{
                        p.sendMessage(ChatColor.DARK_RED+"You need to hold item in hand :(");
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
                                    p.sendMessage(ChatColor.DARK_RED+"Wrong command parameters(/dl remove <item number from /dl list>) :(");
                                }
                            }else{
                                p.sendMessage(ChatColor.DARK_RED+"Wrong command parameters(/dl remove <item number from /dl list>) :(");
                            }                                                       
                        }else{
                            p.sendMessage(ChatColor.DARK_RED+"Wrong command parameters(/dl remove <item number from /dl list>) :(");
                        }                                                
                    }else{
                        p.sendMessage(ChatColor.DARK_RED+"You need to add some items first :(");
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
                        p.sendMessage(ChatColor.DARK_RED+"DigLoot Debug mode Disabled :<");
                    }else{
                        p.sendMessage(ChatColor.DARK_GREEN+"DigLoot Debug mode Enabled :>");
                    }
                }
                
                if(strings[0].equals("rate")){
                    if(tryParseInt(strings[1])){
                        plugin.chance = Integer.parseInt(strings[1]);
                    }
                }
                
                if(strings[0].equals("repair")){
                    YmlDataControl.repairOnlinePlayers();
                }
            }                                           
        }else{
            cs.sendMessage("Wrong use! Player only cmd");
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
