package com.onaple.brawlator.commands;

import com.onaple.brawlator.action.MonsterAction;
import com.onaple.brawlator.data.beans.MonsterBean;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.inject.Inject;
import java.util.Optional;


public class SelectMonsterCommand implements CommandExecutor {
	@Inject
	private MonsterAction monsterAction;

	@Override
	public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
		Optional<String> idArg = args.getOne("name");
        if (idArg.isPresent()) {
            Optional<MonsterBean> monsterOptional = monsterAction.getMonster(idArg.get());
            if (monsterOptional.isPresent()) {
                MonsterBean monster = monsterOptional.get();
                src.sendMessage(Text.of("MONSTER " + monster.getId() + " : " + monster.getName() + " | " +
                        monster.getType() + " | " + monster.getHp() + " | " + monster.getAttackDamage() + " | " + monster.getSpeed() + " | " +
                        monster.getKnockbackResistance()));
                return CommandResult.success();
            } else {
                src.sendMessage(Text.of("MONSTER " + idArg.get() + " not found"));
            }
        } else {
            src.sendMessage(Text.builder("Monster id is missing").color(TextColors.RED).build());
        }
		return CommandResult.empty();
	}
}
