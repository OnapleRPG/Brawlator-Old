package com.ylinor.brawlator.data.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LootTableBean {
    private Integer pool;
    private Integer item;
    private String itemname;
    private String name;

    public LootTableBean(Integer pool, Integer item, String itemname, String name) {
        this.pool = pool;
        this.item = item;
        this.itemname = itemname;
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

    public Integer getItem() {
        return item;
    }

    public void setItem(Integer item) {
        this.item = item;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
