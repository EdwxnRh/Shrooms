package de.sirguard.shrooms.listener;

import de.sirguard.shrooms.utils.GameManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class BlockBreakListener implements Listener {

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if(GameManager.playersInBuildMode.contains(event.getPlayer()))
            return;

        for (Location spawnerLocation : GameManager.spawnerLocations) {
            if (spawnerLocation.distance(event.getBlock().getLocation()) == 0) {
                event.getBlock().getWorld().dropItem(event.getBlock().getLocation().add(0, 1, 0), new ItemStack(Material.RED_MUSHROOM));
            }
        }
        if(event.getBlock().getType() == Material.BRICKS) {
            event.setDropItems(false);
            return;
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onTntBreak(EntityExplodeEvent event) {
        ArrayList<Block> blocks = new ArrayList<>();

        for (Block block : event.blockList()) {
            if(block.getType() == Material.SMOOTH_STONE) {
                blocks.add(block);
            }
        }
        event.blockList().clear();
        event.blockList().addAll(blocks);
        event.setYield(0);
    }
}
