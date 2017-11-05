package com.ylinor.brawlator.data.handler;

import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.SpawnerAction;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import com.ylinor.brawlator.serializer.EffectSerializer;
import com.ylinor.brawlator.serializer.EquipementSerialiser;
import com.ylinor.brawlator.serializer.MonsterSerializer;
import com.ylinor.brawlator.serializer.SpawnerSerializer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.event.entity.SpawnEntityEvent;

import javax.inject.Inject;
import javax.xml.bind.Marshaller;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;


public class ConfigurationHandler {

    private static ConfigurationLoader<CommentedConfigurationNode> monsterConfigLoader;
    private static ConfigurationLoader<CommentedConfigurationNode> spawnerConfigLoader;
    private static CommentedConfigurationNode monster;
    private static CommentedConfigurationNode spawner;

    public static void setMonsterConfiguration(CommentedConfigurationNode commentedConfigurationNode){
        monster = commentedConfigurationNode;
    }

    public static void setSpawnerConfiguration(CommentedConfigurationNode commentedConfigurationNode){
        spawner = commentedConfigurationNode;
    }

    public static CommentedConfigurationNode loadMonsterConfiguration(String configName) {
        monsterConfigLoader = HoconConfigurationLoader.builder().setPath(Paths.get(configName)).build();
        CommentedConfigurationNode configNode = null;
        try {
            configNode = monsterConfigLoader.load();
            Brawlator.getLogger().info(configName + " was loaded successfully");
        } catch (IOException e) {
            Brawlator.getLogger().error("Error while loading configuration " + configName + " : " + e.getMessage());
        }
        return configNode;
    }
    public static CommentedConfigurationNode loadSpawnerConfiguration(String configName) {
        spawnerConfigLoader = HoconConfigurationLoader.builder().setPath(Paths.get(configName)).build();
        CommentedConfigurationNode configNode = null;
        try {
            configNode = spawnerConfigLoader.load();
            Brawlator.getLogger().info(configName + " was loaded successfully");
        } catch (IOException e) {
            Brawlator.getLogger().error("Error while loading configuration " + configName + " : " + e.getMessage());
        }
        return configNode;
    }




    public static void save(){
        try {
            ConfigurationHandler.serializeMonsterList(MonsterDAO.monsterList);
            spawnerConfigLoader.save(spawner);
            monsterConfigLoader.save(monster);

        } catch(IOException e) {
            // error
        }
    }

    public static Optional<List<SpawnerBean>> getSpawnerList(){
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(SpawnerBean.class), new SpawnerSerializer());
        try {
           List<SpawnerBean> list = spawner.getNode("spawner").getList(TypeToken.of(SpawnerBean.class));
            return Optional.ofNullable(list);

        } catch (Exception e){
            Brawlator.getLogger().error(e.toString());
            return Optional.empty();
        }
    }

    public static Optional<List<MonsterBean>> getMonsterList(){
        TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer())
                .registerType(TypeToken.of(EffectBean.class), new EffectSerializer())
                .registerType(TypeToken.of(EquipementBean.class), new EquipementSerialiser());
        try {
            List<MonsterBean> list = monster.getNode("monster").getList(TypeToken.of(MonsterBean.class));
            if (!list.isEmpty()) {
               return Optional.of(list);
            }
            return Optional.empty();
            }
            catch (Exception e) {
            Brawlator.getLogger().error(e.getMessage());
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
