package com.ylinor.brawlator.data.dao;

import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpawnerDAO {
    public static List<SpawnerBean> spawnerList;

    public static void populate(){
        Optional<List<SpawnerBean>> spawnerListOp = ConfigurationHandler.getSpawnerList();
        if(spawnerListOp.isPresent()) {
            spawnerList = spawnerListOp.get();
        } else {
            spawnerList = new ArrayList<>();
        }
    }
}
