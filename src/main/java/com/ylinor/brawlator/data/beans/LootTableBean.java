package com.ylinor.brawlator.data.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LootTableBean {
    private Optional<Integer> pool;
    private Optional<Integer> item;
    private Optional<String> itemname;
    private String name;


    public Optional<String> getItemname() { return itemname; }

    public void setItemname(Optional<String> itemname) { this.itemname = itemname; }

    public Optional<Integer> getPool() {
        return pool;
    }

    public void setPool(Optional<Integer> pool) {
        this.pool = pool;
    }

    public Optional<Integer> getItem() {
        return item;
    }

    public void setItem(Optional<Integer> item) {
        this.item = item;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) { this.name = name; }

    public LootTableBean() {
        this.item = Optional.empty();
        this.itemname = Optional.empty();
        this.pool = Optional.empty();
    }

    public LootTableBean(Integer pool, Integer item, String itemname, String name) {
        if(pool !=0){
            this.pool = Optional.of(pool);
        }
        if(item !=0){
            this.item = Optional.of(item);
        }
        if(itemname != null){
            this.itemname = Optional.of(itemname);
        }
        this.name = name;
    }
}
