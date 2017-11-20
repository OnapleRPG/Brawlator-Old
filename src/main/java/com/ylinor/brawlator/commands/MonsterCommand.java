package com.ylinor.brawlator.commands;

import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.MonsterBuilder;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import java.util.Optional;

/**
 * Create a monster and add it to the database
 */
public class MonsterCommand implements CommandExecutor {

    public MonsterCommand() {}

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

        MonsterBuilder monsterBuilder = MonsterBean.builder();
        Optional<String> nameOptional = args.<String>getOne("name");
        if (nameOptional.isPresent()) {
            monsterBuilder.name(nameOptional.get());
        }
        Optional<String> typeOptional = args.<String>getOne("type");
        if (typeOptional.isPresent()) {
            monsterBuilder.type(typeOptional.get());
        }
        Optional<Double> hpOptional = args.<Double>getOne("hp");
        if (hpOptional.isPresent()) {
            monsterBuilder.hp(hpOptional.get());
        }
        Optional<Integer> krOptionnal = args.<Integer>getOne("kr");
        if (krOptionnal.isPresent()) {
            monsterBuilder.knockbackResistance(krOptionnal.get());
        }
        Optional<Integer> damageOptional = args.<Integer>getOne("damage");
        if (damageOptional.isPresent()) {
            monsterBuilder.knockbackResistance(damageOptional.get());
        }
        Optional<Double> speedOptional = args.<Double>getOne("speed");
        if(speedOptional.isPresent()) {
            monsterBuilder.speed(speedOptional.get());
        }
        MonsterBean monsterBean = monsterBuilder.build();
        MonsterDAO.insert(monsterBean);

        ((Player) src).sendMessage(Text.of("MONSTER successfully created."));
        ((Player) src).sendMessage(Text.builder("    >Name : " + monsterBean.getName()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("    >Type : " + monsterBean.getType()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("    >Health : " + monsterBean.getHp()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("    >Speed : " + monsterBean.getSpeed()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("    >Damage : " + monsterBean.getAttackDamage()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("    >Knockback Resistance : " + monsterBean.getKnockbackResistance()).color(TextColors.GOLD).build());
        ((Player) src).sendMessage(Text.builder("You can add equipments and effects to the monster with the command /modify").color(TextColors.GREEN).build());

        return CommandResult.success();
    }
}
