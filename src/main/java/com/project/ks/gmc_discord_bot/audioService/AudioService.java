package com.project.ks.gmc_discord_bot.audioService;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.springframework.stereotype.Service;

@Service
public class AudioService {

    private final AudioPlayerManager playerManager;
    private final AudioPlayerSendHandler sendHandler;

    public AudioService(AudioPlayerManager playerManager, AudioPlayerSendHandler sendHandler) {
        this.playerManager = playerManager;
        this.sendHandler = sendHandler;
    }

    public void playMp3(Guild guild, VoiceChannel channel, String filePath, AudioTrackListener listener) {

        AudioPlayer player = playerManager.createPlayer();
        sendHandler.setPlayer(player);
        player.addListener(listener);

        guild.getAudioManager().setSendingHandler(sendHandler);
        guild.getAudioManager().openAudioConnection(channel);

        playerManager.loadItem(filePath, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                player.playTrack(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                player.playTrack(playlist.getTracks().get(0));
            }

            @Override
            public void noMatches() {
                System.out.println("No matches found for: " + filePath);
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });
    }
}
