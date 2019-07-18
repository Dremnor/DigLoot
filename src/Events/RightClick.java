/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Events;

import InventoryMenu.ItemfilterRemove;
import Language.Locale;
import LootControll.ItemFilter;
import java.util.List;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Mokito
 */
public class RightClick implements Listener{
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player p = event.getPlayer();
        ItemStack ho = p.getInventory().getItemInMainHand();
        if(event.getPlayer().hasPermission("digloot.admin"))
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if(!ho.getType().equals(Material.AIR)){
                    ItemMeta meta = ho.getItemMeta();
                    List<String> lore = meta.getLore();
                    if(lore!=null)
                        if(lore.size()>0){
                            if(lore.get(0).equals(ChatColor.GOLD+Locale.tool_filter_add)){
                                event.setCancelled(true);
                                ItemFilter.add(p,event.getClickedBlock());
                            }
                            if(lore.get(0).equals(ChatColor.GOLD+Locale.tool_filter_remove)){
                                ItemfilterRemove.removeItem(p,event.getClickedBlock());
                                event.setCancelled(true);
                            }
                        }
                }
                
            }
            
        }
    }
}
