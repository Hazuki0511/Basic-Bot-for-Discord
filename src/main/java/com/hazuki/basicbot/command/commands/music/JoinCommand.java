package com.hazuki.basicbot.command.commands.music;

import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class JoinCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        if (msg != null) {
            return;
        }
        var guild = e.getGuild();
        var channel = e.getChannel();
        var member = e.getMember();
        var audio = e.getGuild().getAudioManager();

        if (member == null) {
            return;
        }
        if (audio.isConnected()) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("もうすでにボイスチャンネルに接続しています").setColor(Color.RED);
            MessageEmbed embed = builder.build();

            channel.sendMessage(embed).queue(); // Send error message
            return;
        }
        var voiceState = member.getVoiceState();

        if (voiceState == null) {
            return;
        }
        if (!voiceState.inVoiceChannel()) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("まずボイスチャンネルに接続してください").setColor(Color.RED);
            MessageEmbed embed = builder.build();

            channel.sendMessage(embed).queue(); // Send error message
            return;
        }
        var voiceChannel = voiceState.getChannel();
        var selfMember = guild.getSelfMember(); // Bot self

        if (voiceChannel == null) {
            return;
        }
        if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
            EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("ボイスチャンネルに接続する権限がありません").setColor(Color.RED);
            MessageEmbed embed = builder.build();

            channel.sendMessage(embed).queue(); // Send error message
            return;
        }
        EmbedBuilder builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("ボイスチャンネルに接続しました").setColor(Color.GREEN);
        MessageEmbed embed = builder.build();

        channel.sendMessage(embed).queue(); // Send message
        audio.openAudioConnection(voiceChannel); // connect to voice channel
    }

    @Override
    public String getInvoke() {
        return "join";
    }

    @Override
    public String getHelp() {
        return "Botをボイスチャンネルに接続します";
    }

}