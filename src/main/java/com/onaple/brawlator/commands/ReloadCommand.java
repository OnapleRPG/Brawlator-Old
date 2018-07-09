package com.onaple.brawlator.commands;

import com.onaple.brawlator.Brawlator;
import org.spongepowered.api.command.CommandException;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.spec.CommandExecutor;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.format.TextColors;

public class ReloadCommand implements CommandExecutor{

    @Override
    public CommandResult execute(CommandSource src, CommandContext args) throws CommandException {
        try{
            int qte = Brawlator.getBrawlator().loadMonsters();
            src.sendMessage(Text.builder()
                    .append(Text.builder("Monsters configuration successfully reloaded. ").color(TextColors.GREEN).build())
                    .append(Text.builder("" + qte).color(TextColors.GOLD).build())
                    .append(Text.builder(" monster loaded.").color(TextColors.GREEN).build())
                    .build());

        } catch (Exception e) {
           writeError(src,e);
        }
        try{
           int spawnerQte = Brawlator.getBrawlator().loadSpawners();
            src.sendMessage(Text.builder()
                    .append(Text.builder("Spawner configuration successfully reloaded. ").color(TextColors.GREEN).build())
                    .append(Text.builder("" + spawnerQte).color(TextColors.GOLD).build())
                    .append(Text.builder(" spawners loaded.").color(TextColors.GREEN).build())
                    .build());
        } catch (Exception e) {
            writeError(src,e);
        }
        try {
            int lootQte = Brawlator.getBrawlator().loadLootTables();
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

    private void writeError(CommandSource src,Exception e) {
        src.sendMessage(Text.builder()
                .append(Text.builder("configuration reload failed. ").color(TextColors.DARK_RED).build())
                .append(Text.builder(e.getMessage()).color(TextColors.RED).build())
                .build());
    }


}
