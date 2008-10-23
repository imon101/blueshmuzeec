/**
 * 
 */
package edu.its.ta.ui;

import java.io.IOException;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextArea;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.FlowLayout;

import edu.its.ta.Blueshmuzeec;

/**
 * @author satria
 *
 */
public class AboutForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);
    
    public AboutForm(Blueshmuzeec b) {
        super("Blueshmuzeec");
        this.addCommand(backCommand);
        this.setCommandListener(this);
        this.setBackCommand(backCommand);
        
        this.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Image icon = null;
        try {
            icon = Image.createImage("/icon_48.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        final Label imageLabel = new Label();
        imageLabel.setAlignment(Label.CENTER);
        imageLabel.getStyle().setPadding(5, 5, 7, 7);
        imageLabel.setIcon(icon);
        Container imagePanel = new Container(new FlowLayout(Component.CENTER));
        imagePanel.addComponent(imageLabel);
        this.addComponent(imagePanel);
        
        final Label textLabel1 = new Label("Blueshmuzeec v.1.0.0");
        textLabel1.setAlignment(Label.CENTER);
        textLabel1.getStyle().setPadding(0, 0, 0, 0);
        this.addComponent(textLabel1);
        
        final Label textLabel4 = new Label("by satria.permana");
        textLabel4.setAlignment(Label.CENTER);
        textLabel4.getStyle().setPadding(0, 0, 0, 0);
        this.addComponent(textLabel4);
        
        TextArea aboutText = new TextArea(getAboutText(), 5, 10);
        aboutText.setEditable(false);
        aboutText.getStyle().setPadding(10, 0, 3, 3);
        
        this.addComponent(aboutText);
        
        Transition in, out;
        out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 500);
        in = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 500);
        this.setTransitionInAnimator(in);
        this.setTransitionOutAnimator(out);
    }
    
    public void actionPerformed(ActionEvent arg0) {
        // TODO Auto-generated method stub
        Blueshmuzeec.mainMenu.show();
    }
    
    private String getAboutText() {
        return "Blueshmuzeec is a Mobile Music Player that can predicts the user music preference(s). Blueshmuzeec also support for transfer music-files (MP3) that match with user music preference(2) accross bluetooth network.";
    }
}
