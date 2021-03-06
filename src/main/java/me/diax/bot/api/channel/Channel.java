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

import me.diax.bot.api.Identifiable;
import me.diax.bot.api.MessageContent;

/**
 * Created by Comportment at 15:41 on 01/05/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
public interface Channel extends Identifiable {

    ChannelType getType();

    void sendMessage(String message);

    default void sendMessage(MessageContent content) {
        sendMessage(content.getContentAsString());
    }

    default void sendMessages(String... messages) {
        for (String message : messages) {
            sendMessage(message);
        }
    }

    default void sendMessages(MessageContent... contents) {
        for (MessageContent content : contents) {
            sendMessage(content);
        }
    }
}