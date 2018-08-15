package com.onaple.brawlator.exception;

public class WorldNotFoundException extends Exception {
    public WorldNotFoundException(String worldName) {
        super("World named " + worldName + " not Found, make sure the world exists and is loaded" );
    }
}
