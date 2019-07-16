/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Events;

import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Mokito
 */
public class ItemDrop implements Listener{
    @EventHandler
    public void onItemDrop (PlayerDropItemEvent event) {
        Player p = event.getPlayer();
        
        ItemMeta ItemMeta = event.getItemDrop().getItemStack().getItemMeta();
        List<String> lore = ItemMeta.getLore();
        if(lore!=null)
            if(lore.size()>0){
                if(lore.get(0).equals(ChatColor.GOLD+"Filter Add") || lore.get(0).equals(ChatColor.GOLD+"Filter Remove")){
                    event.getItemDrop().remove();
                    p.sendMessage(ChatColor.DARK_RED+"You Can't drop this item! Item Removed");
                }
            }
    }
}
