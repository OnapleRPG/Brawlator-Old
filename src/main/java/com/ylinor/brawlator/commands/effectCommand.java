package com.ylinor.brawlator.commands;

import com.ylinor.brawlator.data.beans.EffectBean;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.dao.MonsterDAO;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;

import javax.inject.Inject;
import java.util.Optional;
import java.util.logging.Logger;

/**
 * Command to add an effect to a monster
 */
public class effectCommand implements CommandExecutor {

    @Inject
    private Logger logger;

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<MonsterBean> monsterBeanOptional = args.<MonsterBean>getOne("monster");
        if(monsterBeanOptional.isPresent()){
            MonsterBean monsterBean = monsterBeanOptional.get();
            Optional<EffectBean> effectOptional = args.<EffectBean>getOne("effect");
            if(effectOptional.isPresent()){


                monsterBean.addEffect(effectOptional.get());
            }
            MonsterDAO.update(monsterBean);
            return CommandResult.success();
        } else {
            return CommandResult.empty();
        }
    }
}
