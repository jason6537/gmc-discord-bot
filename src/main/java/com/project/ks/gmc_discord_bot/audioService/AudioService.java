package com.project.ks.gmc_discord_bot.audioService;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.concrete.VoiceChannel;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

@Service
public class AudioService {

    private final AudioPlayerManager playerManager;
    private final AudioPlayerSendHandler sendHandler;

    public AudioService(AudioPlayerManager playerManager, AudioPlayerSendHandler sendHandler) {
        this.playerManager = playerManager;
        this.sendHandler = sendHandler;
    }

    public void playMp3(Guild guild, VoiceChannel channel, String classpathMp3, AudioTrackListener listener) throws IOException {

        // 1️⃣ Copy MP3 from classpath → temp file
        File tempMp3 = copyClasspathToTempFile(classpathMp3);

        AudioPlayer player = playerManager.createPlayer();
        sendHandler.setPlayer(player);
        player.addListener(listener);

        guild.getAudioManager().setSendingHandler(sendHandler);
        guild.getAudioManager().openAudioConnection(channel);

        playerManager.loadItem(tempMp3.getAbsolutePath(), new AudioLoadResultHandler() {
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
                System.out.println("No matches found for: " + tempMp3.getAbsolutePath());
            }

            @Override
            public void loadFailed(FriendlyException exception) {
                exception.printStackTrace();
            }
        });
    }

    private File copyClasspathToTempFile(String classpathMp3) throws IOException {
        ClassPathResource resource = new ClassPathResource(classpathMp3);

        if (!resource.exists()) {
            throw new FileNotFoundException("Classpath MP3 not found: " + classpathMp3);
        }

        File tempFile = File.createTempFile("lava-audio-", ".mp3");
        tempFile.deleteOnExit();

        try (InputStream is = resource.getInputStream()) {
            Files.copy(is, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }

        return tempFile;
    }
}
