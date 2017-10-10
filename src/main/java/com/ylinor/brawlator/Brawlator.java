package com.ylinor.brawlator;

import javax.inject.Inject;


import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.commands.InvokeCommand;
import com.ylinor.brawlator.commands.MonsterCommand;
import com.ylinor.brawlator.commands.database.SelectMonsterCommand;
import com.ylinor.brawlator.commands.effectCommand;
import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.EquipementBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
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
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.event.game.state.GameStoppedServerEvent;
import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.text.Text;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;


@Plugin(id = "brawlator", name = "Brawlator", version = "0.0.1")
public class Brawlator {
	@Inject
	private Logger logger;


	@Inject
	@ConfigDir(sharedRoot=true)
	private Path configDir;

	private CommentedConfigurationNode monster;

	private ConfigurationLoader<CommentedConfigurationNode> configLoader;

	public ConfigurationLoader<CommentedConfigurationNode> getConfigurationLoader(){
		return configLoader;
	}
	@Listener
	public void onServerStart(GameStartedServerEvent event) {


		logger.info("Brawlator plugin initialized.");

		 monster = loadConfiguration("monster.conf");


		ConfigurationHandler.setConfiguration(monster);
		MonsterDAO.populate();





		/*SqliteHandler.testConnection();*/

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


		CommandSpec monsterSelect = CommandSpec.builder()
                .permission("ylinor.brawlator.database_read")
                .description(Text.of("Select and print a monster from database"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
                .executor(new SelectMonsterCommand()).build();

		CommandSpec monsterDatabase = CommandSpec.builder()
                .description(Text.of("Query database about monsters"))
                .child(monsterSelect, "select").build();
        Sponge.getCommandManager().register(this, monsterDatabase, "monsters");

	}

	@Listener
	public void onServerStop(GameStoppedServerEvent event) {
		logger.info("stop");
		save(monster);
	}



	private CommentedConfigurationNode loadConfiguration(String configName) {
		configLoader = HoconConfigurationLoader.builder().setPath(Paths.get(configDir+"/"+configName)).build();
		CommentedConfigurationNode configNode = null;


		TypeSerializerCollection serializers = TypeSerializers.getDefaultSerializers().newChild();
		serializers.registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer())
				.registerType(TypeToken.of(EffectBean.class), new EffectSerializer())
				.registerType(TypeToken.of(EquipementBean.class), new EquipementSerialiser());
		ConfigurationOptions options = ConfigurationOptions.defaults().setSerializers(serializers);
		try {
			configNode = configLoader.load(options);
		} catch (IOException e) {
			logger.error("Error while loading configuration " + configName + " : " + e.getMessage());
		}
		return configNode;
	}
	private void save(ConfigurationNode rootNode){
		try {
			ConfigurationHandler.serializeMonsterList(MonsterDAO.monsterList);

			configLoader.save(rootNode);
		} catch(IOException e) {
			// error
		}
	}


}
