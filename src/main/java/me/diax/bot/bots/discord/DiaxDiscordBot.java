package me.diax.bot.bots.discord;

import me.diax.bot.DiaxCommandHandler;
import me.diax.bot.Main;
import me.diax.bot.lib.bot.AbstractDiaxAudioBot;
import me.diax.bot.lib.exceptions.BotStartException;
import me.diax.bot.lib.exceptions.BotStopException;
import me.diax.bot.lib.objects.DiaxAuthor;
import me.diax.bot.lib.objects.DiaxChannel;
import me.diax.bot.lib.objects.DiaxMessage;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.sql.Timestamp;

/**
 * Created by comportment on 17/04/17.
 * <p>
 * gr8 bot 100% working would use again.
 */
@Singleton
public class DiaxDiscordBot extends AbstractDiaxAudioBot {

    private static JDA jda;
    private DiaxCommandHandler handler;

    @Inject
    public DiaxDiscordBot(DiaxCommandHandler handler) {
        this.handler = handler;
    }

    @Override
    public DiaxDiscordBot start() throws Exception {
        if (this.hasStarted()) throw new BotStartException("Bot has already started.");
        System.out.println("Starting...");
        jda = new JDABuilder(AccountType.BOT).setToken("---").addEventListener(
                new ListenerAdapter() {
                    @Override
                    public void onMessageReceived(MessageReceivedEvent event) {
                        DiaxMessage message = new DiaxMessage(
                                new DiaxAuthor(
                                        event.getAuthor().getAvatarId(),
                                        event.getAuthor().getName()),
                                event.getMessage().getRawContent(),
                                new Timestamp(1000 * event.getMessage().getCreationTime().toEpochSecond()),
                                new DiaxChannel(event.getChannel().getId(), event.getChannel().getName()));
                        handler.execute(new Main().getInstance(DiaxDiscordBot.class), message);
                    }
                }
        ).buildBlocking();
        this.setStarted(true);
        return this;
    }

    @Override
    public DiaxDiscordBot stop() throws Exception {
        if (!this.hasStarted()) throw new BotStopException("Bot has already stopped.");
        System.out.println("Stopping...");
        jda.shutdown(false);
        this.setStarted(false);
        return this;
    }

    @Override
    public DiaxDiscordBot messageTo(DiaxChannel channel, String message) throws Exception {
        jda.getTextChannelById(channel.getIdentifier()).sendMessage(message).queue();
        return this;
    }
}
