package com.ylinor.brawlator;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.data.beans.SpawnerBean;

import com.ylinor.brawlator.data.dao.SpawnerDAO;

import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.property.block.SolidCubeProperty;
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
    public Vector3i getRandomLocation(SpawnerBean spawnerBean){
        Vector3i spawnLocation = spawnerBean.getPosition();
        long posx = Math.round(Math.random()* spawnerBean.getRange());
        long posy = Math.round(Math.random()* spawnerBean.getRange());
        return null;
    }

    /**
     * look if a location is free to spawn an entity
     * @param spawnLocation
     * @return if the location is free to spawn an entity
     */
    public boolean isSpawnable(Vector3i spawnLocation) {
        World world = Brawlator.getWorld();
        int heigth = 2;
        BlockState blockState;
        for(int i = 0 ; i <heigth ; i++){
            blockState = world.getBlock(spawnLocation.add(new Vector3i(0,heigth,0)));
            if (blockState.getProperty(SolidCubeProperty.class).get().getValue()){
                return false;
            }
        }
        return true;
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

    public Vector2i getLocation(SpawnerBean spawnerBean){
        int range = spawnerBean.getRange();
        long x =range - Math.round(Math.random()*range *2);
        long y = range - Math.round(Math.random()*range *2);

        spawnerBean.getPosition().add(x,0,y);

        return new Vector2i(spawnerBean.getPosition().getX()+x,spawnerBean.getPosition().getY()+y);

    }
    public Collection<Entity> getEntities(SpawnerBean spawnerBean) {
        throw new NotImplementedException();
    }

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
}
