package com.hazuki.basicbot.command.commands.basic;

import com.hazuki.basicbot.Bot;
import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class HelpCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        if (msg != null) {
            return;
        }
        var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("コマンド一覧").setColor(Color.BLUE);
        Bot.getInstance().getCommandManager().getCommands().forEach(command -> builder.addField(Bot.getInstance().getCommandManager().getPrefix() + command.getInvoke(), command.getHelp(), false));

        var embed = builder.build();
        e.getChannel().sendMessage(embed).queue();
    }

    @Override
    public String getInvoke() {
        return "help";
    }

    @Override
    public String getHelp() {
        return "ヘルプを表示します";
    }

}