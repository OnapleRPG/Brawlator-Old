package com.onaple.brawlator.exception;

public class EntityTypeNotFound extends Exception {
    public EntityTypeNotFound(String type) {
        super("The type " + type + " does not exist");
    }
}
