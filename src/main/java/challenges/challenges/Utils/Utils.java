package challenges.challenges.Utils;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class Utils {

    public static void FillInventoryWithMaterial(Inventory inv, Material material) {
        int invSlots = inv.getSize();
        for(int i = 0; i < invSlots; i++) {
            inv.setItem(i, new ItemStack(material));
        }
    }

    public static void FillInventoryWithMaterial(Inventory inv, Material material, int slots) {
        int invSlots = slots;
        for(int i = 0; i < invSlots; i++) {
            inv.setItem(i, new ItemStack(material));
        }
    }

}
