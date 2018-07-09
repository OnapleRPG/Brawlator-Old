package com.onaple.brawlator.exception;

public class WorldNotFoundException extends Exception {
    public WorldNotFoundException(String worldName) {
        super("World nammed " + worldName + "not Found, make sure the world exist and is loaded" );
    }
}
