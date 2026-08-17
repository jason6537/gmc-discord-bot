package com.project.ks.gmc_discord_bot.audioService;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.AudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class AudioPlayerSendHandler implements AudioSendHandler {

    private AudioPlayer audioPlayer;
    private AudioFrame lastFrame;

    public void setPlayer(AudioPlayer player) {
        this.audioPlayer = player;
    }

    @Override
    public boolean canProvide() {
        if (audioPlayer == null) return false;
        lastFrame = audioPlayer.provide();
        return lastFrame != null;
    }

    @Override
    public ByteBuffer provide20MsAudio() {
        return ByteBuffer.wrap(lastFrame.getData());
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
