package com.alttd.xraydetector.xraydetector.events;

import com.alttd.xraydetector.xraydetector.Main;
import com.alttd.xraydetector.xraydetector.Objects.PlayerActivity;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Date;

public class Events implements Listener {

    private Main main;
    private ArrayList<PlayerActivity> playerActivityList;

    public Events() {
        main = Main.getInstance();
        this.playerActivityList = main.getMinedBlockList();
        main.getLogger().info(ChatColor.RED + "Initialized Event Listeners");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {

        Block block = e.getBlock();

        if (block.getType().equals(Material.DIAMOND_ORE) && block.getY() <= 16){
            main.getLogger().info(ChatColor.RED + block.getType().toString());
            for (PlayerActivity playerActivity : playerActivityList){

                if (e.getPlayer().equals(playerActivity.getPlayer())){

                    Date date = new Date();
                    long time = date.getTime();

                    playerActivity.addBlock(time, block);
                    return;
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e){

        for (PlayerActivity playerActivity: playerActivityList){

            if (e.getPlayer().equals(playerActivity.getPlayer())){

                playerActivityList.remove(playerActivity);
                main.getLogger().info(ChatColor.DARK_RED + "Removed Player");

                return;
            }
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){

        playerActivityList.add(new PlayerActivity(e.getPlayer()));
        main.getLogger().info(ChatColor.DARK_GREEN + "Added player");

    }

}
