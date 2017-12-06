package com.ylinor.brawlator.data.dao;

import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class MonsterDAO {

    public static final List<MonsterBean> monsterList = new ArrayList<>();

    public static  Optional<MonsterBean> getMonster(String name){
        return monsterList.stream().filter(monsterBean -> Objects.equals(monsterBean.getName(),name)).findFirst();
    }

    public static void insert(MonsterBean monsterBean) {
        monsterList.add(monsterBean);
    }

    public static void delete(MonsterBean monsterBean) {
        //monsterList.stream().filter(monster ->  Objects.equals( monster.getName(),monsterBean.getName())).iterator().remove();
        monsterList.remove(monsterBean);
    }

    public static boolean update(MonsterBean monsterBean){
        Integer index = monsterList.indexOf(monsterBean);
        if (index != -1) {
            monsterList.set(index,monsterBean);
            return true;
        }
        return false;
    }
    public static List<String> getNameList(){
     return monsterList.stream().map(MonsterBean::getName).collect(Collectors.toList());
    }

    public static void populate(){
        Optional<List<MonsterBean>> monsterBeanList = ConfigurationHandler.getMonsterList();
        if(monsterBeanList.isPresent()) {
            monsterList.addAll(monsterBeanList.get());
        }

    }

}
