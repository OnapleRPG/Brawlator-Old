package com.onaple.brawlator.data.beans;

import com.flowpowered.math.vector.Vector3i;
import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.event.SpawnEvent;
import org.spongepowered.api.Sponge;

public class SpawnerBean {

    private int id ;
    private Vector3i position;
    private MonsterBean monsterBean;
    private int quantity;
    private int spawnRate;
    private int range;

    private int time;

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public Vector3i getPosition() {
        return position;
    }

    public void setPosition(Vector3i position) {
        this.position = position;
    }

    public MonsterBean getMonsterBean() {
        return monsterBean;
    }

    public void setMonsterBean(MonsterBean monsterBean) {
        this.monsterBean = monsterBean;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getSpawnRate() {
        return spawnRate;
    }

    public void setSpawnRate(int spawnRate) {
        this.spawnRate = spawnRate;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public SpawnerBean(Vector3i position, MonsterBean monsterBean, int quantity, int spawnRate, int range) {
        this.position = position;
        this.monsterBean = monsterBean;
        this.quantity = quantity;
        this.spawnRate = spawnRate;
        this.range = range;
        this.time = spawnRate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MonsterBean)) return false;

        MonsterBean that = (MonsterBean) o;

        return this.getId() == that.getId();
    }


    @Override
    public String toString() {
        return "SpawnerBean{" + "\n"+
                "position=" + position + "\n"+
                ", monsterBean=" + monsterBean +"\n"+
                ", quantity=" + quantity +"\n"+
                ", spawnRate=" + spawnRate +"\n"+
                ", range=" + range +"\n"+
                '}';
    }

    @Override
    public int hashCode() {
        int result = position.hashCode();
        result = 31 * result + monsterBean.hashCode();
        return result;
    }

    public void updateTime(){
        if(this.time > 0){
            time--;
            Brawlator.getLogger().info("Spawner at " + position.toString() + " have "+ time);
        } else {
            time = spawnRate;

            SpawnEvent event = new SpawnEvent(this);
            Sponge.getEventManager().post(event);
        }

    }

}
