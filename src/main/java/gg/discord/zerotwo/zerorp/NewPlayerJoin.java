package gg.discord.zerotwo.zerorp;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import gg.discord.zerotwo.zerorp.Main;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.logging.Logger;

public final class NewPlayerJoin implements Listener {
    private final Main plugin;
    private Logger log;
    PlayerParticlesAPI ppAPI;

    public NewPlayerJoin(Main plugin, Logger log, PlayerParticlesAPI ppAPI){
        this.plugin = plugin;
        this.log = log;
        this.ppAPI = ppAPI;
    }

    @EventHandler
    public void onNewPlayerJoin(PlayerJoinEvent e){
        Player target = e.getPlayer();
        if(!target.hasPlayedBefore()){
            log.info("Player is New!");
            target.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE,1200,255),true);
            target.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,600,0),true);

            Location targetLoc = target.getLocation();
            target.getWorld().spawnParticle(Particle.DRAGON_BREATH,targetLoc,10,0,-20,0);
            target.teleport(new Location(targetLoc.getWorld(),targetLoc.getX(),targetLoc.getY()+50,targetLoc.getZ()));
            target.getWorld().strikeLightningEffect(targetLoc);
            target.getWorld().playSound(targetLoc, Sound.BLOCK_END_PORTAL_SPAWN, SoundCategory.PLAYERS,1,1);
            e.setJoinMessage("§4§l┇ §c Die Götter haben jemand neues gefunden, der dem Königreich dienen kann, willkommen in Teatopia "+target.getName());

            ItemStack book = new ItemStack(Material.WRITTEN_BOOK);
            BookMeta bookMeta = (BookMeta) book.getItemMeta();

            ArrayList<String> pages = new ArrayList<>();
            pages.add(ChatColor.BOLD+"Willkommen\nReisender!\n\n"+ChatColor.RESET+"Du stehst mitten im\nHerzen Teatopias, dem\nKönigreich von\n"+ChatColor.RED+"GravatonPlay"+ChatColor.RESET+"! Ohne\nStatus, Reichtümer und\nVerbündete ist es nun\nan dir, deinem\nCharakter Leben\neinzuhauchen...");
            pages.add(ChatColor.BOLD+"Kurzanleitung\n\n"+ChatColor.RESET+"Um einen Beruf und\nweitere Informationen\nzu erhalten,\nwende dich per "+ChatColor.GRAY+"/msg\n"+ChatColor.RESET+"an:\n"+ChatColor.BLUE+"Akyzel "+ChatColor.RESET+"(Farmer)\n"+ChatColor.BLUE+"Ayaki "+ChatColor.RESET+"(Förster)\n"+ChatColor.BLUE+"Merloma "+ChatColor.RESET+"(Gräber)\n"+ChatColor.BLUE+"Juli1706 "+ChatColor.RESET+"(Miner)\n"+ChatColor.BLUE+"MickySie"+ChatColor.RESET+" (Abenteurer)");
            pages.add("Sicher fragst du dich:\nAber wie soll ich meine\nGeschichte beginnen?");
            pages.add("Nun als Erstes\nbrauchst du ein\nsicheres Dach über\ndem Kopf!\n\nDie vier Adelshäuser\nrepräsentieren die vier Berufe, von\nwelchen du einen\nwählen kannst, indem\ndu bei ihnen vorstellig\nwirst.");
            pages.add("Das nördliche an der\nStadt angrenzende\nAdelsgebiet der\nHerzogin "+ChatColor.BLUE+"Akyzel,"+ChatColor.RESET+"\nwelche die Farmer des\nLandes verwaltet.\n\nDas östliche\nAdelsgebiet der\nFürstin "+ChatColor.BLUE+"Ayaqui,"+ChatColor.RESET+" welche\ndie Försterdes\nLandes verwaltet.");
            pages.add("Das westliche\nAdelsgebiet des\nLandgrafen "+ChatColor.BLUE+"Merloma,"+ChatColor.RESET+"\nwelcher die Gräber\ndes Landes verwaltet.\n\nUnd das hoch im\nNorden liegende\nAdelsgebiet des\nPfalzgrafen"+ChatColor.BLUE+"Juli1706,"+ChatColor.RESET+"\nwelcher die Miner\nverwaltet.");
            pages.add("Es sind gefährliche\nZeiten und es ist\nwichtig im Schutze des\nAdels auf dessen\nLand, geschützt von\nWachen und Göttern\nzu leben und die\nGesetze des\nKönigreiches\neinzuhalten.");
            pages.add("Tust du dies\nallerdings nicht und\nlebst gesetzlos in den\nWäldern, so wird dir\nniemand zu Hilfe eilen\nund du bist Räubern\nund Banditen\nschutzlos\nausgeliefert!");
            pages.add("Falls du jedoch nach\nAbenteuern suchst,\nwäre vielleicht die\nGilde etwas für dich!\nDie Gildenmeisterin\n"+ChatColor.DARK_AQUA+"MickySie "+ChatColor.RESET+"vergibt\nQuests an\nunerschrockene\nAbenteurer, welche\nihren Lebensunterhalt\nmit den erfüllen eben\ndieser bestreiten wollen.");;
            pages.add("Wenn du dich als\nAbenteurer anmelden\nwillst, solltest du das\nGildenhaus aufsuchen.\nGib jedoch Acht, was\ndu bei diesen Reisen\nverlierst, das bleibt\ndir auf immer\nverloren.");
            pages.add("Du kannst die Adeligen\nund Gildenmeister per\nBrieftaube ("+ChatColor.GRAY+"/msg"+ChatColor.RESET+")\nerreichen, diese\nkosten dich allerdings\n2,5 Goldmünzen.\n\nDank der\nGroßzügigkeit unseres\nKönigs "+ChatColor.RED+"GravatonPlay\n"+ChatColor.RESET+"erhältst du als neuer\nBürger des\nKönigreichs Teatopia\n250 Goldmünzen.");
            pages.add("Dies soll dir auf den\nBeginn deines Weges\nhelfen, wohin auch\nimmer dieser führen\nsoll.");

            bookMeta.setPages(pages);
            bookMeta.setAuthor("Das ServerTeam");
            bookMeta.setTitle("Startet-Guide");
            book.setItemMeta(bookMeta);
            e.getPlayer().getInventory().addItem(book);



        }else{
            PersistentDataContainer targetData = target.getPersistentDataContainer();
            if(targetData.get(new NamespacedKey(plugin,"curse"), PersistentDataType.INTEGER) != null &&
                    targetData.get(new NamespacedKey(plugin,"curse"), PersistentDataType.INTEGER) == 1) {
                new BukkitRunnable() {
                    public void run() {
                        ppAPI.resetActivePlayerParticles(target);
                    }
                }.runTaskLater(this.plugin, 20L * 900);
            }
        }
    }
}