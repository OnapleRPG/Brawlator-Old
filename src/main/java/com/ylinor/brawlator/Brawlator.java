package com.ylinor.brawlator;

import javax.inject.Inject;


import com.google.common.reflect.TypeToken;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.handler.ConfigurationHandler;
import ninja.leaping.configurate.ConfigurationNode;
import ninja.leaping.configurate.commented.CommentedConfigurationNode;
import ninja.leaping.configurate.hocon.HoconConfigurationLoader;
import ninja.leaping.configurate.loader.ConfigurationLoader;
import ninja.leaping.configurate.objectmapping.ObjectMappingException;
import ninja.leaping.configurate.objectmapping.serialize.TypeSerializers;
import org.slf4j.Logger;
import org.spongepowered.api.config.ConfigDir;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;


@Plugin(id = "brawlator", name = "Brawlator", version = "0.0.1")
public class Brawlator {
	@Inject
	private Logger logger;


	@Inject
	@ConfigDir(sharedRoot=true)
	private Path configDir;

	private CommentedConfigurationNode monster;

	@Listener
	public void onServerStart(GameStartedServerEvent event) {


		logger.info("Brawlator plugin initialized.");

		 monster = loadConfiguration("monster.conf");
		TypeSerializers.getDefaultSerializers().registerType(TypeToken.of(MonsterBean.class), new MonsterSerializer());
		try {
			List<MonsterBean> monsterList = monster.getNode("monster").getList(TypeToken.of(MonsterBean.class));
			for (MonsterBean monsterBean: monsterList
				 ) {
				logger.info(monsterBean.getName());
			}
		} catch (ObjectMappingException e) {
			e.printStackTrace();
		}





		/*SqliteHandler.testConnection();

		/// Commandes du plugin

		CommandSpec invoke = CommandSpec.builder()
                .description(Text.of("Invoke a monster whose id is registered into the database"))
				.arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
				.executor(new InvokeCommand()).build();
		Sponge.getCommandManager().register(this, invoke, "invoke");


		CommandSpec monsterSelect = CommandSpec.builder()
                .permission("ylinor.brawlator.database_read")
                .description(Text.of("Select and print a monster from database"))
                .arguments(GenericArguments.onlyOne(GenericArguments.integer(Text.of("id"))))
                .executor(new SelectMonsterCommand()).build();

		CommandSpec monsterDatabase = CommandSpec.builder()
                .description(Text.of("Query database about monsters"))
                .child(monsterSelect, "select").build();
        Sponge.getCommandManager().register(this, monsterDatabase, "monsters");
*/
	}
	private CommentedConfigurationNode loadConfiguration(String configName) {
		ConfigurationLoader<CommentedConfigurationNode> configLoader = HoconConfigurationLoader.builder().setPath(Paths.get(configDir+"/"+configName)).build();
		CommentedConfigurationNode configNode = null;
		try {
			configNode = configLoader.load();
		} catch (IOException e) {
			logger.error("Error while loading configuration " + configName + " : " + e.getMessage());
		}
		return configNode;
	}

}
