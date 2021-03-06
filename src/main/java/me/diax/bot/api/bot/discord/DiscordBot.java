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


import me.diax.bot.ComponentProvider;
import me.diax.bot.DiaxProperties;
import me.diax.bot.api.bot.AbstractBot;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;

/**
 * Created by Comportment at 14:35 on 30/04/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
@Singleton
public class DiscordBot extends AbstractBot {

    private final String token;
    private final ComponentProvider provider;

    @Inject
    public DiscordBot(ComponentProvider provider, DiaxProperties properties) {
        this.provider = provider;
        this.token = properties.getDiscordToken();
        start();
    }

    @Override
    public void start() {
        JDA jda;
        try {
            jda = new JDABuilder(AccountType.BOT)
                    .setToken(token)
                    .addEventListener(provider.getInstance(DiscordListener.class))
                    .buildBlocking();
        } catch (Exception ignored) {
            jda = null;
        }
        shards = new JDA[]{jda};
    }

    @Override
    public void stop() {
        Arrays.stream(shards).forEach(shard -> ((JDA) shard).shutdown());
        shards = null;
    }
}