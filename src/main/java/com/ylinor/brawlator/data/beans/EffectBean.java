package com.ylinor.brawlator.data.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.effect.potion.PotionEffectTypes;

import java.util.HashMap;
@DatabaseTable(tableName = "effect")
public class EffectBean {

    /** ID de la potion**/
    private int id;
    /** Type de l'effet**/
   @DatabaseField(canBeNull = false)
    private String type;
    /** durée de l'effet**/
    @DatabaseField
    private int duration;
    /** Amplification de l'effet**/
    @DatabaseField
    private int amplifier;
    /** Monstre associée**/
    private int monsterId;

    public EffectBean() {
    }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getDuration() { return duration; }
    public int getMonsterId() { return monsterId; }
    public int getId() { return id; }
    public void setDuration(int duration) { this.duration = duration; }
    public int getAmplifier() { return amplifier; }
    public void setAmplifier(int amplifier) { this.amplifier = amplifier; }
    public void setMonsterId(int monsterId) { this.monsterId = monsterId; }
    public void setId(int id) { this.id = id; }

    public EffectBean( String type, int duration, int amplifier) {

        this.type = type;
        this.duration = duration;
        this.amplifier = amplifier;
    }

    public static final HashMap<String, PotionEffectType> effectTypes;
    static {
        effectTypes = new HashMap<>();
        effectTypes.put("FIRE_RESISTANCE", PotionEffectTypes.FIRE_RESISTANCE);
        effectTypes.put("INVISIBILITY", PotionEffectTypes.INVISIBILITY);
        effectTypes.put("RESISTANCE", PotionEffectTypes.RESISTANCE);
        effectTypes.put("SPEED", PotionEffectTypes.SPEED);
        effectTypes.put("STRENGTH", PotionEffectTypes.STRENGTH);
    }


}
