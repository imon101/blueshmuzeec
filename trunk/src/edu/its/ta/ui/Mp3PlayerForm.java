/**
 * 
 */
package edu.its.ta.ui;

import java.io.IOException;

import javax.microedition.lcdui.Ticker;

import com.sun.lwuit.Button;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Font;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.layouts.FlowLayout;
import com.sun.lwuit.plaf.Border;

import edu.its.ta.Blueshmuzeec;

/**
 * @author satria
 *
 */
public class Mp3PlayerForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);

    public Mp3PlayerForm() {
        super("Blueshmuzeec");

        this.setLayout(new BorderLayout());
        this.setScrollable(false);

        this.addCommand(backCommand);
        this.setCommandListener(this);
        this.setBackCommand(backCommand);
        
        Container settingContainer = new Container();
        settingContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        settingContainer.addComponent(new Label("repeat"));
        settingContainer.addComponent(new Label("suffle"));

        Container northContainer = new Container();
        northContainer.setLayout(new BorderLayout());
        northContainer.addComponent(BorderLayout.EAST, new Label("total track(s): 24"));
        northContainer.addComponent(BorderLayout.WEST, settingContainer);

        this.addComponent(BorderLayout.NORTH, northContainer);
        
        Container mediaContainer = new Container();
        mediaContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Image icons[] = new Image[4];
        try {
            icons[0] = Image.createImage("/art_48.png");
            icons[1] = Image.createImage("/Play1Hot_32.png");
            icons[2] = Image.createImage("/Stop1Hot_32.png");
            icons[3] = Image.createImage("/Play1Hot_32.png");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
        Container infoContainer = new Container();
        infoContainer.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        final Label imageLabel = new Label();
        imageLabel.setAlignment(Label.CENTER);
        imageLabel.getStyle().setPadding(15, 3, 0, 0);
        imageLabel.setIcon(icons[0]);
        infoContainer.addComponent(imageLabel);
        
        final Label textLabel1 = new Label("artist");
        textLabel1.setAlignment(Label.CENTER);
        textLabel1.getStyle().setPadding(0, 0, 0, 0);
        infoContainer.addComponent(textLabel1);
        
        final Label textLabel4 = new Label("title");
        textLabel4.setAlignment(Label.CENTER);
        textLabel4.getStyle().setPadding(0, 0, 0, 0);
        try {
            textLabel4.getStyle().setFont(Font.getBitmapFont("verdana_med_bold"), true);
        } catch(IOException ioE) {
            System.out.println(ioE);
        }
        infoContainer.addComponent(textLabel4);
        
        final Label textLabel5 = new Label("album");
        textLabel5.setAlignment(Label.CENTER);
        textLabel5.getStyle().setPadding(0, 0, 0, 0);
        infoContainer.addComponent(textLabel5);

        mediaContainer.addComponent(infoContainer);
        
        Container controlContainer = new Container();
        controlContainer.setHeight(50);
        controlContainer.setLayout(new BoxLayout(BoxLayout.X_AXIS));
        
        Label buttonPlay = new Label(icons[1]);
        buttonPlay.setAlignment(Label.CENTER);
        buttonPlay.setHeight(35);
        buttonPlay.setWidth(35);
        buttonPlay.getStyle().setBgTransparency(0);
        buttonPlay.getStyle().setBorder(Border.createRoundBorder(8, 8, 0xcccccc));
        buttonPlay.getStyle().setPadding(0, 0, 0, 0);
        
        controlContainer.addComponent(buttonPlay);
        Button buttonStop = new Button("Stop");
        buttonStop.setHeight(20);
        buttonStop.setWidth(20);
        buttonStop.getStyle().setBorder(Border.createRoundBorder(8, 8, 0xcccccc));
        
        controlContainer.addComponent(buttonStop);
        
        mediaContainer.addComponent(controlContainer);
        
        this.addComponent(BorderLayout.CENTER, mediaContainer);
        
        this.addComponent(BorderLayout.SOUTH, new Label("SOUTH"));
    }

    public void actionPerformed(ActionEvent arg0) {
        switch (arg0.getCommand().getId()) {
        case 1:
            Blueshmuzeec.playlist.show();
            break;
        }
    }

}
