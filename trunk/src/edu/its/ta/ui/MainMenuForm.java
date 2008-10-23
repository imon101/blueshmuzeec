/**
 * 
 */
package edu.its.ta.ui;

import java.io.IOException;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.util.Resources;

import edu.its.ta.Blueshmuzeec;

/**
 * @author satria
 *
 */
public class MainMenuForm extends Form implements ActionListener {
    
    private Blueshmuzeec blueshmuzeec = null;
    
    private static final Command exitCommand = new Command("Exit", 1);
    private static final Command aboutCommand = new Command("About", 2);
    
    public MainMenuForm(Blueshmuzeec b) {
        super("Blueshmuzeec");
        
        this.blueshmuzeec = b;
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        
        Label textLabel = new Label("Blueshmuzeec Main Menu :");
        this.addComponent(textLabel);
      
        Image icon[] = new Image[3];
        Resources r = null;
        try {
            icon[0] = Image.createImage("/headphones_24.png");
            icon[1] = Image.createImage("/disk_jockey_24.png");
            icon[2] = Image.createImage("/network_24.png");
            r = Resources.open("/blueshmuzeec.res");
            textLabel.getStyle().setBgImage(r.getImage("Image1"), true);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Button buttonMusic = new Button(" Music Player");
        buttonMusic.setIcon(icon[0]);
        buttonMusic.getStyle().setMargin(1, 0, 2, 2);
        buttonMusic.getStyle().setPadding(3, 3, 10, 3);
        buttonMusic.setAlignment(Label.LEFT);
        buttonMusic.getStyle().setBorder(Border.createRoundBorder(8, 8, 0xcccccc));
        buttonMusic.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Blueshmuzeec.playlist.preRender();
                Blueshmuzeec.playlist.show();
            }
        });
        
        Button buttonProfile = new Button(" User Info");
        buttonProfile.setIcon(icon[1]);
        buttonProfile.getStyle().setMargin(1, 0, 2, 2);
        buttonProfile.getStyle().setPadding(3, 3, 10, 3);
        buttonProfile.setAlignment(Label.LEFT);
        buttonProfile.getStyle().setBorder(Border.createRoundBorder(8, 8, 0xcccccc));
        buttonProfile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                // TODO Auto-generated method stub
                Blueshmuzeec.userinfo.preRender();
                Blueshmuzeec.userinfo.show();
            }
        });
        
        Button buttonConnection = new Button(" Bluesh Network");
        buttonConnection.setIcon(icon[2]);
        buttonConnection.getStyle().setMargin(1, 0, 2, 2);
        buttonConnection.getStyle().setPadding(3, 3, 10, 3);
        buttonConnection.setAlignment(Label.LEFT);
        buttonConnection.getStyle().setBorder(Border.createRoundBorder(8, 8, 0xcccccc));
        
        this.addComponent(buttonMusic);
        this.addComponent(buttonProfile);
        this.addComponent(buttonConnection);
        
        this.addCommand(exitCommand);
        this.addCommand(aboutCommand);
        this.setCommandListener(this);
    }
    
    public void actionPerformed(ActionEvent arg0) {
        Command cmd = arg0.getCommand();
        switch (cmd.getId()) {
            case 1:
                Blueshmuzeec.userinfo.getRmsUserinfoControl().closeRecordStore();
                Blueshmuzeec.playlist.getRmsPlaylistControl().closeRecordStore();
                blueshmuzeec.notifyDestroyed();
                break;
            case 2:
                Blueshmuzeec.about.show();
                break;
        }
    }
}
