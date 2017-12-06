package com.ylinor.brawlator.commands.element;

import org.spongepowered.api.command.CommandSource;
import org.spongepowered.api.command.args.ArgumentParseException;
import org.spongepowered.api.command.args.CommandArgs;
import org.spongepowered.api.command.args.CommandContext;
import org.spongepowered.api.command.args.CommandElement;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;

import java.util.ArrayList;
import java.util.List;

public class Vector3iCommandElement extends CommandElement {

    CommandArgs errorArgs;

    public Vector3iCommandElement(Text key) {
        super(key);
    }

    private int x,y,z =0;
    @Override
    protected Object parseValue(CommandSource source, CommandArgs args) throws ArgumentParseException {

         x = parseInt(args.next());
         y = parseInt(args.next());
         z = parseInt(args.next());
        return null;
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
       Player p = (Player)src;
        if(x ==0) {
          return new ArrayList<>(p.getTransform().getPosition().getFloorX());
        }
        if(y==0) {
            return new ArrayList<>(p.getTransform().getPosition().getFloorX());
        }
        if(z==0) {
            return new ArrayList<>(p.getTransform().getPosition().getFloorX());
        }
        return null;
    }

}
