package com.hazuki.basicbot.command.commands.music;

import com.hazuki.basicbot.audio.PlayerManager;
import com.hazuki.basicbot.command.ICommand;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class PlayCommand implements ICommand {

    @Override
    public void onMessageReceived(GuildMessageReceivedEvent e, String[] msg) {
        if (msg.length != 1) {
            return;
        }
        if (!msg[0].contains("http")) { // Check the URL
            return;
        }
        var manager = PlayerManager.getInstance();
        var guild = e.getGuild();
        var channel = e.getChannel();

        manager.getGuildMusicManager(guild).player.setVolume(10); // Set volume
        manager.loadAndPlay(channel, msg[0]); // Load track and play
    }

    @Override
    public String getInvoke() {
        return "play";
    }

    @Override
    public String getHelp() {
        return "ボイスチャンネル内で曲を再生します";
    }

}