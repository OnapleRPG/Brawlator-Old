package com.ylinor.brawlator;

import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.dao.SpawnerDAO;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.block.BlockSnapshot;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.data.property.block.SolidCubeProperty;
import org.spongepowered.api.world.World;

import javax.inject.Inject;
import java.text.BreakIterator;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.logging.Logger;

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
    public static void updateSpawner(){
        Brawlator.getLogger().info("update");
        for (SpawnerBean spawnerBean : SpawnerDAO.spawnerList) {
            spawnerBean.updateTime();
        }
    }
}
