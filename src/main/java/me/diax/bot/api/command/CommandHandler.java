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

package me.diax.bot.api.command;

import me.diax.bot.api.IMessage;

import javax.inject.Singleton;

/**
 * Created by Comportment at 21:10 on 01/05/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
@Singleton
public class CommandHandler {

    public CommandHandler() {
    }

    public void execute(Command command, IMessage message, String args) {
        CommandDescription cd = command.getClass().getAnnotation(CommandDescription.class);
        if (cd.args() > args.split(" ").length) {
            return;
        }
        try {
            command.execute(message, args);
        } catch (Exception e) {
            message.getChannel().sendMessage("An error has occurred.");
            e.printStackTrace();
        }
    }
}