package challenges.challenges.Utils.Heads;

import com.google.j2objc.annotations.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemFlag;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.profile.PlayerTextures;

public class Heads {

    public static String getValue(final Head head) {
        return head.Value;
    }

    public static ItemStack Back() {
        final ItemStack itemStack = createCustomHead(getValue(Head.WOOD_ARROW_LEFT));
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ArrayList<String> itemLore = new ArrayList<String>();
        itemMeta.setDisplayName("§eZur\u00fcck");
        itemLore.add(" ");
        itemLore.add("§7Bringt dich auf die vorherige Seite.");
        itemLore.add(" ");
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        itemMeta.setLore((List)itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack Next() {
        final ItemStack itemStack = createCustomHead(getValue(Head.WOOD_ARROW_RIGHT));
        final ItemMeta itemMeta = itemStack.getItemMeta();
        final ArrayList<String> itemLore = new ArrayList<String>();
        itemMeta.setDisplayName("§eWeiter");
        itemLore.add(" ");
        itemLore.add("§7Bringt dich auf die n\u00e4chste Seite.");
        itemLore.add(" ");
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_DESTROYS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_PLACED_ON });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_POTION_EFFECTS });
        itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_UNBREAKABLE });
        itemMeta.setLore((List)itemLore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public enum Head
    {
        WOOD_ARROW_LEFT("bd69e06e5dadfd84e5f3d1c21063f2553b2fa945ee1d4d7152fdc5425bc12a9"),
        WOOD_ARROW_RIGHT("19bf3292e126a105b54eba713aa1b152d541a1d8938829c56364d178ed22bf");

        private final String Value;

        private Head(final String Value) {
            this.Value = Value;
        }
    }

    public static ItemStack createCustomHead(String headID) {
        ItemStack head = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        PlayerProfile profile = Bukkit.createPlayerProfile(UUID.randomUUID());
        PlayerTextures textures = profile.getTextures();
        try {
            textures.setSkin(new URL("https://textures.minecraft.net/texture/" + headID));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        meta.setOwnerProfile(profile);
        head.setItemMeta(meta);
        return head;
    }

}
