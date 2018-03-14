package com.ylinor.brawlator;

import javax.inject.Inject;
import com.ylinor.brawlator.action.MonsterAction;
import com.ylinor.brawlator.action.SpawnerAction;
import com.ylinor.brawlator.commands.*;
import com.ylinor.brawlator.commands.database.SelectMonsterCommand;
import com.ylinor.brawlator.commands.element.EffectCommandElement;
import com.ylinor.brawlator.commands.element.EquipementCommandElement;
import com.ylinor.brawlator.commands.element.MonsterCommandElement;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import com.ylinor.brawlator.data.dao.SpawnerDAO;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import com.ylinor.brawlator.event.SpawnEvent;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.data.key.Keys;
import org.spongepowered.api.entity.Entity;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.entity.DestructEntityEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.event.item.inventory.DropItemEvent;
import org.spongepowered.api.item.inventory.ItemStackSnapshot;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.plugin.PluginContainer;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.World;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Plugin(id = "brawlator", name = "Brawlator", version = "0.0.1")
public class Brawlator {

	private static Logger logger;

	@Inject
	private void setLogger(Logger logger) {
		Brawlator.logger = logger;
	}
	public static Logger getLogger() {
		return logger;
	}

	@Inject
	@ConfigDir(sharedRoot=true)
	private Path configDir;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Brawlator plugin initialized.");
		ConfigurationHandler.setMonsterConfiguration(ConfigurationHandler.loadMonsterConfiguration(configDir + "/monster.conf"));
		ConfigurationHandler.setSpawnerConfiguration(ConfigurationHandler.loadSpawnerConfiguration(configDir + "/spawner.conf"));
		ConfigurationHandler.setLootTableConfigLoader(ConfigurationHandler.loadLootTableConfiguration(configDir + "/loottable.conf"));
		MonsterDAO.populate();
		SpawnerDAO.populate();

		ConfigurationHandler.getLootTableList();

		getLogger().info(String.valueOf(SpawnerDAO.spawnerList.size())+ " Spawner(s) loaded");
		getLogger().info(String.valueOf(MonsterDAO.monsterList.size())+ " Monster(s) loaded");

		Sponge.getEventManager().registerListeners(this, new BrawlatorListener());

		CommandSpec create = CommandSpec.builder()
				.description(Text.of("Create a monster and add it to the base"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))
						,GenericArguments.onlyOne(GenericArguments.string(Text.of("type")))
						,GenericArguments.flags().valueFlag(GenericArguments.doubleNum(Text.of("hp")),"-hp")
						.buildWith(GenericArguments.none()))
				.executor(new MonsterCommand()).build();
		Sponge.getCommandManager().register(this,create,"create");

		CommandSpec invoke = CommandSpec.builder()
				.description(Text.of("Invoke a monster whose id is registered into the database"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("id"))))
				.executor(new InvokeCommand()).build();
		Sponge.getCommandManager().register(this, invoke, "invoke");

		CommandSpec effect = CommandSpec.builder().arguments(
				new MonsterCommandElement(Text.of("monster"))
				,new EffectCommandElement(Text.of("effect"))
		).executor(new effectCommand()).build();

		Sponge.getCommandManager().register(this,effect,"effect");

		CommandSpec equipment = CommandSpec.builder().arguments(
				new MonsterCommandElement(Text.of("monster"))
				,GenericArguments.string(Text.of("emplacement"))
				,new EquipementCommandElement(Text.of("equipment"))
		).executor(new equipementCommand()).build();
		Sponge.getCommandManager().register(this, equipment, "equipment");

		CommandSpec createSpawner = CommandSpec.builder().arguments(
				GenericArguments.location(Text.of("position"))
				,new MonsterCommandElement(Text.of("monster"))
				,GenericArguments.integer(Text.of("range"))
				,GenericArguments.integer(Text.of("spawnrate"))
				,GenericArguments.integer(Text.of("quantity"))
		).executor(new CreateSpawnerCommand()).build();

		Sponge.getCommandManager().register(this,createSpawner,"spawner");

		CommandSpec monsterSelect = CommandSpec.builder()
				.permission("ylinor.brawlator.database_read")
				.description(Text.of("Select and print a monster from database"))
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
				.executor(new SelectMonsterCommand()).build();

		CommandSpec monsterDatabase = CommandSpec.builder()
				.description(Text.of("Query database about monsters"))
				.child(monsterSelect, "select").build();
		Sponge.getCommandManager().register(this, monsterDatabase, "monsters");

		 Task.builder().execute(()-> SpawnerAction.updateSpawner()).delay(20, TimeUnit.SECONDS)
				.interval(10,TimeUnit.SECONDS).name("Spawn monster").submit(this);



	}

	/**
	 * Listener of spawner
	 * @param event event rised
	 */
	@Listener
	public void onSpawnEvent(SpawnEvent event){

				try {
					getLogger().info(MonsterAction.invokeMonster(getWorld(), getWorld().getLocation(event.getSpawnerBean().getPosition()), event.getSpawnerBean().getMonsterBean()).get().toString());
				} catch (Exception e) {
					getLogger().warn(e.getMessage());
				}
		}

	/**
	 * Listener of the server stop event and save configuration
	 * @param event
	 */
	@Listener
	public void onServerStop(GameStoppedServerEvent event) {
		logger.info("stop");
		ConfigurationHandler.save();
	}

	@Listener
	public void onEntityDeath(DestructEntityEvent.Death event){
	logger.info(event.getTargetEntity().toString());

	}

		/**
		 * Cancel block breaking dropping event unless specified in config
		 * @param event Item dropping event
		 */
		@Listener
		public void onDropItemEvent(DropItemEvent.Destruct event) {
			/*List<String> defaultDrops = ConfigurationHandler.getHarvestDefaultDropList();
			Optional<Player> player = event.getCause().first(Player.class);
			if (player.isPresent()) {
				for (Entity entity: event.getEntities()) {
					Optional<ItemStackSnapshot> stack = entity.get(Keys.REPRESENTED_ITEM);
					if (stack.isPresent()) {
						if (!defaultDrops.contains(stack.get().getType().getId())) {
							event.setCancelled(true);
						}
					}
				}

		}*/
	}

	/**
	 * Get the plugin instance
	 * @return the instance
	 */
	public static PluginContainer getInstance(){
		return  Sponge.getPluginManager().getPlugin("brawlator").get();
	}

	/**
	 * Get the current world
	 * @return the world
	 */
	public static World getWorld(){
		Optional<World> worldOptional = Sponge.getServer().getWorld("world");
		if(worldOptional.isPresent()){
			return worldOptional.get();
		}
		return null;
	}
}
