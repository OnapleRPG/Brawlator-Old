package com.onaple.brawlator.commands.element;

import com.onaple.brawlator.data.beans.EffectBean;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.effect.potion.PotionEffectType;
import org.spongepowered.api.text.Text;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class EffectCommandElement extends CommandElement {
    CommandArgs errorArgs;

    public EffectCommandElement(Text key) {
        super(key);
    }

    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {
        errorArgs = args;

        String effectInput = args.next();
        Optional<PotionEffectType> effectType = Sponge.getRegistry().getType(PotionEffectType.class,effectInput);
        if(!effectType.isPresent()){
            throw errorArgs.createError(Text.of("Effect named : " + effectInput + " does not exist"));
        }
        Optional<String> amplifierInputOptionnal = args.nextIfPresent();
        int amplifier = 1;
        if (amplifierInputOptionnal.isPresent()) {
             amplifier = parseInt(amplifierInputOptionnal.get());
        }
        return new EffectBean(effectType.get(), amplifier);
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
        return Sponge.getRegistry().getAllOf(PotionEffectType.class)
                .stream().map(potionEffectType -> potionEffectType.getName()).collect(Collectors.toList());
    }
    @Override
    public Text getUsage(CommandSource src) {
        return  Text.of("<Effect type> [Amplifier]");
    }
}
