/**
 * 
 */
package edu.its.ta.control;

import java.io.IOException;

import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;
import javax.microedition.media.PlayerListener;
import javax.microedition.media.control.MetaDataControl;

import edu.its.ta.Blueshmuzeec;

/**
 * @author satria
 *
 */
public class Mp3Player implements Runnable, PlayerListener {

    private String fileName = "";

    private MetaDataControl metaDataControl = null;
    private Player player = null;

    private Thread tPlayer = null;
    
    private boolean isPlaying = false;

    public Mp3Player(String fileName) {
        this.fileName = fileName;
        isPlaying = false;
    }
    
    public Mp3Player() {
        isPlaying = false;
    }

    public void play(String fileName) {
        this.fileName = fileName;
        tPlayer = new Thread(this);
        tPlayer.start();
    }

    public void pause() {
        try {
            player.stop();
        } catch (MediaException mE) {

        }
    }

    public void resume() {
        try {
            player.start();
        } catch (MediaException mE) {

        }
    }

    public void stop() {
        try {
            player.stop();
            player.close();
            tPlayer = null;
            isPlaying = false;
        } catch (MediaException mE) {

        }
    }

    public MetaDataControl getMetaDataControl() throws MediaException, IOException {
        player = null;
        player = Manager.createPlayer("file://localhost/" + fileName.trim());
        player.prefetch();
        metaDataControl = (MetaDataControl) player.getControl("MetaDataControl");
        return metaDataControl;
    }

    public MetaDataControl getMetaDataControlAlt() throws MediaException, IOException {
        player = null;
        player = Manager.createPlayer("file:///" + fileName.trim());
        player.prefetch();
        metaDataControl = (MetaDataControl) player.getControl("MetaDataControl");
        return metaDataControl;
    }

    public void close() {
        if (player != null) {
            player.close();
            isPlaying = false;
        }
    }

    public void playerUpdate(Player arg0, String arg1, Object arg2) {
        // TODO Auto-generated method stub
        if (arg1.equalsIgnoreCase(PlayerListener.END_OF_MEDIA)) {
            arg0.close();
            Blueshmuzeec.playlist.resetMusicStoppedCommand();
            isPlaying = false;
        }
    }

    public void run() {
        player = null;
        try {
            player = Manager.createPlayer("file:///" + fileName.trim());

            player.addPlayerListener(this);
            player.prefetch();
            player.realize();
            player.start();
            isPlaying = true;
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public boolean isPlaying() {
        return isPlaying;
    }
}
