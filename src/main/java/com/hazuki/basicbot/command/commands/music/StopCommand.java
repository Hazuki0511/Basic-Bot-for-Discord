package com.hazuki.basicbot.command.commands.music;

import com.hazuki.basicbot.audio.PlayerManager;
import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;

public class StopCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        var playerManager = PlayerManager.getInstance();
        var musicManager = playerManager.getGuildMusicManager(e.getGuild());
        var channel = e.getChannel();
        var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("曲を停止します").setColor(Color.BLUE);
        var embed = builder.build();

        channel.sendMessage(embed).queue(); // Send message
        musicManager.scheduler.getQueue().clear(); // Clear the queue
        musicManager.player.stopTrack(); // Stop the track
        musicManager.player.setPaused(false);
    }

    @Override
    public String getInvoke() {
        return "stop";
    }

    @Override
    public String getHelp() {
        return "ボイスチャンネル内で再生中の曲を停止します";
    }

}