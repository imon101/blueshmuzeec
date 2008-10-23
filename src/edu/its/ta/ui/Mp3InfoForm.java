/**
 * 
 */
package edu.its.ta.ui;

import com.sun.lwuit.Command;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BoxLayout;

import edu.its.ta.Blueshmuzeec;
import edu.its.ta.data.Playlist;

/**
 * @author satria
 *
 */
public class Mp3InfoForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);
    
    private Label labelTitle = new Label();
    private Label labelAlbum = new Label();
    private Label labelArtist = new Label();
    private Label labelGenre = new Label();
    private Label labelYear = new Label();
    
    public Mp3InfoForm(Blueshmuzeec b) {
        super("Blueshmuzeec");
        
        this.addCommand(backCommand);
        this.setCommandListener(this);
        
        this.setBackCommand(backCommand);
        
        this.setScrollableX(true);
        this.setScrollableY(true);
        
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        
        this.addComponent(new Label("Blueshmuzeec Mp3 info:"));
        
        this.addComponent(labelTitle);
        this.addComponent(labelArtist);
        this.addComponent(labelAlbum);
        this.addComponent(labelGenre);
        this.addComponent(labelYear);
    }
    
    public void preRender(Playlist playlist) {
        labelTitle.setText("Title: " + playlist.getTitle());
        labelArtist.setText("Artist: " + playlist.getArtist());
        labelAlbum.setText("Album: " + playlist.getAlbum());
        labelGenre.setText("Genre: " + playlist.getGenre());
        labelYear.setText("Year: " + playlist.getYear());
        
        this.revalidate();
    }
    
    public void actionPerformed(ActionEvent arg0) {
        switch (arg0.getCommand().getId()) {
        case 1:
            Blueshmuzeec.playlist.show();
            break;
        }
    }
}
