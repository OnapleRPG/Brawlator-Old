package com.ylinor.brawlator.commands.element;

import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.List;
import java.util.Optional;

public class MonsterCommandElement extends CommandElement {
    CommandArgs errorArgs;

    public MonsterCommandElement(Text key) {
        super(key);
    }


    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        String monsterName = args.next();
        Optional<MonsterBean> monsterBeanOptional = MonsterDAO.getMonster(monsterName);
        if(monsterBeanOptional.isPresent()){
          return monsterBeanOptional.get();
        }
        throw errorArgs.createError(Text.of("No monster named "+ monsterName + " found."));

    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return MonsterDAO.getNameList();
    }
}
