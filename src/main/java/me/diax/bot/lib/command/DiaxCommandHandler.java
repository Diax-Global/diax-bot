package me.diax.bot.lib.command;

import me.diax.bot.lib.AbstractDiaxBot;
import me.diax.bot.lib.exceptions.NotEnoughArgsException;
import me.diax.bot.lib.objects.DiaxMessage;

/**
 * Created by Comportment on 18/04/17.
 * <p>
 * Crystal can do a lot better.
 */
public class DiaxCommandHandler {

    private final String prefix = "<>";

    public boolean execute(AbstractDiaxBot bot, DiaxMessage input) {
        if (!input.getContent().startsWith(prefix)) return false;
        String content = input.getContent().replaceFirst(prefix, "").trim();
        DiaxCommandDescription description = new DiaxCommands().find(content.split(" ")[0]);
        if (description == null) return false;
        try {
            if (content.split(" ").length < description.minimumArgs())
                throw new NotEnoughArgsException(description.name() + " needs " + description.minimumArgs() + " args.");
            return new DiaxCommands().newInstance(description).execute(bot, input, content);
        } catch (Exception e) {
            System.err.println("Error executing: " + description.name());
            return false;
        }
    }
}