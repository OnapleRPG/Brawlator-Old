package com.onaple.brawlator.commands;

import com.onaple.brawlator.Brawlator;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

import javax.inject.Inject;

public class ReloadCommand implements CommandExecutor{
    @Inject
    private Brawlator brawlator;

    /**
     * Reload the different configuration files
     *
     * @param src Source of the command
     * @param args Arguments given to the command
     * @return Command result
     * @throws CommandException Command exception
     */
    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try{
            int qte = brawlator.loadMonsters();
            src.sendMessage(Text.builder()
                    .append(Text.builder("Monsters configuration successfully reloaded. ").color(TextColors.GREEN).build())
                    .append(Text.builder("" + qte).color(TextColors.GOLD).build())
                    .append(Text.builder(" monster loaded.").color(TextColors.GREEN).build())
                    .build());

        } catch (Exception e) {
           writeError(src,e);
        }
        try{
           int spawnerQte = brawlator.loadSpawners();
            src.sendMessage(Text.builder()
                    .append(Text.builder("Spawner configuration successfully reloaded. ").color(TextColors.GREEN).build())
                    .append(Text.builder("" + spawnerQte).color(TextColors.GOLD).build())
                    .append(Text.builder(" spawners loaded.").color(TextColors.GREEN).build())
                    .build());
        } catch (Exception e) {
            writeError(src,e);
        }
        try {
            int lootQte = brawlator.loadLootTables();
            src.sendMessage(Text.builder()
                    .append(Text.builder("LootTable configuration successfully reloaded.. ").color(TextColors.GREEN).build())
                    .append(Text.builder("" + lootQte).color(TextColors.GOLD).build())
                    .append(Text.builder(" loot tables loaded.").color(TextColors.GREEN).build())
                    .build());
        } catch (Exception e) {
            writeError(src,e);
        }
        return CommandResult.success();
    }

    /**
     * Send an error to the command executor
     * @param src Source of the command
     * @param e Exception to report
     */
    private void writeError(CommandSource src, Exception e) {
        src.sendMessage(Text.builder()
                .append(Text.builder("Configuration reload failed : ").color(TextColors.DARK_RED).build())
                .append(Text.builder(e.getMessage()).color(TextColors.RED).build())
                .build());
    }
}
