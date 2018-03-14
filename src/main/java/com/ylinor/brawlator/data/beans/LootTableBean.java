package com.ylinor.brawlator.data.beans;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class LootTableBean {
    private Optional<Integer> pool;
    private Optional<Integer> item;
    private String name;

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

    public void setName(String name) {
        this.name = name;
    }

    public LootTableBean() {

    }




}
