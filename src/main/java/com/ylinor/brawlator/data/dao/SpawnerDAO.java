package com.ylinor.brawlator.data.dao;

import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
    public static void insert(SpawnerBean spawnerBean) {
        spawnerList.add(spawnerBean);
    }

    public static boolean update(SpawnerBean spawnerBean){
        Integer index = spawnerList.indexOf(spawnerBean);
        if (index != -1) {
            spawnerList.set(index,spawnerBean);
            return true;
        }
        return false;
    }

    public static void delete(SpawnerBean spawnerBean) {
        spawnerList.stream().filter(spawner ->  Objects.equals( spawner.getId(),spawnerBean.getId())).iterator().remove();
    }

}
