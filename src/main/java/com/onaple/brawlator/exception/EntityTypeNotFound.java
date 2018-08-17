package com.onaple.brawlator.exception;

public class EntityTypeNotFound extends Exception {
    public EntityTypeNotFound(String type) {
        super("The entity type " + type + " does not exist");
    }
}
