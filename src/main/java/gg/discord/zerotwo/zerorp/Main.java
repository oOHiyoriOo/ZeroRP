package gg.discord.zerotwo.zerorp;

import dev.esophose.playerparticles.api.PlayerParticlesAPI;
import gg.discord.zerotwo.zerorp.mobs.IgorZombie;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;


public final class Main extends JavaPlugin {
    private Logger log;
    private PlayerParticlesAPI ppAPI;

    @Override
    public void onEnable() {
        // Config Logic
        File file = new File("plugins/ZeroRP/config.yml");
        if(!file.exists()){
            this.saveDefaultConfig(); // only saved provided plugin conf is old doesn't exist.
        }

        if (Bukkit.getPluginManager().isPluginEnabled("PlayerParticles")) {
            this.ppAPI = PlayerParticlesAPI.getInstance();
        }

        // Plugin startup logic
        log = getLogger(); // get logger to spam the console o.O

        // infinity cookie (admin item :kek:)
        getServer().getPluginManager().registerEvents(new cookies(this,log), this);


        getServer().getPluginManager().registerEvents(new PumpkinHide(this,log), this);


        getServer().getPluginManager().registerEvents(new WealthTax(this,log), this);
        log.info("Registered: WealthTax");

        getServer().getPluginManager().registerEvents(new XPBook(this,log), this);
        getServer().getPluginManager().registerEvents(new onPlayerCraftItem(this,log), this);
        log.info("Registered: XPBook");


        getServer().getPluginManager().registerEvents(new NewPlayerJoin(this,log,this.ppAPI), this);
        log.info("Registerted NewPlayerJoin.");

        getServer().getPluginManager().registerEvents(new IgorZombie(this,log), this);
        log.info("Registerted IgorZombie.");

        // Register the recipes.
        registerRecipes();

        // register blood command
        getCommand("blood").setExecutor(new bloodCommand(this,log,this.ppAPI));

        getCommand("ride").setExecutor(new RideCommand(this,log));

        log.info("ZeroRP is now running!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        log.info("ZeroRP is now disabled!");
    }









    public void registerRecipes(){

        // Register XP Book recipe!
        ItemStack book = new ItemStack(Material.WRITTEN_BOOK,1);
        BookMeta bookMeta = (BookMeta) book.getItemMeta();
        bookMeta.setDisplayName(ChatColor.LIGHT_PURPLE+"XP Book: 0XP");
        PersistentDataContainer BookData = bookMeta.getPersistentDataContainer();
        BookData.set(new NamespacedKey(this,"stored_xp"), PersistentDataType.DOUBLE,0.0);

        bookMeta.setTitle("XP Book");
        bookMeta.setAuthor("TiNe WiTtLeR");
        bookMeta.addPage("Hömma wieso siehst du das hier? aber wenn du schon dabei bist, sag akyzel, dass sie stinkt.");

        book.setItemMeta(bookMeta);
        NamespacedKey key = new NamespacedKey(this, "xp_book");
        ShapedRecipe recipe = new ShapedRecipe(key,book);
        recipe.shape("LLL","LBL","LLL");
        recipe.setIngredient('L',Material.LAPIS_LAZULI);
        recipe.setIngredient('B',Material.BOOK);
        Bukkit.addRecipe(recipe);


        // Register XP Book recipe!
        ItemStack cookie = new ItemStack(Material.COOKIE,1);
        ItemMeta cookieMeta = cookie.getItemMeta();
        PersistentDataContainer cookieData = cookieMeta.getPersistentDataContainer();
        cookieData.set(new NamespacedKey(this,"infinity"), PersistentDataType.DOUBLE,1.0);
        cookie.addUnsafeEnchantment(Enchantment.ARROW_INFINITE,1);
        cookieMeta.setDisplayName("§4Infinity §aCookie");
        cookie.setItemMeta(cookieMeta);
        NamespacedKey cookieKey = new NamespacedKey(this, "infinity_cookie");
        ShapedRecipe cookieRecipe = new ShapedRecipe(cookieKey,cookie);
        cookieRecipe.shape("RCR","CCC","LCL");
        cookieRecipe.setIngredient('C',Material.COOKIE);
        cookieRecipe.setIngredient('R',Material.WITHER_ROSE);
        cookieRecipe.setIngredient('L',Material.EMERALD_BLOCK);
        Bukkit.addRecipe(cookieRecipe);
    }
}
