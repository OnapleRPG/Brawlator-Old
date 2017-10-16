package com.ylinor.brawlator;

import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.data.beans.SpawnerBean;

public class SpawnerAction {

    public Vector3i getRandomLocation(SpawnerBean spawnerBean){
        Vector3i spawnLocation = spawnerBean.getPosition();
        while (isAir(spawnLocation)) {


        }
    return null;
    }
    public boolean isAir(Vector3i spawnLocation) {
       return true;
    }
}
