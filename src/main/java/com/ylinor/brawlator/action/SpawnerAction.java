package com.ylinor.brawlator.action;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3d;
import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.dao.SpawnerDAO;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.block.SolidCubeProperty;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityTypes;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import java.util.Optional;
import java.util.function.Predicate;


public class SpawnerAction {
    /**
     *get a spawnable lo
     * @param spawnerBean the spawner
     * @return a spawnable location
     */
    public Vector2i getLocation(SpawnerBean spawnerBean){
        int range = spawnerBean.getRange();
        long x;
        long y;
        Vector3i position;
        do {
            position = spawnerBean.getPosition();
            x = range - Math.round(Math.random()*range *2);
            y = range - Math.round(Math.random()*range *2);

            position.add(x, 0, y);
        }while (isAir(position));
        return new Vector2i(spawnerBean.getPosition().getX()+x,spawnerBean.getPosition().getY()+y);
    }
    /**
     * look if a location is free to spawn an entity
     * @param spawnLocation the position to check
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
     * periodicly update all spawner time and unused delete spawner
     */
    public static void updateSpawner() {

        Brawlator.getLogger().info("updating "+ SpawnerDAO.spawnerList.size() + " spawner");
        for (SpawnerBean spawnerBean : SpawnerDAO.spawnerList) {

            if (Brawlator.getWorld().getLocation(spawnerBean.getPosition()).getBlockType() == BlockTypes.BARRIER) {
               if (!isEnoughEntity(spawnerBean)) {
                    spawnerBean.updateTime();
                }
            }
        }
    }
    /**
     * get the number of entity near the spawner
     * @param spawnerBean
     * @return
     */
    private static int getEntities(SpawnerBean spawnerBean) {
        //Entity entity = Brawlator.getWorld().createEntity(EntityTypes.,spawnerBean.getPosition());
        //Brawlator.getLogger().info("entité proche" + entity.getNearbyEntities(spawnerBean.getRange()+10).size());
       int qte = Brawlator.getWorld().getLocation(spawnerBean.getPosition()).getExtent().getEntities(
                entity -> compare(entity,spawnerBean)).size();
        Brawlator.getLogger().info("there is "+qte + "entities near "+spawnerBean.getPosition().toString());
    return qte;
    }

    /**
     * Check if there is enough entity spawned  around a spawner
     * @param spawnerBean the spawner to check
     * @return true if is enough false if not
     */
    private static Boolean isEnoughEntity(SpawnerBean spawnerBean){
        return getEntities(spawnerBean)>=spawnerBean.getQuantity();
    }

    private static boolean compare(Entity entity, SpawnerBean spawnerBean){
        Optional<Text> nameOpt = entity.get(Keys.DISPLAY_NAME);
        if (entity.getLocation().getPosition().distance(spawnerBean.getPosition().toDouble())<50){
            if(nameOpt.isPresent()){
                Text name = nameOpt.get();
                Brawlator.getLogger().info(name.toPlain());
                boolean eqname = name.toPlain().equals(spawnerBean.getMonsterBean().getName());

            } else {
                Brawlator.getLogger().warn("Entity name not present");
                return false;
            }
        }
        return false;
    }


}
