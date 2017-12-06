package com.ylinor.brawlator.commands.element;

import com.ylinor.brawlator.data.beans.EffectBean;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


public class EffectCommandElement extends CommandElement {
    CommandArgs errorArgs;

    public EffectCommandElement(Text key) {
        super(key);
    }

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        errorArgs = args;

        String effectInput = args.next().toUpperCase();

        if(!EffectBean.exist(effectInput)){
            throw errorArgs.createError(Text.of("Effect named : " + effectInput + " does not exist"));
        }
        Optional<String> amplifierInputOptionnal = args.nextIfPresent();
        Optional<String> durationInputOptionnal = args.nextIfPresent();
        int amplifier = 1;
        int duration = 99999;
        if (amplifierInputOptionnal.isPresent()) {
             amplifier = parseInt(amplifierInputOptionnal.get());
        }
        if (durationInputOptionnal.isPresent()) {
            duration  = parseInt(durationInputOptionnal.get());
        }
        return new EffectBean(effectInput, amplifier, duration);
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
    @Override
    public Text getUsage(CommandSource src) {
        return  Text.of("<Effect type> [Amplifier] [duration]");
    }
}
