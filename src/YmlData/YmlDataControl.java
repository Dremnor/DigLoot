/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package YmlData;

import Commands.CommandDl;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import me.Dremnor.DigLoot.Main;

/**
 *
 * @author Mokito
 */
public class YmlDataControl {
    Main plugin = Main.getPlugin(Main.class);
    
    public void createPlayer(UUID uuid){
        
        //Create Players folder
        File file = new File(plugin.getDataFolder(),"C:\\Players");
        if (!file.exists()) {
            file.mkdir();
        }
        
        //Check if player data file exists and create if not
        File ymlitemsFile = new File(plugin.getDataFolder()+"\\Players",uuid.toString()+".yml");
        if(!ymlitemsFile.exists()){
            try {
                    ymlitemsFile.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(CommandDl.class.getName()).log(Level.SEVERE, null, ex);
                }
        }
        
        
        
        
    }
    
    
}
