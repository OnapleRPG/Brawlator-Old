package com.onaple.brawlator;

import com.onaple.brawlator.commands.ReloadCommand;
import com.onaple.brawlator.commands.database.SelectMonsterCommand;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import com.onaple.brawlator.event.BrawlatorSpawnEvent;
import com.onaple.brawlator.action.MonsterAction;
import com.onaple.brawlator.action.SpawnerAction;
import com.onaple.brawlator.commands.InvokeCommand;
import com.onaple.brawlator.exception.PluginNotFoundException;
import com.onaple.brawlator.exception.WorldNotFoundException;

import com.onaple.itemizer.Itemizer;
import com.onaple.itemizer.service.IItemService;

import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.asset.Asset;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;

import org.slf4j.Logger;

import javax.inject.Inject;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Plugin(id = "brawlator", name = "Brawlator", version = "1.1.0")
public class Brawlator {
	@Inject
	private Logger logger;

	@Inject
	@ConfigDir(sharedRoot=true)
	private Path configDir;

	@Inject
	private ConfigurationHandler configurationHandler;

	@Inject
	private MonsterAction monsterAction;
	@Inject
	private SpawnerAction spawnerAction;

	private Optional<IItemService> itemService;
	public Optional<IItemService> getItemService() {
	    return itemService;
    }

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		Sponge.getEventManager().registerListeners(this, new BrawlatorListener());

		// Try to load Itemizer plugin dependency
		Optional<PluginContainer> itemServiceContainer = Sponge.getPluginManager().getPlugin("Itemizer");
		if (itemServiceContainer.isPresent()) {
		    itemService = Sponge.getServiceManager().provide(IItemService.class);
        } else {
            logger.warn("Itemizer dependency not found");
        }

        // Load configuration files
		try {
			int monsterLoadedCount = loadMonsters();
			logger.info("Monsters loaded : {}", monsterLoadedCount);
			int spawnerLoadedCount = loadSpawners();
			logger.info("Spawners loaded : {}", spawnerLoadedCount);
			int lootTableLoadedCount = loadLootTables();
			logger.info("Loot tables loaded : {}", lootTableLoadedCount);
		} catch (IOException e) {
			logger.error("IOException : {}", e.getMessage());
		} catch (ObjectMappingException e) {
		    logger.error("ObjectMappingException : {}", e.getMessage());
        }

        // Register Brawlator commands
        registerCommands();

		// Register spawner routine
		Task.builder().execute(()-> spawnerAction.updateSpawner()).delay(20, TimeUnit.SECONDS)
				.interval(10,TimeUnit.SECONDS).name("Spawn monster").submit(this);

		logger.info("Brawlator plugin initialized.");
	}

	/**
	 * Register the different Brawlator commands to the command manager
	 */
	private void registerCommands() {
		CommandSpec invokeCommand = CommandSpec.builder()
				.description(Text.of("Invoke a monster whose id is registered into the database"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))))
				.permission("brawlator.command.invoke")
				.executor(new InvokeCommand()).build();
		Sponge.getCommandManager().register(this, invokeCommand, "invoke");

		CommandSpec monsterSelectCommand = CommandSpec.builder()
				.description(Text.of("Select and print a monster from database"))
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
				.permission("brawlator.command.read")
				.executor(new SelectMonsterCommand()).build();
		CommandSpec monsterDatabase = CommandSpec.builder()
				.description(Text.of("Query database about monsters"))
				.child(monsterSelectCommand, "select").build();
		Sponge.getCommandManager().register(this, monsterDatabase, "monsters");

		CommandSpec reloadCommand = CommandSpec.builder()
				.description(Text.of("Reload Brawlator configuration from files"))
				.permission("brawlator.command.reload")
				.executor(new ReloadCommand()).build();
		Sponge.getCommandManager().register(this, reloadCommand, "reload-brawlator");
	}

	/**
	 * Spawner listener
	 * @param event Spawning event
	 */
	@Listener
	public void onSpawnEvent(BrawlatorSpawnEvent event){
		try {
			Optional<Entity> monster = monsterAction.invokeMonster(
					getWorld("world").getLocation(event.getSpawnerBean().getPosition()),
					event.getSpawnerBean().getMonsterBean());
			if(!monster.isPresent()){
				logger.error("Monster invocation failed !");
			}
		} catch (Exception e) {
			logger.warn(e.getMessage());
		}
	}

	/**
	 * Get the current world
	 * @return the world
	 */
	public static World getWorld(String worldName) throws WorldNotFoundException {
		Optional<World> worldOptional = Sponge.getServer().getWorld(worldName);
		return worldOptional.orElseThrow(() -> new WorldNotFoundException(worldName));
	}

	/**
	 * Load monsters from configuration
	 * @return Amount of monsters loaded
     * @throws IOException error when copying default config in config/brawlator/ folder
     * @throws ObjectMappingException error when the configuration file have an syntax error
	 */
	public int loadMonsters() throws IOException, ObjectMappingException {
		initDefaultConfig("monster.conf");
		return configurationHandler.setMonsterList(configurationHandler.loadConfiguration(configDir + "/brawlator/monster.conf"));
	}

	/**
	 * Load spawners from configuration
	 * @return Amount of spawners loaded
     * @throws IOException error when copying default config in config/brawlator/ folder
     * @throws ObjectMappingException error when the configuration file have an syntax error
	 */
	public int loadSpawners() throws IOException, ObjectMappingException {
		initDefaultConfig("spawner.conf");
		return configurationHandler.setSpawnerList(configurationHandler.loadConfiguration(configDir + "/brawlator/spawner.conf"));
	}

	/**
	 * Load loot tables from configuration
	 * @return Amount of loot tables loaded
     * @throws IOException error when copying default config in config/brawlator/ folder
     * @throws ObjectMappingException error when the configuration file have an syntax error
	 */
	public int loadLootTables() throws IOException, ObjectMappingException {
		initDefaultConfig("loot_table.conf");
		return configurationHandler.setLootTableList(configurationHandler.loadConfiguration(configDir + "/brawlator/loot_table.conf"));
	}

    /**
     * Load the default configuration from resources if file is not found
     * @param path Configuration path within the config/bralawtor/ folder
     */
	private void initDefaultConfig(String path){
		if (Files.notExists(Paths.get(configDir+ "/brawlator/" + path))) {
			PluginContainer pluginInstance = null;
			try {
				pluginInstance = Sponge.getPluginManager().getPlugin("brawlator").orElseThrow(() -> new PluginNotFoundException("brawlator"));
				Optional<Asset> itemsDefaultConfigFile = pluginInstance.getAsset(path);
				logger.info("No config file set for {} default config will be loaded", path);
				if (itemsDefaultConfigFile.isPresent()) {
					try {
						itemsDefaultConfigFile.get().copyToDirectory(Paths.get(configDir+"/brawlator/"));
					} catch (IOException e) {
						Itemizer.getLogger().error("Error while setting default configuration : {}", e.getMessage());
					}
				} else {
					logger.warn("Item default config not found");
				}
			} catch (PluginNotFoundException e) {
				logger.error(e.toString());
			}
		}
	}
}
