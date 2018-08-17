package com.onaple.brawlator.exception;

public class WorldNotFoundException extends Exception {
    public WorldNotFoundException(String worldName) {
        super("World named " + worldName + " not found, make sure the world exists and is loaded" );
    }
}
