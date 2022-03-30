package simulation.firealarm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class AlarmSound{

    private AudioInputStream ais;
    private Clip clip;
    private String path;
    private boolean loop;

    public AlarmSound(String path, boolean loop){
        this.path = path;
        this.loop = loop;
        try{
            this.ais = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void play(){
        try{
            this.clip.open(ais);
            if(this.loop){
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                this.clip.loop(0);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void stop(){
        this.clip.stop();
        try{
            ais = AudioSystem.getAudioInputStream(
                    new File(this.path).getAbsoluteFile());
            clip = AudioSystem.getClip();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
