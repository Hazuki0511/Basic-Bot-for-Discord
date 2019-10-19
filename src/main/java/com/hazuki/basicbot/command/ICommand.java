package com.hazuki.basicbot.command;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

    void onMessageReceived(GuildMessageReceivedEvent e, String[] msg);

    String getInvoke();

    String getHelp();

}