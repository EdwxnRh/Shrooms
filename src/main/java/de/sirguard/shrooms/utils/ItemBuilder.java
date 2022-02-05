package de.sirguard.shrooms.utils;

import net.kyori.adventure.text.Component;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.Arrays;

public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta itemMeta;

    public ItemBuilder(Material material){
        item = new ItemStack(material);
        itemMeta = item.getItemMeta();
    }

    public ItemBuilder setDisplayName(String name) {
        itemMeta.displayName(Component.text(name));
        return this;
    }

    public ItemBuilder setLeatherColor(Color color) {
        ((LeatherArmorMeta)itemMeta).setColor(color);
        return this;
    }

    public ItemBuilder setLore(String... lore){
        itemMeta.setLore(Arrays.asList(lore));
        return this;
    }

    public ItemStack build() {
        item.setItemMeta(itemMeta);
        return item;
    }
}
