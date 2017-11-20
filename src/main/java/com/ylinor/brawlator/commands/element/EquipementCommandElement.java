package com.ylinor.brawlator.commands.element;

import com.ylinor.brawlator.data.beans.EquipementBean;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class EquipementCommandElement extends CommandElement {
    CommandArgs errorArgs;

    public EquipementCommandElement(Text key) {
        super(key);
    }

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        errorArgs = args;
        String equipementNameInput = args.next();
        Optional<String> idOptionnal = args.nextIfPresent();
        int id = 0;
        if(idOptionnal.isPresent()){
            id = parseInt(idOptionnal.get());
        }
        return new EquipementBean(equipementNameInput, id);
    }

    private int parseInt(String input) throws ArgumentParseException {
        try {
            return Integer.parseInt(input);
        } catch(NumberFormatException e) {
            throw errorArgs.createError(Text.of("'" + input + "' is not a valid number!"));
        }
    }

    @Override
    public List<String> complete(CommandSource src, CommandArgs args, CommandContext context) {
        return Collections.emptyList();
    }
}
