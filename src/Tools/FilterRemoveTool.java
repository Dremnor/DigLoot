/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package Tools;

import Language.Locale;
import java.util.ArrayList;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Mokito
 */
public class FilterRemoveTool {
    public static ItemStack addtool = new ItemStack(Material.PAPER, 1);
    
    static {
        ItemMeta itemMeta = addtool.getItemMeta();
        itemMeta.setDisplayName(Locale.tool_filter_remove);
        ArrayList<String> lore = new ArrayList<String>();
        lore.add(ChatColor.GOLD+Locale.tool_filter_remove);
        lore.add("=========");
        lore.add(ChatColor.GOLD+"Right click item to remove it from Filter");
        itemMeta.setLore(lore);
        addtool.setItemMeta(itemMeta);
    }
    
    public static void toolGive(Player p){
        if(p.getInventory().firstEmpty()!=-1){
            p.getInventory().addItem(addtool);
        }else{
            p.sendMessage(ChatColor.DARK_RED+Locale.no_slot_for_tools);
        }
    }
}
