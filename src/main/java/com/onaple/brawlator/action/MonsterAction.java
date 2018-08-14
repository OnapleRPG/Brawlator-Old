package com.onaple.brawlator.action;

import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.EffectBean;
import com.onaple.brawlator.data.beans.EquipementBean;
import com.onaple.brawlator.data.beans.MonsterBean;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import com.onaple.brawlator.exception.EntityTypeNotFound;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.data.DataHolder;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.data.manipulator.mutable.PotionEffectData;
import org.spongepowered.api.data.type.HandTypes;
import org.spongepowered.api.effect.potion.PotionEffect;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.entity.ArmorEquipable;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.inventory.ItemStack;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.*;
import java.util.stream.Collectors;

@Singleton
public class MonsterAction {
    @Inject
    private Logger logger;

    public MonsterAction() {}

    @Inject
    private ConfigurationHandler configurationHandler;


    /**
     * Invoque a monster at a specific position
     * @param location Spawn 's Emplacement
     * @param monster Bean of the monster and his characteristic
     * @return Optional entity spawned
     */
    public Optional<Entity> invokeMonster(Location location, MonsterBean monster) throws EntityTypeNotFound {

        //  Création de l'entité
        logger.info(monster.toString());
        Optional<EntityType> entityTypeOptional = Sponge.getRegistry().getType(EntityType.class,monster.getType());

        if( entityTypeOptional.isPresent()){
            Entity baseEntity = location.createEntity(entityTypeOptional.get());

            Entity customedEntity  = editCharacteristics( baseEntity, monster.getName(), monster.getHp(), monster.getAttackDamage(), monster.getSpeed(), monster.getKnockbackResistance());

            //  Gestion des effets de potion à donner au monstre
            Entity effectedEntity = addEffects(customedEntity, monster.getEffectLists());

            //  Gestion des objets appartenant au monstre
            Entity equipedEntity = equip( effectedEntity, monster);


            /** Spawn de l'entité dans le monde*/
            location.spawnEntity(equipedEntity);
            //  Spawn de l'entité dans le monde
            return Optional.ofNullable(equipedEntity);
        }

        return Optional.empty();
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
    private Entity editCharacteristics(DataHolder entity, String displayName, double hp, double attackDamage, double speed, int knockbackResistance) {
        entity.offer(Keys.DISPLAY_NAME, Text.of(displayName));

       /* MovementSpeedData movementSpeedData = entity.getOrCreate(MovementSpeedData.class).get();
        movementSpeedData.walkSpeed().set(speed);*/
        entity.offer(Keys.MAX_HEALTH,hp);
        entity.offer(Keys.HEALTH,hp);
        entity.offer(Keys.WALKING_SPEED,speed);
        entity.offer(Keys.ATTACK_DAMAGE, attackDamage);
        entity.offer(Keys.KNOCKBACK_STRENGTH, knockbackResistance);

        return (Entity) entity;
    }

    /**
     * Add potion effects to a monster and send back the entity
     * @param entity Entity to apply the effect
     * @param effects List of effect
     * @return Modified entity
     */
    private Entity addEffects(Entity entity, List<EffectBean> effects){
        List<PotionEffect> potions = new ArrayList();
        for (EffectBean effect :
             effects) {
                PotionEffect potion = PotionEffect.builder().potionType(effect.getType())
                        .duration(Integer.MAX_VALUE).amplifier(effect.getAmplifier()).build();
                potions.add(potion);
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
    private Entity addItems(Entity entity, HashMap<String, ItemType> items) {
        if (entity instanceof ArmorEquipable ) {
            ArmorEquipable equipableEntity = (ArmorEquipable) entity;
            for (Map.Entry<String, ItemType> item : items.entrySet()) {
                ItemStack itemStack = ItemStack.builder().itemType(item.getValue()).build();
                switch (item.getKey()) {
                    case "hand":
                        equipableEntity.setItemInHand(HandTypes.MAIN_HAND, itemStack);
                        break;
                    case "helmet":
                        equipableEntity.setHelmet(itemStack);
                        break;
                    case "chestplate":
                        equipableEntity.setChestplate(itemStack);
                        break;
                    case "boots":
                        equipableEntity.setBoots(itemStack);
                        break;
                    case "leggings":
                        equipableEntity.setLeggings(itemStack);
                        break;
                }
            }
            return (Entity) equipableEntity;
        }
        return  entity;
    }

    /***
     * Equip an entity(ArmorEquipable) of these equipments indiques in the monsterBean
     * @param entity entity to equip
     * @param monster monster of reference to get items
     * @return Modified entity
     */
    private Entity equip(Entity entity, MonsterBean monster){
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
    public Optional<MonsterBean> getMonster(String name){
        return configurationHandler.getMonsterList().stream().filter(monsterBean -> monsterBean.getName().equals(name)).findFirst();
    }


}
