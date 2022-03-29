package simulation.firealarm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AlarmSound{

    private AudioInputStream ais;
    private Clip clip;

    public AlarmSound(){
        try{
            this.ais = AudioSystem.getAudioInputStream(new File("resources/alarm.wav").getAbsoluteFile());
            this.clip = AudioSystem.getClip();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        try{
            this.clip.open(ais);
            this.clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        this.clip.stop();
        try{
            ais = AudioSystem.getAudioInputStream(
                    new File("resources/alarm.wav").getAbsoluteFile());
            clip = AudioSystem.getClip();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
