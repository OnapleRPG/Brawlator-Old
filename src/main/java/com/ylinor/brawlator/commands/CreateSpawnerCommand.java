package com.ylinor.brawlator.commands;

import com.ylinor.brawlator.Brawlator;
import com.ylinor.brawlator.data.beans.MonsterBean;
import com.ylinor.brawlator.data.beans.SpawnerBean;
import org.spongepowered.api.block.BlockState;
import org.spongepowered.api.block.BlockTypes;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.blockray.BlockRay;
import org.spongepowered.api.util.blockray.BlockRayHit;
import org.spongepowered.api.world.Location;
import org.spongepowered.api.world.World;

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

        BlockRay<World> blockRay = BlockRay.from((Player) src).skipFilter(BlockRay.onlyAirFilter()).build();
        Optional<BlockRayHit<World>> hitOptional = blockRay.end();
        if(hitOptional.isPresent()){
            hitOptional.get().getBlockPosition();
            src.sendMessage(Text.of("look at block at " + hitOptional.get().getBlockPosition().toString()));
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
        SpawnerBean spawnerBean = new SpawnerBean(hitOptional.get().getBlockPosition(),monsterBeanOptional.get(),qua,sp,ran);
        //SpawnerDAO.insert(spawnerBean);

        Brawlator.getWorld().setBlock(hitOptional.get().getBlockPosition(), BlockState.builder().blockType(BlockTypes.BARRIER).build());

        if( src instanceof Player){
            src.sendMessage(Text.builder().append(Text.of("Spawner successfully created with this parameter"))
                    .color(TextColors.GREEN).build());
            src.sendMessage(Text.builder().append(Text.of(spawnerBean.toString())).color(TextColors.GOLD).build());
        }

        return CommandResult.builder().build();
        }
        src.sendMessage(Text.builder("You must look at the block you want to change to a spawner").color(TextColors.RED).build());
        return  CommandResult.empty();
    }
}
