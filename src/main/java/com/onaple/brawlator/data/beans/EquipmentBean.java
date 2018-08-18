package com.onaple.brawlator.data.beans;

public class EquipmentBean {
    /** Equipment name **/
    private String name;

    /** Modifier id **/
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

    public EquipmentBean(String name, int modifierId) {
        this.name = name;
        this.modifierId = modifierId;
    }
}
