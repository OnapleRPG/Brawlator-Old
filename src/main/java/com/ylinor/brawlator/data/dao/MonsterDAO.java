package com.ylinor.brawlator.data.dao;

import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MonsterDAO {

    public Optional<MonsterBean> getMonster(String name){
        List<MonsterBean> monsterBeanList = ConfigurationHandler.getMonsterList();
      return   monsterBeanList.stream().filter(monsterBean -> Objects.equals(monsterBean.getName(),name)).findFirst();
    }


    public void insert(MonsterBean monsterBean) {
        throw new NotImplementedException();
    }
}
