/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package me.Dremnor.DigLoot;


import Commands.CommandDl;
import Events.BreakBlock;
import Events.NewPlayer;
import InventoryMenu.LootInventory;
import MysqlData.MysqlDataCon;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Mokito
 */
public class Main extends JavaPlugin implements Listener {
    
    public MysqlDataCon db;
    public boolean debug = false;
    public int chance = 100;
    public boolean mysql = false;
    
    @Override
    public void onEnable(){
        
        //load config
        saveResource("config.yml", false);
        
        //Check data storage option
        this.mysql = this.getConfig().getBoolean("ues_mysql");
        
        //Database Connection
        if(mysql){
            this.db = new MysqlDataCon();
            try {
                this.db.setUp();
            } catch (SQLException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        //Events
        getServer().getPluginManager().registerEvents(new NewPlayer(), this);
        getServer().getPluginManager().registerEvents(new BreakBlock(), this);
        getServer().getPluginManager().registerEvents(new LootInventory(), this);
        
        //Commands
        this.getCommand("dl").setExecutor(new CommandDl());
        
        
        
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"DigLoot Enabled");
    }
    
    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"DigLoot Disabled");
    }
    
    
    
    
}
