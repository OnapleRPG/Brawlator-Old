package com.onaple.brawlator.data.beans;

import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;

import java.util.HashMap;

public class EffectBean {

    /** ID de la potion**/
    private int id;
    /** Type de l'effet**/
    private PotionEffectType type;
    /** durée de l'effet**/
    private int amplifier;
    /** Monstre associée**/
    private int monsterId;
    public EffectBean() {
    }

    public PotionEffectType getType() { return type; }
    public void setType(PotionEffectType type) { this.type = type; }

    public int getMonsterId() { return monsterId; }
    public int getId() { return id; }
    public int getAmplifier() { return amplifier; }
    public void setAmplifier(int amplifier) { this.amplifier = amplifier; }
    public void setMonsterId(int monsterId) { this.monsterId = monsterId; }
    public void setId(int id) { this.id = id; }

    public EffectBean(PotionEffectType type, int amplifier) {

        this.type = type;
        this.amplifier = amplifier;
    }





}
