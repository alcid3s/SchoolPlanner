package simulation.firealarm;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

/**
 * Class AlarmSound
 * Creates a alarmsound and adds methods to stop and start the sound.
 */
public class AlarmSound {

    private AudioInputStream ais;
    private Clip clip;
    private final String path;
    private final boolean loop;

    /**
     * Constructor Alarmsound
     * Creates an object AlarmSound
     * @param path of the alarmsound
     * @param loop boolean if the sound needs to be repeated after its finished.
     */
    public AlarmSound(String path, boolean loop) {
        this.path = path;
        this.loop = loop;
        try{
            this.ais = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
            this.clip = AudioSystem.getClip();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method play
     * Activates the sound.
     */
    public void play() {
        try{
            this.clip.open(ais);
            if(this.loop){
                this.clip.loop(Clip.LOOP_CONTINUOUSLY);
            }else{
                this.clip.loop(0);
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method stop
     * Stops the sound.
     */
    public void stop() {
        this.clip.stop();
        try{
            ais = AudioSystem.getAudioInputStream(
                    new File(this.path).getAbsoluteFile());
            clip = AudioSystem.getClip();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
