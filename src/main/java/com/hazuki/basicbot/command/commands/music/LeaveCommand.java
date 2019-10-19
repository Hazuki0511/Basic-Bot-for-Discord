package com.hazuki.basicbot.command.commands.music;

import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class LeaveCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        if (msg != null) {
            return;
        }
        var channel = e.getChannel();
        var member = e.getMember();
        var audio = e.getGuild().getAudioManager();

        if (member == null) {
            return;
        }
        if (!audio.isConnected()) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("まだボイスチャンネルに接続していません").setColor(Color.RED);
            MessageEmbed embed = builder.build();

            channel.sendMessage(embed).queue(); // Send error message
            return;
        }
        var voiceChannel = audio.getConnectedChannel();

        if (!voiceChannel.getMembers().contains(member)) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("このコマンドを使うにはBotと同じボイスチャンネルに接続してください").setColor(Color.RED);
            MessageEmbed embed = builder.build();

            channel.sendMessage(embed).queue(); // Send error message
            return;
        }
        EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("ボイスチャンネルから退出しました").setColor(Color.GREEN);
        MessageEmbed embed = builder.build();

        channel.sendMessage(embed).queue(); // Send message
        audio.closeAudioConnection(); // Leave from voice channel
    }

    @Override
    public String getInvoke() {
        return "leave";
    }

    @Override
    public String getHelp() {
        return "Botをボイスチャンネルから退出させます";
    }

}