//Credits to TR!STAN (I just copied his code)
package JheyBot.Commands.play.musicHandler;

import JheyBot.Commands.CommandHandlers.both.JBothHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.event.AudioEventAdapter;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason;
import net.dv8tion.jda.api.entities.Guild;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import static com.sedmelluq.discord.lavaplayer.track.AudioTrackEndReason.FINISHED;

public class TrackSchedule extends AudioEventAdapter {
   public final AudioPlayer audioPlayer;
   public final BlockingDeque<AudioTrack> queue;
   private Guild guild;

   public TrackSchedule(AudioPlayer audioPlayer, Guild guild){
      this.audioPlayer = audioPlayer;
      this.queue = new LinkedBlockingDeque<>();
      this.guild = guild;
   }


   public void queue (AudioTrack track){
      if(!this.audioPlayer.startTrack(track, true)) {
         this.queue.offer(track);
      }
   }
   public void nextTrack(){
      this.audioPlayer.startTrack(this.queue.poll(), false);
   }
   @Override
   public void onTrackEnd(AudioPlayer player, AudioTrack track, AudioTrackEndReason endReason) {
      if(endReason.mayStartNext) nextTrack();
      if(endReason.equals(FINISHED)) {
         JBothHandler.afkTime.setGuild(guild);
         JBothHandler.afkTime.start();
      }
   }

   public void endTrack(){
     this.queue.clear();
   }

}
