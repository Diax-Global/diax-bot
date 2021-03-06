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

package me.diax.bot.api.channel;

import net.dv8tion.jda.core.JDA;

/**
 * Created by Comportment at 19:20 on 07/05/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
public class DiscordPrivateChannel extends DiscordChannel {

    public DiscordPrivateChannel(JDA jda, String id) {
        super(id, ChannelType.PRIVATE, jda);
    }

    @Override
    public void sendMessage(String message) {
        jda.getPrivateChannelById(getId()).sendMessage(message).queue();
    }
}