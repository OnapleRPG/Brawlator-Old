package com.onaple.brawlator.data.beans;

import org.spongepowered.api.item.inventory.ItemStack;

import java.util.List;

public class LootTableBean {
    private Integer pool;
    private List<ItemStack> items;

    private String name;

    public LootTableBean(String name) {
        this.name = name;
    }
    public LootTableBean(String name, List<ItemStack> items){
        this.name = name;
        this.items= items;
    }

    public LootTableBean(Integer pool, String name) {
        this.pool = pool;
        this.name = name;
    }

    public LootTableBean() {

    }

    public Integer getPool() {

        return pool;
    }

    public void setPool(Integer pool) {
        this.pool = pool;
    }

    @Override
    public String toString() {
        return "LootTableBean{" +
                "pool=" + pool +
                ", items=" + items +
                ", name='" + name + '\'' +
                '}';
    }

    public List<ItemStack> getItems() {
        return items;
    }

    public void setItems(List<ItemStack> items) {
        this.items = items;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
