package edu.its.ta;

import javax.microedition.midlet.MIDlet;

import com.sun.lwuit.plaf.UIManager;
import com.sun.lwuit.util.Resources;

import edu.its.ta.control.FileControl;
import edu.its.ta.control.Mp3Player;
import edu.its.ta.ui.AboutForm;
import edu.its.ta.ui.FileBrowserForm;
import edu.its.ta.ui.MainMenuForm;
import edu.its.ta.ui.Mp3InfoForm;
import edu.its.ta.ui.Mp3PlayerForm;
import edu.its.ta.ui.PlaylistForm;
import edu.its.ta.ui.UserinfoForm;

public class Blueshmuzeec extends MIDlet {
    
    public static MainMenuForm mainMenu = null;
    public static AboutForm about = null;
    public static UserinfoForm userinfo = null;
    public static PlaylistForm playlist = null;
    public static FileBrowserForm browser = null;
    public static Mp3InfoForm mp3Info = null;
    public static Mp3PlayerForm mp3PlayerForm = null;
    
    public static FileControl filecontrol = null;
    public static Mp3Player mp3Player = null;
    
    public Blueshmuzeec() {
        com.sun.lwuit.Display.init(this);
        
        try {
            Resources r = Resources.open("/blueshmuzeec.res");
            UIManager.getInstance().setThemeProps(r.getTheme("blueshmuzeecTheme"));
        } catch (Exception e) {
            System.out.println(e);
        }
        
        mainMenu = new MainMenuForm(this);
        about = new AboutForm(this);
        userinfo = new UserinfoForm(this);
        playlist = new PlaylistForm(this);
        browser = new FileBrowserForm(this);
        mp3Info = new Mp3InfoForm(this);
        mp3PlayerForm = new Mp3PlayerForm();
        
        filecontrol = new FileControl();
        mp3Player = new Mp3Player();
    }

    protected void destroyApp(boolean arg0)  {
        notifyDestroyed();
    }

    protected void pauseApp() {
    }

    protected void startApp()  {
        mainMenu.show();
    }

}
