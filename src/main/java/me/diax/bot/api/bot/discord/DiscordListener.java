/*
 * Copyright 2017 Comportment | comportment@diax.me
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.diax.bot.api.bot.discord;

import me.diax.bot.DiaxProperties;
import me.diax.bot.api.MessageContentBuilder;
import me.diax.bot.api.User;
import me.diax.bot.api.channel.Channel;
import me.diax.bot.api.channel.DiscordPrivateChannel;
import me.diax.bot.api.channel.DiscordPublicChannel;
import me.diax.bot.api.command.Command;
import me.diax.bot.api.command.CommandHandler;
import me.diax.bot.api.command.CommandProvider;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.inject.Inject;
import java.sql.Timestamp;

/**
 * Created by Comportment at 17:09 on 07/05/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
public class DiscordListener extends ListenerAdapter {

    private final DiaxProperties properties;
    private final CommandProvider provider;
    private final CommandHandler handler;

    @Inject
    public DiscordListener(DiaxProperties properties, CommandProvider provider, CommandHandler handler) {
        this.properties = properties;
        this.provider = provider;
        this.handler = handler;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        String p = properties.getPrefix();
        Message message = event.getMessage();
        String content = message.getRawContent();
        MessageChannel channel = message.getChannel();
        ChannelType type = message.getChannelType();
        if (content.startsWith(p)) {
            content = content.replaceFirst(p, "");
        } else {
            if (!type.equals(ChannelType.PRIVATE)) {
                return;
            }
        }
        Channel chan = (type.equals(ChannelType.PRIVATE) ? new DiscordPrivateChannel(event.getJDA(), channel.getId()) : new DiscordPublicChannel(event.getJDA(), channel.getId()));
        Command command = provider.newInstance(provider.find(content.split(" ")[0]));
        if (command == null) return;
        net.dv8tion.jda.core.entities.User user = message.getAuthor();
        me.diax.bot.Message msg = new me.diax.bot.Message(
                message.getId(),
                new User(user.getId(), user.getName(), user.getName() + "#" + user.getDiscriminator()),
                chan,
                new MessageContentBuilder().setContent(message.getRawContent()).build(),
                new Timestamp(System.currentTimeMillis()));
        handler.execute(command, msg, content.replaceFirst(content.split(" ")[0], ""));
    }
}