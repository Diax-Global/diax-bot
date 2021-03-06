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

package me.diax.bot.api;

import me.diax.bot.api.channel.Channel;

import java.sql.Timestamp;

/**
 * Created by Comportment at 15:33 on 01/05/17
 * https://github.com/Comportment | comportment@diax.me
 *
 * @author Comportment
 */
public interface IMessage extends Identifiable {

    IUser getAuthor();

    Channel getChannel();

    MessageContent getContent();

    default String getContentAsString() {
        return getContent().getContentAsString();
    }

    Timestamp getTimestamp();
}