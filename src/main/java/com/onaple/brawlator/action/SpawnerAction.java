package com.onaple.brawlator.action;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.SpawnerBean;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import com.onaple.brawlator.exception.WorldNotFoundException;
import org.slf4j.Logger;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.property.block.SolidCubeProperty;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Optional;

@Singleton
public class SpawnerAction {
    @Inject
    private Logger logger;

    @Inject
    private ConfigurationHandler configurationHandler;

    public SpawnerAction() {
    }

    /**
     * get a spawnable lo
     *
     * @param spawnerBean the spawner
     * @return a spawnable location
     */
    public Vector2i getLocation(SpawnerBean spawnerBean) {
        int range = spawnerBean.getRange();
        long x;
        long y;
        Vector3i position;
        do {
            position = spawnerBean.getPosition();
            x = range - Math.round(Math.random() * range * 2);
            y = range - Math.round(Math.random() * range * 2);

            position.add(x, 0, y);
        } while (isAir(position));
        return new Vector2i(spawnerBean.getPosition().getX() + x, spawnerBean.getPosition().getY() + y);
    }

    /**
     * look if a location is free to spawn an entity
     *
     * @param spawnLocation the position to check
     * @return if the location is free to spawn an entity
     */
    public boolean isAir(Vector3i spawnLocation) {

        try {
            World world = Brawlator.getWorld("world");
            BlockState blockState = world.getLocatableBlock(spawnLocation).getBlockState();

            Optional<SolidCubeProperty> solidCubePropertyOptional = blockState.getProperty(SolidCubeProperty.class);
            if(solidCubePropertyOptional.isPresent()){
                return solidCubePropertyOptional.get().getValue();
            }else{
                return false;
            }
        } catch (NullPointerException e) {
            logger.error(e.getMessage());
            return false;
        } catch (WorldNotFoundException e) {
            logger.error(e.toString());
            return false;
        }
    }

    /**
     * periodicly update all spawner time and unused delete spawner
     */
    public void updateSpawner() {

        for (SpawnerBean spawnerBean : configurationHandler.getSpawnerList()) {

            try {
                if (Brawlator.getWorld("world").getLocation(spawnerBean.getPosition()).getBlockType() == BlockTypes.BARRIER) {
                    if (!isEnoughEntity(spawnerBean)) {
                        spawnerBean.updateTime();
                    }
                }
            } catch (WorldNotFoundException e) {
                logger.error(e.toString());

            }
        }
    }

    /**
     * get the number of entity near the spawner
     *
     * @param spawnerBean
     * @return
     */
    private int getEntities(SpawnerBean spawnerBean) {
        int quantity = 0;
        try {
            quantity = Brawlator.getWorld("world").getLocation(spawnerBean.getPosition()).getExtent().getEntities(
                    entity -> compareSpawnerToEntity(entity, spawnerBean)).size();
        } catch (WorldNotFoundException e) {
            logger.error(e.toString());
        }
        return quantity;
    }

    /**
     * Check if there are enough entities spawned around a spawner
     *
     * @param spawnerBean The spawner to check
     * @return true if is enough, else false
     */
    private Boolean isEnoughEntity(SpawnerBean spawnerBean) {
        return getEntities(spawnerBean) >= spawnerBean.getQuantity();
    }

    /**
     * Check that an entity is close enough from a spawner and that the entity matches the spawner content
     * @param entity Entity to compare
     * @param spawnerBean Spawner to compare
     * @return Boolean true if entity can be from the spawner
     */
    private boolean compareSpawnerToEntity(Entity entity, SpawnerBean spawnerBean) {
        Optional<Text> nameOpt = entity.get(Keys.DISPLAY_NAME);
        if (entity.getLocation().getPosition().distance(spawnerBean.getPosition().toDouble()) < spawnerBean.getRange() * 1.50) {
            if (nameOpt.isPresent()) {
                Text name = nameOpt.get();
                return name.toPlain().equals(spawnerBean.getMonsterBean().getName());
            }
        }
        return false;
    }


}
