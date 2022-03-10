package gg.discord.zerotwo.zerorp.mobs;

import gg.discord.zerotwo.zerorp.Main;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Objects;
import java.util.logging.Logger;


public class IgorZombie implements Listener {
    private final Main plugin;
    private Logger log;

    public IgorZombie(Main plugin, Logger log){
        this.plugin = plugin;
        this.log = log;
    }

    public void SpawnZombie(Location loc, Player master){
        Zombie IZombie = Objects.requireNonNull(loc.getWorld()).spawn(loc,Zombie.class); // Spawn Zombie.
        Wolf IWolf = Objects.requireNonNull(loc.getWorld()).spawn(loc,Wolf.class);

        IZombie.setAI(false);
        IWolf.addPassenger(IZombie);

        PersistentDataContainer IZombieData = IZombie.getPersistentDataContainer(); // datacontainer
        IZombieData.set(new NamespacedKey(this.plugin,"izombie"), PersistentDataType.INTEGER,1);
        IZombieData.set(new NamespacedKey(this.plugin,"master"), PersistentDataType.STRING,""+master.getUniqueId());

        //
        IZombie.setArrowsInBody(49);
        // Set adult cuz baby ztombies are cacner
        IWolf.setAdult();
        IWolf.setTamed(true);
        IWolf.setOwner(master);

        // set health
        AttributeInstance IZAttribute = IWolf.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        assert IZAttribute != null;
        IZAttribute.setBaseValue(100);
        IWolf.setHealth(100);
    }

    @EventHandler
    public void IgorDamage(EntityDamageByEntityEvent e){
        Entity DamageCause = e.getDamager(); // who made the damage?

        PersistentDataContainer EntityData = e.getEntity().getPersistentDataContainer();
        boolean isIzombie = EntityData.get(new NamespacedKey(this.plugin, "izombie"), PersistentDataType.INTEGER) != null &&
                EntityData.get(new NamespacedKey(this.plugin, "izombie"), PersistentDataType.INTEGER) == 1;

        if(DamageCause instanceof Player && // damager was player
            DamageCause.hasPermission("igor.zombie") && // damager is allowed to make IZombie
                e.getEntity() instanceof Zombie && // entity that got damaged is a zombie
                !isIzombie){ // zombie is not already an IZombie

            Player player = (Player)DamageCause; // get the damager as player obj.
            if(player.getInventory().getItemInMainHand().getType().equals(Material.EMERALD)){ // is the player is holding an emerald
                player.getWorld().strikeLightningEffect(e.getEntity().getLocation()); // spawn lightning
                Location SpawnLocation = e.getEntity().getLocation(); // get location of old zombie
                e.getEntity().remove(); // remove the old zombie
                SpawnZombie(SpawnLocation,player); // spawn the iZombie
            }
        }
    }
}
