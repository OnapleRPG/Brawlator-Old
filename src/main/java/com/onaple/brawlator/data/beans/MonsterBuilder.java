package com.onaple.brawlator.data.beans;

import org.spongepowered.api.effect.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MonsterBuilder {

    /** Nom affiché au dessus du monstre **/
    private String name = "default name";
    /** Type de monstre **/
    private String type = "zombie";
    /** Points de vie du monstre **/
    private double hp = 20.0d;
    /** Vitesse du monstre **/
    private double speed = 1.0d;
    /**Dommage de base du monstre**/
    private double attackDamage = 1.0d;
    /** Resistance à la poussée **/
    private int knockbackResistance = 1;

    private List<EffectBean> effectList = new ArrayList<>();

;    private HashMap<String,EquipementBean> equipement = new HashMap<>();

    public MonsterBuilder effect(PotionEffectType type, int amplifier){
        this.effectList.add(new EffectBean(type,amplifier));
        return this;
    }
    public MonsterBuilder effect(EffectBean effectBean){
        this.effectList.add(effectBean);
        return this;
    }
    public MonsterBuilder effects(List<EffectBean> effectList){
        this.effectList = effectList;
        return this;
    }

    public MonsterBuilder name(String name){
        this.name = name;
        return this;
    }
    public MonsterBuilder type(String type){
        this.type = type;
        return this;
    }
    public MonsterBuilder speed(double speed){
        this.speed = speed;
        return this;
    }
    public MonsterBuilder hp(double hp){
        this.hp = hp;
        return this;
    }
    public MonsterBuilder attackDamage(double damage){
        this.attackDamage = damage;
        return  this;
    }
    public MonsterBuilder knockbackResistance(int knockbackResistance){
        this.knockbackResistance = knockbackResistance;
        return  this;
    }
    public MonsterBuilder addEquipement(String emplacement,EquipementBean equipement){
        this.equipement.put(emplacement,equipement);
        return this;
    }

    public MonsterBean build(){
        return new MonsterBean(name,type,hp,speed,attackDamage,knockbackResistance,effectList,equipement);
    }

}
