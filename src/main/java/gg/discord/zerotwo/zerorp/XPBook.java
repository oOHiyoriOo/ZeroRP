package gg.discord.zerotwo.zerorp;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Random;
import java.util.logging.Logger;

public final class XPBook implements Listener {
    private final Main plugin;
    private Logger log;

    public XPBook(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
    }

    @EventHandler
    public void XPBookUse(PlayerInteractEvent e){
        Player player = e.getPlayer();
        Block targetBlock = e.getClickedBlock();

        if(e.getItem() != null &&
            e.getItem().getType() == Material.WRITTEN_BOOK){

            ItemStack book = e.getItem();
            BookMeta bookMeta = (BookMeta) book.getItemMeta();
            PersistentDataContainer BookData = bookMeta.getPersistentDataContainer();

            if(BookData.get(new NamespacedKey(this.plugin,"stored_xp"), PersistentDataType.DOUBLE) != null){
                e.setCancelled(true);

                if(e.getHand().equals(EquipmentSlot.HAND) && !player.isSneaking()){
                    int MissingXP = player.getExpToLevel();
                    int storedXp = BookData.get(new NamespacedKey(this.plugin,"stored_xp"), PersistentDataType.DOUBLE).intValue();
                    int xp = 0;

                    if(storedXp > MissingXP){
                        xp = MissingXP;
                    }else{
                        xp = storedXp;
                    }

                    BookData.set(new NamespacedKey(this.plugin,"stored_xp"), PersistentDataType.DOUBLE,(double) (storedXp - xp));
                    ChatColor color = ChatColor.getByChar(Integer.toHexString(new Random().nextInt(16)));

                    bookMeta.setDisplayName(color+"XP Book: "+( (int)(storedXp - xp))+"XP");
                    book.setItemMeta(bookMeta);

                    player.giveExp(xp); // dont ask just do it.

                }else if(e.getAction() == Action.RIGHT_CLICK_BLOCK &&
                        targetBlock != null &&
                        targetBlock.getType() == Material.ENCHANTING_TABLE &&
                        e.getHand().equals(EquipmentSlot.HAND) &&
                        player.isSneaking() ){

                    double levelXP = (player.getExpToLevel() * player.getExp())*-1;

                    int xp = (int) levelXP-1;

                    if( (player.getLevel() > 0) ){
                        player.giveExp(xp);

                        double storedXp = BookData.get(new NamespacedKey(this.plugin,"stored_xp"), PersistentDataType.DOUBLE);
                        BookData.set(new NamespacedKey(this.plugin,"stored_xp"), PersistentDataType.DOUBLE,storedXp + (xp*-1));

                        ChatColor color = ChatColor.getByChar(Integer.toHexString(new Random().nextInt(16)));

                        bookMeta.setDisplayName(color+"XP Book: "+((int)(storedXp + (xp*-1)))+"XP");
                        book.setItemMeta(bookMeta);
                    }
                }
            }
        }
    }
}
