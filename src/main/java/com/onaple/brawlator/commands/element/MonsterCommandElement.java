package com.onaple.brawlator.commands.element;

import com.onaple.brawlator.Brawlator;
import com.onaple.brawlator.action.MonsterAction;
import com.onaple.brawlator.data.beans.MonsterBean;
import com.onaple.brawlator.data.handler.ConfigurationHandler;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class MonsterCommandElement extends CommandElement {
    @Inject
    MonsterAction monsterAction;

    @Inject
    ConfigurationHandler configurationHandler;

    CommandArgs errorArgs;

    public MonsterCommandElement(Text key) {
        super(key);
    }


    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String monsterName = args.next();
        Optional<MonsterBean> monsterBeanOptional = monsterAction.getMonster(monsterName);
        if(monsterBeanOptional.isPresent()){
          return monsterBeanOptional.get();
        }
        throw errorArgs.createError(Text.of("No monster named "+ monsterName + " found."));

    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return configurationHandler.getMonsterList().stream().map(MonsterBean::getName).collect(Collectors.toList());
    }
}
