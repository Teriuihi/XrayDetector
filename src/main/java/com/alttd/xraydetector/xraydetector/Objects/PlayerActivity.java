package com.alttd.xraydetector.xraydetector.Objects;

import com.alttd.xraydetector.xraydetector.Main;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.*;

public class PlayerActivity {

    private Player player;
    private Map<Long, Coordinates> storage;
    private Main main;

    public PlayerActivity(Player player) {
        this.main = Main.getInstance();
        this.player = player;
        storage = new HashMap<>();
    }

    public void addBlock(long time, Block block) {
        Coordinates c = new Coordinates(block.getX(), block.getY(), block.getZ());
        boolean inVein = checkVein(c);

        main.getLogger().info(ChatColor.AQUA + "Different vein: " + inVein);

        if (inVein) {
            main.getLogger().info(ChatColor.AQUA + "Storing a new block");
            storage.put(time, c);

        }

        if (isXraying()){
            main.getLogger().info(ChatColor.YELLOW + "Is xraying");

            main.getServer().getOnlinePlayers().forEach(e -> {
                if (e.hasPermission("xraydetector.notify")){
                    e.sendMessage(ChatColor.RED + "Player suspected of xraying at: " + block.getLocation());
                }

            });

        }

    }

    private boolean isXraying() {

        Date date = new Date();
        Long limitTime = date.getTime() - 900000;
        Coordinates lastCoords = null;
        int count = 0;

        for (Map.Entry<Long, Coordinates> entry : storage.entrySet()){

            if (lastCoords == null){
                lastCoords = entry.getValue();
                main.getLogger().info(ChatColor.GRAY + "lastCoords == null");
                continue;
            }

            Long time = entry.getKey();
            Coordinates coords = entry.getValue();

            if (time > limitTime){
                main.getLogger().info(ChatColor.GRAY + "time > limitTime");
                count++;
                if (!coords.isStraightLine(lastCoords)){
                    main.getLogger().info(ChatColor.GRAY + "coords not in straight line");
                    count++;
                }
            }

            lastCoords = coords;

            if (count > 6){
                return true;
            }
        }

        return false;
    }

    /**
     * Checks if the mined block is mined within a certain radius of another mined block.
     * @param coords The block which got mined.
     * @return if the block was in a certain radius of any of the other mined blocks by this player.
     */
    private boolean checkVein(Coordinates coords){
        final int minedBlockX = coords.getX() + 4;
        final int minedBlockY = coords.getY() + 3;
        final int minedBlockZ = coords.getZ() + 4;

        //TODO check if there is air above player head and if so ignore it this should work but it is untested
        Location location = player.getLocation();
        Block blockAt = player.getWorld().getBlockAt((int) Math.round(location.getX()), (int) Math.round(location.getY()) + 1, (int) Math.round(location.getZ()));
        if (blockAt.getType().equals(Material.AIR) || blockAt.getType().equals(Material.CAVE_AIR)){
            return false;
        }

        main.getLogger().info(ChatColor.GOLD + "" + coords.toString());

        for (Map.Entry<Long, Coordinates> entry : storage.entrySet()){
            Coordinates coordEntry = entry.getValue();
            final int entryBlockX = coordEntry.getX();
            final int entryBlockY = coordEntry.getY();
            final int entryBlockZ = coordEntry.getZ();

            if (!coords.equals(coordEntry)) {

                main.getLogger().info(ChatColor.ITALIC + "Not Same");

                for (int x = coords.getX() - 4; x <= minedBlockX; x++) {
                    for (int y = coords.getY() - 3; y <= minedBlockY; y++) {
                        for (int z = coords.getZ() - 4; z <= minedBlockZ; z++) {

                            if (entryBlockX == x && entryBlockY == y && entryBlockZ == z){
                                return false;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public Player getPlayer(){
        return player;
    }

}
