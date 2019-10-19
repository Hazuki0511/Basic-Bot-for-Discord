package com.hazuki.basicbot.command.commands.basic;

import com.hazuki.basicbot.Bot;
import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class StopBotCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        if (msg != null) {
            return;
        }
        EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("Botを終了しています").setColor(Color.RED);
        MessageEmbed embed = builder.build();

        e.getChannel().sendMessage(embed).queue(); // Send stop message
        Bot.getInstance().stopBot(); // Stop the bot
    }

    @Override
    public String getInvoke() {
        return "stopbot";
    }

    @Override
    public String getHelp() {
        return "Botを終了させます";
    }

}