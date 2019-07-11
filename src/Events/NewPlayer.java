/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Events;

import YmlData.YmlDataControl;
import java.sql.SQLException;
import java.util.UUID;
import me.Dremnor.DigLoot.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;



/**
 *
 * @author Mokito
 */
public class NewPlayer implements Listener {
    Main plugin = Main.getPlugin(Main.class);
    
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) throws SQLException {
        Player p = event.getPlayer();
        UUID uuid = p.getUniqueId();
        if(!p.hasPlayedBefore())
            if(plugin.mysql){
                if(plugin.debug)System.out.println("Player: "+p.getName()+" Logged in! "+uuid.toString());
                if(!plugin.db.playerExist(uuid)){
                    plugin.db.playerCreate(p.getUniqueId(), p.getName());
                }
            }else{
                YmlDataControl ym = new YmlDataControl();
                ym.createPlayer(uuid);
            }
        
        
    }
    
    
}
