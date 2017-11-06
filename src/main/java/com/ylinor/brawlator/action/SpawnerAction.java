package com.ylinor.brawlator.action;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.SpawnerBean;

import com.ylinor.brawlator.data.dao.SpawnerDAO;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.property.block.SolidCubeProperty;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.world.World;

import org.spongepowered.api.entity.Entity;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.Collection;


public class SpawnerAction {

    /**
     *
     * @param spawnerBean
     * @return
     */
    public Vector2i getLocation(SpawnerBean spawnerBean){
        int range = spawnerBean.getRange();
        long x;
        long y;
        Vector3i position;
        do {
            position = spawnerBean.getPosition();
             x =range - Math.round(Math.random()*range *2);
             y = range - Math.round(Math.random()*range *2);

            position.add(x, 0, y);
        }while (isAir(position));
        return new Vector2i(spawnerBean.getPosition().getX()+x,spawnerBean.getPosition().getY()+y);

    }
    /**
     * look if a location is free to spawn an entity
     * @param spawnLocation
     * @return if the location is free to spawn an entity
     */
    public boolean isAir(Vector3i spawnLocation) {
        World world = Brawlator.getWorld();
        boolean traversable;
        int check = 0;
        while (check < 2 || spawnLocation.getY()<256){
            spawnLocation.add(new Vector3i(0,1,0));
            BlockState blockState  = world.getLocatableBlock(spawnLocation).getBlockState();
            traversable =  blockState.getProperty(SolidCubeProperty.class).get().getValue();

            if(traversable){
                check++;
            }
        }
        if(check == 2){
            return true;
        }
        return false;
    }

    /**
     * update all spawner time
     */
    public static void updateSpawner() {
        Brawlator.getLogger().info("update");
        for (SpawnerBean spawnerBean : SpawnerDAO.spawnerList) {
            spawnerBean.updateTime();
        }
    }


    public int getEntities(SpawnerBean spawnerBean) {

        Entity entity = Brawlator.getWorld().createEntity(EntityTypes.PLAYER,spawnerBean.getPosition());
        Brawlator.getLogger().info("entité proche" + entity.getNearbyEntities(spawnerBean.getRange()+10).size());
    return entity.getNearbyEntities(spawnerBean.getRange()+10).size();
    }


}
