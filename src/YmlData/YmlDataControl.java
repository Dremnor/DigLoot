/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package YmlData;

import Commands.CommandDl;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

/**
 *
 * @author Mokito
 */
public class YmlDataControl {
    private final static Main PLUGIN = Main.getPlugin(Main.class);
    
    public void createPlayer(UUID uuid){
        
        //Create Players folder
        File file = new File(PLUGIN.getDataFolder(),"Players");
        if (!file.exists()) {
            file.mkdir();
        }
        
        //Check if player data file exists and create if not
        File ymlitemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players",uuid.toString()+".yml");
        if(!ymlitemsFile.exists()){
            try {
                ymlitemsFile.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    public static void repairOnlinePlayers(){
        
        File file = new File(PLUGIN.getDataFolder(),"Players");
        if (!file.exists()) {
            file.mkdir();
        }
        
        //repair online players
        for (Player p : Bukkit.getOnlinePlayers()) {
            UUID uuid = p.getUniqueId();
            
            
            
            File ymlitemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,uuid.toString()+".yml");
            if(!ymlitemsFile.exists()){
                try {
                    ymlitemsFile.createNewFile();
                    FileConfiguration pdata = YamlConfiguration.loadConfiguration(ymlitemsFile);
                    pdata.set("inventory.items",new ArrayList<>());
                    pdata.set("reward.count",0);
                    pdata.set("reward.lvl",1);
                    pdata.save(ymlitemsFile);
                } catch (IOException ex) {
                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        //repair offline players
        for (OfflinePlayer p : Bukkit.getOfflinePlayers()) {
            UUID uuid = p.getUniqueId();
            
            
            
            File ymlitemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,uuid.toString()+".yml");
            if(!ymlitemsFile.exists()){
                try {
                    ymlitemsFile.createNewFile();
                    FileConfiguration pdata = YamlConfiguration.loadConfiguration(ymlitemsFile);
                    pdata.set("reward.count",0);
                    pdata.set("reward.lvl",1);
                    pdata.save(ymlitemsFile);
                } catch (IOException ex) {
                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public static boolean addItem(UUID uuid, Map<String, Object> item){
        File ymlitemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,uuid.toString()+".yml");
        if(ymlitemsFile.exists()){
            FileConfiguration pdata = YamlConfiguration.loadConfiguration(ymlitemsFile);
            List<Map<?, ?>> itemlist = pdata.getMapList("inventory.items");
            if(itemlist.size()<27){
                itemlist.add(item);
                pdata.set("inventory.items",itemlist);
                try {
                    pdata.save(ymlitemsFile);
                    return true;
                } catch (IOException ex) {
                    Logger.getLogger(YmlDataControl.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                return false;
            }
            
        }else {
            repairPlayer(uuid);
            addItem(uuid,item);
        }
        return true;
    }
    
    private static void repairPlayer(UUID uuid){
        File ymlitemsFile = new File(PLUGIN.getDataFolder()+File.separator+"Players"+File.separator,uuid.toString()+".yml");
        if(!ymlitemsFile.exists()){
            try {
                ymlitemsFile.createNewFile();
                FileConfiguration pdata = YamlConfiguration.loadConfiguration(ymlitemsFile);
                pdata.set("reward.count",0);
                pdata.set("reward.lvl",1);
                pdata.save(ymlitemsFile);
            } catch (IOException ex) {
                Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
