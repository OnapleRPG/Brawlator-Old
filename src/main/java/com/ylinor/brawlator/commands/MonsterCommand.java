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

import java.util.Optional;

public class MonsterCommand implements CommandExecutor {

    public MonsterCommand(){

    }

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {

       MonsterBuilder monsterBuilder = MonsterBean.builder();
        Optional<String> nameOptional = args.<String>getOne("name");
        if (nameOptional.isPresent()) {
           monsterBuilder.name(nameOptional.get());
        }
        Optional<String> typeOptional = args.<String>getOne("type");
        if(typeOptional.isPresent()) {
            monsterBuilder.type(typeOptional.get());
        }
        Optional<Double> hpOptional = args.<Double>getOne("hp");
        if(typeOptional.isPresent()) {
            monsterBuilder.hp(hpOptional.get());
        }

        Optional<Double> damageOptional = args.<Double>getOne("damage");

        MonsterDAO.insert(monsterBuilder.build());

        ((Player) src).sendMessage(Text.of("MONSTER successfully created."));

        return CommandResult.success();
    }
}
