package challenges.challenges.iChallenges.Challenges;

import challenges.challenges.Challenges;
import challenges.challenges.Utils.Items.ItemBuilder;
import challenges.challenges.iChallenges.Settings.ISetting;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public abstract class Challenge implements Listener, IChallenge {

    protected Challenge instance;
    protected String name;
    protected String description;
    protected Material guiMaterial;
    protected boolean isEnabled;
    protected LinkedHashMap<String, Object> varsToSave = new LinkedHashMap<>();

    public boolean isEnabled() {
        return isEnabled;
    }

    public Challenge(String name, String description, Material guiMaterial, boolean firstValue) {
        instance = this;
        this.name = name;
        this.description = description;
        this.guiMaterial = guiMaterial;
        if(!Challenges.getInstance().getConfig().contains("challenges." + name + ".enabled")) {
            Challenges.getInstance().getConfig().set("challenges." + name + ".enabled", firstValue);
        }
        isEnabled = Challenges.getInstance().getConfig().getBoolean("challenges." + name + ".enabled");
        Bukkit.getPluginManager().registerEvents(this, Challenges.getInstance());
    }

    @Override
    public void load() {
        for(Map.Entry<String, Object> entry : varsToSave.entrySet()) {
            if(!Challenges.getInstance().getConfig().contains("challenges." + name + "." + entry.getKey())) {
                Challenges.getInstance().getConfig().set("challenges." + name + "." + entry.getKey(), entry.getValue());
            }
        }
        System.out.println("[" + getName() + "] Loaded successfully.");
    }

    @Override
    public void save() {
        for(Map.Entry<String, Object> entry : varsToSave.entrySet()) {
            System.out.println(entry.getKey() + " | " + entry.getValue());
            Challenges.getInstance().getConfig().set("challenges." + name + "." + entry.getKey(), entry.getValue());
        }
        Challenges.getInstance().getConfig().set("challenges." + name + ".enabled", isEnabled);
    }


    public Challenge getInstance() {
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

        List<String> descParts = Arrays.asList(getDescription().split("\n"));
        for(String descPart : descParts) {
            lore.add("§7" + descPart);
        }

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
