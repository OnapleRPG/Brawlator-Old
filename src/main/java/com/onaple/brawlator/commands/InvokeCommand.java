package com.onaple.brawlator.commands;

import com.onaple.brawlator.action.MonsterAction;
import com.onaple.brawlator.data.beans.MonsterBean;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import javax.inject.Inject;
import java.util.Optional;


/**
 * Command to invoke a monster present in the database
 */
public class InvokeCommand implements CommandExecutor {
	@Inject
	MonsterAction monsterAction;

	/**
	 * Constructeur par defaut
	 */
	public InvokeCommand() {
	}

	/**
	 * Invoke un monstre en fonction de son Nom
	 * @param src source qui a effecteur la commande
	 * @param args liste des argument
	 * @return resultat de l'execution
	 * @throws CommandException
	 */
	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player) {
			World world = ((Player) src).getWorld();
			Location location = ((Player) src).getLocation();


			Optional<String> idArgs = args.getOne("id");
			if(idArgs.isPresent()) {

				Optional<MonsterBean> monster = monsterAction.getMonster(idArgs.get());


				if (monster.isPresent()) {
					try {
						monsterAction.invokeMonster(location, monster.get());
						src.sendMessage(Text.of("MONSTER " + monster.get().getName() + " spawned."));
						return CommandResult.empty();
					} catch (Exception e) {
						src.sendMessage(Text.builder().append(Text.of(e.getMessage())).color(TextColors.GREEN).build());
					}

					return CommandResult.success();
				} else {
					src.sendMessage(Text.of("MONSTER " + idArgs.get() + " not found."));
				}
			} else {
				src.sendMessage(Text.builder("Monster id is missing").color(TextColors.RED).build());
			}
		}
		return CommandResult.empty();
	}
}
