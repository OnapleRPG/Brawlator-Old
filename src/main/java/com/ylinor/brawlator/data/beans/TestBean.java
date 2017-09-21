package com.ylinor.brawlator.data.beans;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
@DatabaseTable(tableName = "monster")
public class TestBean {

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
        private int hp;

    public TestBean() {
    }

    public TestBean(int id, String name, String type, int hp) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.hp = hp;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }
}
