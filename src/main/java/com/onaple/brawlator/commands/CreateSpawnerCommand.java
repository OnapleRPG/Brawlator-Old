package com.onaple.brawlator.commands;

import com.onaple.brawlator.data.beans.MonsterBean;
import com.onaple.brawlator.data.beans.SpawnerBean;
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

    /**
     * Generate a spawner at the given position for the given monster
     * @param src Source which executed the command
     * @param args List of arguments
     * @return Command result
     * @throws CommandException Command exception
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        Optional<Location> position = args.<Location>getOne("position");
        Optional<MonsterBean> monsterBeanOptional = args.<MonsterBean>getOne("monster");
        Optional<Integer> rangeOptional = args.<Integer>getOne("range");
        Optional<Integer> spawnRateOptional = args.<Integer>getOne("spawnrate");
        Optional<Integer> quantityOptional = args.<Integer>getOne("quantity");

        int spawnRate, range, quantity;

        BlockRay<World> blockRay = BlockRay.from((Player) src).skipFilter(BlockRay.onlyAirFilter()).build();
        Optional<BlockRayHit<World>> hitOptional = blockRay.end();
        if (hitOptional.isPresent()) {
            hitOptional.get().getBlockPosition();
            src.sendMessage(Text.of("look at block at " + hitOptional.get().getBlockPosition().toString()));
            if (!monsterBeanOptional.isPresent()) {
                return CommandResult.empty();
            }
            spawnRate = spawnRateOptional.orElse(5); // Default rate if spawnRateOptional is empty
            range = rangeOptional.orElse(20); // Default range if rangeOptional is empty
            quantity = quantityOptional.orElse(5); // Default quantity if quantityOptional is empty

            SpawnerBean spawnerBean = new SpawnerBean(hitOptional.get().getBlockPosition(),
                    monsterBeanOptional.get(), quantity, spawnRate, range);

            hitOptional.get().getLocation().setBlock(BlockState.builder().blockType(BlockTypes.BARRIER).build());

            if(src instanceof Player){
                src.sendMessage(Text.builder().append(Text.of("Spawner successfully created with the following parameters :"))
                        .color(TextColors.GREEN).build());
                src.sendMessage(Text.builder().append(Text.of(spawnerBean.toString())).color(TextColors.GOLD).build());
            }

            return CommandResult.builder().build();
        } else {
            src.sendMessage(Text.builder("You must look at the block you want to change to a spawner").color(TextColors.RED).build());
            return CommandResult.empty();
        }
    }
}
