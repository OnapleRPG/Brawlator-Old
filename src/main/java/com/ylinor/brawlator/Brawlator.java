package com.ylinor.brawlator;

import javax.inject.Inject;


import com.flowpowered.math.vector.Vector3i;
import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.commands.InvokeCommand;
import com.ylinor.brawlator.commands.MonsterCommand;
import com.ylinor.brawlator.commands.database.SelectMonsterCommand;
import com.ylinor.brawlator.commands.effectCommand;
import com.ylinor.brawlator.commands.element.EffectCommandElement;
import com.ylinor.brawlator.commands.element.EquipementCommandElement;
import com.ylinor.brawlator.commands.element.MonsterCommandElement;
import com.ylinor.brawlator.commands.equipementCommand;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import com.ylinor.brawlator.data.dao.SpawnerDAO;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import com.ylinor.brawlator.serializer.EffectSerializer;
import com.ylinor.brawlator.serializer.EquipementSerialiser;
import com.ylinor.brawlator.serializer.MonsterSerializer;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.ConfigurationOptions;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializerCollection;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.args.GenericArguments;
import org.spongepowered.api.command.spec.CommandSpec;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.entity.living.animal.Wolf;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.block.TickBlockEvent;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.LocatableBlock;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
		MonsterDAO.populate();
		SpawnerDAO.populate();

		getLogger().info(String.valueOf(SpawnerDAO.spawnerList.size())+ " Spawner(s) loaded");

		getLogger().info(String.valueOf(MonsterDAO.monsterList.size())+ " Monster(s) loaded");

		/// Commandes du plugin

		CommandSpec create = CommandSpec.builder()
				.description(Text.of("Create a monster and it to the base"))
				.arguments(GenericArguments.onlyOne(GenericArguments.string(Text.of("name")))
				,GenericArguments.onlyOne(GenericArguments.string(Text.of("type")))
				,GenericArguments.flags().valueFlag(GenericArguments.doubleNum(Text.of("hp")),"-hp").buildWith(GenericArguments.none()))

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


		CommandSpec monsterSelect = CommandSpec.builder()
                .permission("ylinor.brawlator.database_read")
                .description(Text.of("Select and print a monster from database"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
                .executor(new SelectMonsterCommand()).build();

		CommandSpec monsterDatabase = CommandSpec.builder()
                .description(Text.of("Query database about monsters"))
                .child(monsterSelect, "select").build();
        Sponge.getCommandManager().register(this, monsterDatabase, "monsters");



        Task task = Task.builder().execute(()-> SpawnerAction.updateSpawner()).delay(20, TimeUnit.SECONDS)
				.interval(10,TimeUnit.SECONDS).name("Spawn monster").submit(this);


	}

	@Listener
	public void onSpawnEvent(SpawnEvent event){
		getLogger().info(
		MonsterAction.invokeMonster(getWorld(),getWorld().getLocation(event.getSpawnerBean().getPosition()),event.getSpawnerBean().getMonsterBean()).get().toString()
		);
	}

	@Listener
	public void onServerStop(GameStoppedServerEvent event) {
		logger.info("stop");
		ConfigurationHandler.save();
	}

	public static World getWorld(){
		Optional<World> worldOptional = Sponge.getServer().getWorld("world");
		if(worldOptional.isPresent()){
			return worldOptional.get();
		}
		return null;
	}
}
