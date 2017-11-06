package com.ylinor.brawlator.commands;
import com.ylinor.brawlator.action.MonsterAction;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

import java.util.Optional;



public class InvokeCommand implements CommandExecutor {
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
			String monsterId = (args.getOne("id").isPresent()) ? args.<String>getOne("id").get() : "";
			Optional<MonsterBean> monster = MonsterDAO.getMonster(monsterId);
			if(monster.isPresent()) {
				MonsterAction.invokeMonster(world, location, monster.get());
				 src.sendMessage(Text.of("MONSTER " + monster.get().getName() + " spawned."));
				return CommandResult.success();
			} else {
				((Player) src).sendMessage(Text.of("MONSTER " + monsterId + " not found."));
			}
		}
		return CommandResult.empty();
	}
}
