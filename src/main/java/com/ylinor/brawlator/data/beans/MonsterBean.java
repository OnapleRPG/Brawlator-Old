package com.ylinor.brawlator.data.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.spongepowered.api.entity.EntityType;
import org.spongepowered.api.entity.EntityTypes;
import java.util.HashMap;
import java.util.List;
@DatabaseTable(tableName = "monster")
public class MonsterBean {

    /** Id du monstre dans la base de données **/
    @DatabaseField(id=true)
    private int id;

    /** Nom affiché au dessus du monstre **/
    @DatabaseField
    private String name;

    /** Type de monstre **/
    @DatabaseField
    private String type;
    /** Points de vie du monstre **/
    @DatabaseField
    private double hp;
    /** Vitesse du monstre **/
    @DatabaseField
    private double speed;
    /**Dommage de base du monstre**/
    @DatabaseField
    private double attackDamage;
    /** Resistance à la poussée **/
    @DatabaseField
    private int knockbackResistance;

    /** Effets appliquer au monstre **/
    private List<EffectBean> effectLists;
    /** Eqipement du monstre**/
    private HashMap<String,EquipementBean> equipement;



    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getType() {
        return type;
    }
    public double getHp() {
        return hp;
    }
    public double getSpeed() { return speed; }
    public double getAttackDamage() { return attackDamage; }
    public int getKnockbackResistance() { return knockbackResistance; }
   public List<EffectBean> getEffectLists() { return effectLists; }

    public static HashMap<String, EntityType> getMonsterTypes() { return monsterTypes; }

    public void setId(int id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setType(String _type) {
        this.type = _type;
    }
    public void setHp(double hp) { this.hp = hp; }
    public void setSpeed(double speed) { this.speed = speed; }
    public void setAttackDamage(double attackDamage) { this.attackDamage = attackDamage; }
    public void setKnockbackResistance(int knockbackResistance) { this.knockbackResistance = knockbackResistance; }
    public void setEffectLists(List<EffectBean> effectLists) { this.effectLists = effectLists; }

    public MonsterBean(String name, String type, double hp, double speed, double attackDamage, int knockbackResistance,
                       List<EffectBean> effectLists,HashMap<String, EquipementBean> equipement) {
        this.name = name;
        this.type = type;
        this.hp = hp;
        this.speed = speed;
        this.attackDamage = attackDamage;
        this.knockbackResistance = knockbackResistance;

        this.effectLists = effectLists;
        this.equipement = equipement;
    }

    public void addEquipement(String emplacement,EquipementBean equipementBean){
        this.equipement.put(emplacement,equipementBean);

  //      this.effectLists = effectLists;

    }

    public HashMap<String, EquipementBean> getEquipement() {
        return equipement;
    }

    public static final MonsterBuilder builder(){
        return new MonsterBuilder();
    }

    /** Liste des types de monstres reconnus **/
    public static final HashMap<String, EntityType> monsterTypes;
    static {
        monsterTypes = new HashMap<>();
        monsterTypes.put("creeper", EntityTypes.CREEPER);
        monsterTypes.put("ocelot", EntityTypes.OCELOT);
        monsterTypes.put("zombie", EntityTypes.ZOMBIE);
        monsterTypes.put("horse", EntityTypes.HORSE);
        monsterTypes.put("skeleton", EntityTypes.SKELETON);
    }
}
