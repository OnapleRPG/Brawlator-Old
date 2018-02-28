package com.ylinor.brawlator.action;

import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import com.ylinor.brawlator.exception.EntityTypeNotFound;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.event.cause.entity.spawn.SpawnTypes;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.*;

public class MonsterAction {

    /**
     * Invoque a monster at a specific position
     *
     * @param world Instance of the world
     * @param location Spawn 's Emplacement
     * @param monster Bean of the monster and his characteristic
     * @return Optional entity spawned
     */
    public static Optional<Entity> invokeMonster(World world, Location location, MonsterBean monster) throws EntityTypeNotFound {
        //  Vérification que le type du monstre existe pour le plugin
        if (!MonsterBean.monsterTypes.containsKey(monster.getType())) {
            Brawlator.getLogger().warn("Le type du monstre n'existe pas");
            throw new EntityTypeNotFound(monster.getType());
        }
        //  Création de l'entité
       Brawlator.getLogger().info(monster.toString());
        Entity entity = world.createEntity(MonsterBean.monsterTypes.get(monster.getType()), location.getPosition());
        if(entity instanceof  DataHolder) {
            entity =(Entity) editCharacteristics( (DataHolder) entity, monster.getName(), monster.getHp(), monster.getAttackDamage(), monster.getSpeed(), monster.getKnockbackResistance());
        }
        //  Gestion des effets de potion à donner au monstre
        entity = addEffects(entity, monster.getEffectLists());



        //  Gestion des objets appartenant au monstre
        if (entity instanceof ArmorEquipable) {
            entity = (Entity) equip((ArmorEquipable)entity,monster);
        }
        //  Spawn de l'entité dans le monde

        entity.offer(Keys.ATTACK_DAMAGE,10.0d);
        world.spawnEntity(entity);

       // Brawlator.getLogger().info("info" + entity.getOrCreate(HealthData.class).get().health().get());

        return Optional.ofNullable(entity);
    }


    /**
     * Edit Monster's characteristics of monster and send back the modified entity
     * @param entity Monster to edit
     * @param displayName Name of the monster to display
     * @param hp health point of the monster
     * @param attackDamage damage of the monster
     * @param speed walking speed of the monster
     * @param knockbackResistance resistance to knockback
     * @return Modified entity
     */
    private static DataHolder editCharacteristics(DataHolder entity, String displayName, double hp, double attackDamage, double speed, int knockbackResistance) {
        entity.offer(Keys.DISPLAY_NAME, Text.of(displayName));

       /* MovementSpeedData movementSpeedData = entity.getOrCreate(MovementSpeedData.class).get();
        movementSpeedData.walkSpeed().set(speed);*/
        entity.offer(Keys.MAX_HEALTH,hp);
        entity.offer(Keys.HEALTH,hp);

      entity.offer(Keys.WALKING_SPEED,speed);

        entity.offer(Keys.ATTACK_DAMAGE, attackDamage);


        entity.offer(Keys.KNOCKBACK_STRENGTH, knockbackResistance);
        return entity;
    }

    /**
     * Add potion effects to a monster and send back the entity
     * @param entity Entity to apply the effect
     * @param effects List of effect
     * @return Modified entity
     */
    private static Entity addEffects(Entity entity, List<EffectBean> effects){
        List<PotionEffect> potions = new ArrayList();
        for (EffectBean effect :
             effects) {
            PotionEffectType potionEffectType = EffectBean.effectTypes.get(effect.getType());
            if(potionEffectType == null){
                Brawlator.getLogger().warn(effect.getType() + " is not valid");
            } else {
                PotionEffect potion = PotionEffect.builder().potionType(potionEffectType)
                        .duration(effect.getDuration()).amplifier(effect.getAmplifier()).build();
                potions.add(potion);
            }
        }
        PotionEffectData effectData = entity.getOrCreate(PotionEffectData.class).get();
        effectData.addElements(potions);
        entity.offer(effectData);
        return entity;
    }

    /**
     * Add objects to a monster , and send back the entity
     * @param entity Monster to equip
     * @param items Hashmap of objects to equip with their emplacements
     * @@return Modified entity
     */
    private static ArmorEquipable addItems(ArmorEquipable entity, HashMap<String, ItemType> items) {
        for (Map.Entry<String, ItemType> item : items.entrySet()) {
            ItemStack itemStack = ItemStack.builder().itemType(item.getValue()).build();
            switch (item.getKey().toString()) {
                case "hand":
                    entity.setItemInHand(HandTypes.MAIN_HAND, itemStack);
                    break;
                case "helmet":
                    entity.setHelmet(itemStack);
                    break;
                case "chestplate":
                    entity.setChestplate(itemStack);
                    break;
                case "boots":
                    entity.setBoots(itemStack);
                    break;
                case "leggings":
                    entity.setLeggings(itemStack);
                    break;
            }
        }
        return entity;
    }

    /***
     * Equip an entity(ArmorEquipable) of these equipments indiques in the monsterBean
     * @param entity entity to equip
     * @param monster monster of reference to get items
     * @return Modified entity
     */
    private static ArmorEquipable equip(ArmorEquipable entity, MonsterBean monster){
        HashMap<String, ItemType> equipement = new HashMap<>();
        for(Map.Entry<String,EquipementBean> entry : monster.getEquipement().entrySet()){
           Optional<ItemType> itemType = EquipementAction.getEquipement(entry.getValue());
           if(itemType.isPresent()){
               equipement.put(entry.getKey(),itemType.get());
           }
        }
       return addItems(entity,equipement);
    }


    /**
     * Request the DAO to get a monster in the database
     *
     * @param name name of the monster
     * @return the monster (optional)
     */
    public static Optional<MonsterBean> getMonster(String name){
        MonsterDAO monsterDao = new MonsterDAO();
        return monsterDao.getMonster(name);
    }
}
