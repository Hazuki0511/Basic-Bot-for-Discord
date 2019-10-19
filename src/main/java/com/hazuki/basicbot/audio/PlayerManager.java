package com.hazuki.basicbot.audio;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PlayerManager {

    private final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();

    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    public PlayerManager() {
        AudioSourceManagers.registerRemoteSources(this.playerManager);
        AudioSourceManagers.registerLocalSource(this.playerManager);
    }

    public static synchronized PlayerManager getInstance() {
        return new PlayerManager();
    }

    public void loadAndPlay(TextChannel channel, String trackURL) {
        var musicManager = this.getGuildMusicManager(channel.getGuild());

        this.playerManager.loadItemOrdered(musicManager, trackURL, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
                var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription(audioTrack.getInfo().title + "　" + "を再生します").setColor(Color.BLUE);
                var embed = builder.build();

                channel.sendMessage(embed).queue(); // Send message
                play(musicManager, audioTrack); // Play the track
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                var firstTrack = audioPlaylist.getSelectedTrack();

                if (firstTrack == null) {
                    firstTrack = audioPlaylist.getTracks().get(0);
                }
                var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("プレイリスト:" + "　" + audioPlaylist.getName() + "　" + "から" + firstTrack.getInfo().title + "　" + "を再生します").setColor(Color.BLUE);
                var embed = builder.build();

                channel.sendMessage(embed).queue(); // Send message
                play(musicManager, firstTrack); // Play the track
            }

            @Override
            public void noMatches() {
                var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("URL:" + "　" + trackURL + "　" + "からは曲が見つかりませんでした").setColor(Color.RED);
                var embed = builder.build();

                channel.sendMessage(embed).queue(); // Send error message
            }

            @Override
            public void loadFailed(FriendlyException e) {
                var builder = new EmbedBuilder().setTitle("Basic Bot").setDescription("URL:" + "　" + trackURL + "　" + "からは曲を読み込めませんでした" + "　:　" + e.getMessage()).setColor(Color.RED);
                var embed = builder.build();

                channel.sendMessage(embed).queue(); // Send error message
            }
        });
    }

    private void play(GuildMusicManager musicManager, AudioTrack track) {
        musicManager.scheduler.queue(track);
    }

    public synchronized GuildMusicManager getGuildMusicManager(Guild g) {
        var guildID = g.getIdLong();
        var musicManager = this.musicManagers.get(guildID);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(this.playerManager);
            this.musicManagers.put(guildID, musicManager);
        }
        g.getAudioManager().setSendingHandler(musicManager.getSendHandler());
        return musicManager;
    }

}