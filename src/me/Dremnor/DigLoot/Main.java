/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package me.Dremnor.DigLoot;


import Commands.CommandDl;
import Events.BreakBlock;
import Events.ItemDrop;
import Events.NewPlayer;
import Events.RightClick;
import InventoryMenu.ItemfilterRemove;
import InventoryMenu.LootInventory;
import Language.Locale;
import MysqlData.MysqlDataCon;
import java.io.File;
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
    public boolean itemfilter = false;
    public boolean is_filter_whitelist = false;
    
    
    @Override
    public void onEnable(){
        
        //load config
        File check = new File(getDataFolder(),"config.yml");
        if(!check.exists())
        saveResource("config.yml", false);
        
        check = new File(getDataFolder(),"locale.yml");
        if(!check.exists())
        saveResource("locale.yml", false);
        
        //Check data storage option
        //this.mysql = this.getConfig().getBoolean("ues_mysql");
        
        
        
        //Filter options
        this.itemfilter = this.getConfig().getBoolean("itemfilter");
        this.is_filter_whitelist = this.getConfig().getBoolean("is_filter_whitelist");
        
        //load locale
        Locale.reloadLocale(this.getConfig().getString("language"));
        
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
        getServer().getPluginManager().registerEvents(new ItemfilterRemove(), this);
        getServer().getPluginManager().registerEvents(new RightClick(), this);
        getServer().getPluginManager().registerEvents(new ItemDrop(), this);
        
        //Commands
        this.getCommand("dl").setExecutor(new CommandDl());
        
        
        
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@@@@@@@@@@@@@@@@@@@");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@                 @");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@ DigLoot Enabled @");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@ DigLoot Enabled @");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@                 @");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"@@@@@@@@@@@@@@@@@@@");
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_BLUE+"DigLoot used translation author: "+Locale.created_by);
        
    }
    
    @Override
    public void onDisable(){
        getServer().getConsoleSender().sendMessage(ChatColor.DARK_PURPLE+"DigLoot Disabled");
    }
    
    
    
    
}
