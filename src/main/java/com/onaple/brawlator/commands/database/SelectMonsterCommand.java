package com.onaple.brawlator.commands.database;

import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.data.beans.MonsterBean;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;


public class SelectMonsterCommand implements CommandExecutor {

	public SelectMonsterCommand() {}

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		if (src instanceof Player && src.hasPermission("database_read")) {

			Optional<String> idArg = args.getOne("name");
			if (idArg.isPresent()) {
				Optional<MonsterBean> monsterOptional = Brawlator.getMonsterAction().getMonster(idArg.get());
				if (monsterOptional.isPresent()) {
					MonsterBean monster = monsterOptional.get();
					 src.sendMessage(Text.of("MONSTER " + monster.getId() + " : " + monster.getName() + " | " +
							monster.getType() + " | " + monster.getHp() + " | " + monster.getAttackDamage() + " | " + monster.getSpeed() + " | " +
							monster.getKnockbackResistance()));
					return CommandResult.success();
				} else {
					src.sendMessage(Text.of("MONSTER " + idArg.get() + " not found"));
				}
			}
		} else {
			src.sendMessage(Text.builder("Monster id is missing").color(TextColors.RED).build());
		}
		return CommandResult.empty();
	}
}
