package com.ylinor.brawlator.data.handler;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.MonsterBean;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.slf4j.Logger;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

public class ConfigurationHandler {



    @Inject
    private Logger logger;


    private static CommentedConfigurationNode monster;

    public static void setConfiguration(CommentedConfigurationNode commentedConfigurationNode){
        monster = commentedConfigurationNode;
    }

    public static Optional<List<MonsterBean>> getMonsterList(){

        try {
            List<MonsterBean> list = monster.getNode("monster").getList(TypeToken.of(MonsterBean.class));
            if (!list.isEmpty()) {
               return Optional.of(list);
            }
            return Optional.empty();
            }
            catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }

    }

    public static void serializeMonsterList(List<MonsterBean> monsterList){
        final TypeToken<List<MonsterBean>> token = new TypeToken<List<MonsterBean>>() {};
        try {

            monster.getNode("monster").setValue(token, monsterList);

        } catch (ObjectMappingException e) {
            e.printStackTrace();
        }
    }






}
