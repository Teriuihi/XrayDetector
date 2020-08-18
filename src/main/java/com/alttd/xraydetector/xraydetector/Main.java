package com.alttd.xraydetector.xraydetector;

import com.alttd.xraydetector.xraydetector.Objects.PlayerActivity;
import com.alttd.xraydetector.xraydetector.events.Events;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public final class Main extends JavaPlugin {

    private static Main instance;
    private ArrayList<PlayerActivity> minedBlockList;

    @Override
    public void onEnable() {
        instance = this;
        minedBlockList = new ArrayList<>();
        instance.getLogger().info("Starting X-ray Detector V0.1.2...");

        getServer().getPluginManager().registerEvents(new Events(), instance);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        this.getLogger().info("Closing X-ray Detector...");
    }

    public static Main getInstance() {
        return instance;
    }

    public ArrayList<PlayerActivity> getMinedBlockList() {return minedBlockList;}

}
