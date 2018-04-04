package com.ylinor.brawlator.action;

import com.flowpowered.math.vector.Vector2i;
import com.flowpowered.math.vector.Vector3i;
import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
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
    ConfigurationHandler configurationHandler;

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
        World world = Brawlator.getWorld();
        try {
            BlockState blockState = world.getLocatableBlock(spawnLocation).getBlockState();
            return blockState.getProperty(SolidCubeProperty.class).get().getValue();
        } catch (NullPointerException e) {
            Brawlator.getLogger().error(e.getMessage());
            return false;
        }
    }

    /**
     * periodicly update all spawner time and unused delete spawner
     */
    public void updateSpawner() {

        for (SpawnerBean spawnerBean : configurationHandler.spawnerList) {

            if (Brawlator.getWorld().getLocation(spawnerBean.getPosition()).getBlockType() == BlockTypes.BARRIER) {
                if (!isEnoughEntity(spawnerBean)) {
                    spawnerBean.updateTime();
                }
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
        int qte = Brawlator.getWorld().getLocation(spawnerBean.getPosition()).getExtent().getEntities(
                entity -> compare(entity, spawnerBean)).size();
        Brawlator.getLogger().info("there is " + qte + " entities near " + spawnerBean.getPosition().toString());
        return qte;
    }

    /**
     * Check if there is enough entity spawned  around a spawner
     *
     * @param spawnerBean the spawner to check
     * @return true if is enough false if not
     */
    private Boolean isEnoughEntity(SpawnerBean spawnerBean) {
        return getEntities(spawnerBean) >= spawnerBean.getQuantity();
    }

    private boolean compare(Entity entity, SpawnerBean spawnerBean) {
        Optional<Text> nameOpt = entity.get(Keys.DISPLAY_NAME);
        if (entity.getLocation().getPosition().distance(spawnerBean.getPosition().toDouble()) < spawnerBean.getRange() * 1.50) {
            if (nameOpt.isPresent()) {
                Text name = nameOpt.get();
                boolean eqname = name.toPlain().equals(spawnerBean.getMonsterBean().getName());
                return eqname;
            } else {
                return false;
            }
        }
        return false;
    }


}
