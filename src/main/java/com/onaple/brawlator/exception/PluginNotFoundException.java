package com.onaple.brawlator.exception;

public class PluginNotFoundException extends Exception {
    public PluginNotFoundException(String pluginName) {
        super("Plugin named " + pluginName + "not found, make sure the plugin is present in your server's mods folder");
    }
}
