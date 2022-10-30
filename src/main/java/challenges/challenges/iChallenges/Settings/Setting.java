package challenges.challenges.iChallenges.Settings;

import challenges.challenges.Challenges;
import challenges.challenges.Utils.Items.ItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class Setting implements Listener, ISetting {

    protected Setting instance;
    protected String name;
    protected String description;
    protected Material guiMaterial;
    protected boolean isEnabled;
    protected LinkedHashMap<String, Object> varsToSave = new LinkedHashMap<>();

    public Setting(String name, String description, Material guiMaterial, boolean firstValue) {
        instance = this;
        this.name = name;
        this.description = description;
        this.guiMaterial = guiMaterial;
        if(!Challenges.getInstance().getConfig().contains("settings." + name + ".enabled")) {
            Challenges.getInstance().getConfig().set("settings." + name + ".enabled", firstValue);
        }
        isEnabled = Challenges.getInstance().getConfig().getBoolean("settings." + name + ".enabled");
        Bukkit.getPluginManager().registerEvents(this, Challenges.getInstance());
    }

    @Override
    public void load() {
        for(Map.Entry<String, Object> entry : varsToSave.entrySet()) {
            if(!Challenges.getInstance().getConfig().contains("settings." + name + "." + entry.getKey())) {
                Challenges.getInstance().getConfig().set("settings." + name + "." + entry.getKey(), entry.getValue());
            }
        }
    }

    @Override
    public void save() {
        for(Map.Entry<String, Object> entry : varsToSave.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
                Challenges.getInstance().getConfig().set("settings." + name + "." + entry.getKey(), entry.getValue());
        }
    }


    public Setting getInstance() {
        return instance;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Material getGuiMaterial() {
        return guiMaterial;
    }

    public LinkedHashMap<String, Object> getVarsToSave() {
        return varsToSave;
    }

    public ItemStack getGUIItem() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add("§7" + getDescription());
        lore.add("");
        String state = "§cDeaktiviert";
        if(isEnabled) state = "§aAktiviert";
        lore.add("§7» " + state);
        ItemStack output = new ItemBuilder(getGuiMaterial())
                .setDisplayName("§7§l» §e§l" + getName()
                ).setLore(lore)
                .setGlow(isEnabled)
                .build();
        return output;
    }

}
