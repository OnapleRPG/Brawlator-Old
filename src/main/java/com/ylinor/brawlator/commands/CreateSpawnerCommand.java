package com.ylinor.brawlator.commands;

import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import com.ylinor.brawlator.data.dao.SpawnerDAO;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.cause.Cause;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.world.Location;

import java.util.Optional;

/**
 * Command to create a spawner
 */
public class CreateSpawnerCommand implements CommandExecutor {

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Location> position = args.<Location>getOne("position");
        Optional<MonsterBean> monsterBeanOptional = args.<MonsterBean>getOne("monster");
        Optional<Integer>  range = args.<Integer>getOne("range");
        Optional<Integer>  spawnRate = args.<Integer>getOne("spawnrate");
        Optional<Integer> quantity = args.<Integer>getOne("quantity");

        int sp,ran,qua;
        if (!position.isPresent()) {
            return CommandResult.empty();
        }
        if (!monsterBeanOptional.isPresent()) {
            return CommandResult.empty();
        }
        if (spawnRate.isPresent()) {
            sp = spawnRate.get();
        } else {
            sp = 5;//default value if spawnRate is empty
        }
        if (quantity.isPresent()) {
            qua = quantity.get();
        } else {
            qua = 5;//default value if quantity is empty
        }
        if (range.isPresent()) {
            ran = range.get();
        } else {
            ran = 20;//default value if range is empty
        }
        SpawnerBean spawnerBean = new SpawnerBean(position.get().getBlockPosition(),monsterBeanOptional.get(),qua,sp,ran);
        SpawnerDAO.insert(spawnerBean);
        Brawlator.getWorld().setBlock(position.get().getBlockPosition(), BlockState.builder().blockType(BlockTypes.BARRIER).build());
        if( src instanceof Player){
            src.sendMessage(Text.builder().append(Text.of("Spawner successfully created with this parameter"))
                    .color(TextColors.GREEN).build());
            src.sendMessage(Text.builder().append(Text.of(spawnerBean.toString())).color(TextColors.GOLD).build());
        }

        return CommandResult.builder().build();
    }
}
