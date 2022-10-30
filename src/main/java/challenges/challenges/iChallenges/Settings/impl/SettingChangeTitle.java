package challenges.challenges.iChallenges.Settings.impl;

import challenges.challenges.iChallenges.Settings.Setting;
import org.bukkit.Material;

public class SettingChangeTitle extends Setting {

    public SettingChangeTitle(String name, String description, Material guiMaterial, boolean firstValue) {
        super(name, description, guiMaterial, firstValue);
    }

    @Override
    public void start() {
        super.load();
        System.out.println("[" + name + "] Loaded successfully.");
    }

    @Override
    public void stop() {
        super.save();
    }
}
