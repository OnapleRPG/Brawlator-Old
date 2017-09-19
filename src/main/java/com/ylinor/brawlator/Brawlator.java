package com.ylinor.brawlator;

import javax.inject.Inject;


import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.ylinor.brawlator.data.beans.Monster;
import org.slf4j.Logger;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;
import org.spongepowered.api.plugin.Plugin;


@Plugin(id = "brawlator", name = "Brawlator", version = "0.0.1")
public class Brawlator {
	@Inject
	private Logger logger;
	
	@Listener
	public void onServerStart(GameStartedServerEvent event) {
		logger.info("Brawlator plugin initialized.");

		testConnection();


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

	public static void testConnection(){
		try {
			// this uses h2 by default but change to match your database
			String databaseUrl = "jdbc:sqlite:browlator.db";
			// create a connection source to our database
			ConnectionSource connectionSource =
					new JdbcConnectionSource(databaseUrl);

			// instantiate the dao
			Dao<Monster, String> accountDao =
					DaoManager.createDao(connectionSource, Monster.class);


			// if you need to create the 'accounts' table make this call
			TableUtils.createTableIfNotExists(connectionSource, Monster.class);
			Monster monster = new Monster(1,"Skeleton",30);

// persist the account object to the database
			accountDao.create(monster);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
