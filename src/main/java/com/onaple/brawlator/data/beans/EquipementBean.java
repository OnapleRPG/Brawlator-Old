package com.onaple.brawlator.data.beans;

public class EquipementBean {
    /** Nom de l'Equpiement **/
    private String name;

    /** Id du modifier **/
    private int modifierId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getModifierId() {
        return modifierId;
    }

    public void setModifierId(int modifierId) {
        this.modifierId = modifierId;
    }

    public EquipementBean(String name, int modifierId) {
        this.name = name;
        this.modifierId = modifierId;
    }


}
