/**
 * 
 */
package edu.its.ta.ui;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.rms.InvalidRecordIDException;
import javax.microedition.rms.RecordEnumeration;

import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.list.ListCellRenderer;
import com.sun.lwuit.plaf.Border;
import com.sun.lwuit.util.Resources;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.List;

import edu.its.ta.Blueshmuzeec;
import edu.its.ta.control.Mp3Player;
import edu.its.ta.control.RMSControl;
import edu.its.ta.data.Playlist;

/**
 * @author satria
 *
 */
public class PlaylistForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);
    private static final Command playCommand = new Command("Play", 2);
    private static final Command infoCommand = new Command("Info", 3);
    private static final Command delCommand = new Command("Remove", 4);
    private static final Command addCommand = new Command("Add", 5);
    private static final Command stopCommand = new Command("Stop", 6);
    private static final Command nowPlayingCommand = new Command("Now Playing", 7);
    private static final Command removeAllCommand = new Command("RemoveAll", 8);

    private static final Command yesCommand = new Command("Yes");
    private static final Command noCommand = new Command("No");

    private Vector vMusicList = new Vector();
    private List list = null;

    private Label textLabel1 = new Label("Blueshmuzeec Playlist :");

    private RMSControl rmsPlaylistControl = null;
    private Playlist playlist = null;

    public PlaylistForm(Blueshmuzeec b) {
        super("Blueshmuzeec");

        this.addCommand(backCommand);
        this.addCommand(addCommand);
        this.setCommandListener(this);

        this.setBackCommand(backCommand);

        this.setScrollable(false);

        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);

        textLabel1.setText("Blueshmuzeec Playlist" + " (" + vMusicList.size() + ")" + " :");
        try {
            Resources r = Resources.open("/blueshmuzeec.res");
            textLabel1.getStyle().setBgImage(r.getImage("Image1"), true);
        } catch (Exception e) {
            System.out.println(e);
        }

        this.addComponent(textLabel1);

        playlist = new Playlist();
        rmsPlaylistControl = new RMSControl(Playlist.RS_NAME);

        Transition in, out;
        out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 500);
        in = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 500);
        this.setTransitionInAnimator(in);
        this.setTransitionOutAnimator(out);
    }

    public void actionPerformed(ActionEvent arg0) {
        int index = 0;
        switch (arg0.getCommand().getId()) {
        case 1:
            Blueshmuzeec.mainMenu.show();
            break;
        case 2:
            try {
                index = Integer.parseInt(getPlaylistIndex());
                playMusic(index);
                Blueshmuzeec.mp3PlayerForm.show();
            } catch(NumberFormatException nfE) {
                
            }
            break;
        case 3:
            try {
                index = Integer.parseInt(getPlaylistIndex());
                playlist.parsePlaylistData(new String(rmsPlaylistControl.readStringData(index)));
                Blueshmuzeec.mp3Info.preRender(playlist);
                Blueshmuzeec.mp3Info.show();
            } catch (NumberFormatException nfE) {

            }
            break;
        case 4:
            Command result = Dialog.show("Confirmation", "Remove from playlist?", yesCommand, new Command[] { yesCommand, noCommand }, 0, null, 0,
                    CommonTransitions.createSlide(CommonTransitions.SLIDE_VERTICAL, true, 1000));
            if (result == yesCommand) {
                try {
                    index = Integer.parseInt(getPlaylistIndex());
                    rmsPlaylistControl.deleteStringData(index);
                    preRender();
                } catch (NumberFormatException nfE) {

                }
            }
            break;
        case 5:
            Blueshmuzeec.filecontrol.getDirContent("/");
            Blueshmuzeec.browser.reRender();
            Blueshmuzeec.browser.show();
            break;
        case 6:
            stopMusic();
            break;
        case 7:
            Blueshmuzeec.mp3PlayerForm.show();
            break;
        }
    }

    public void preRender() {
        RecordEnumeration re = rmsPlaylistControl.getEnumerationRecordStore();
        vMusicList.removeAllElements();
        if (re.numRecords() > 0) {
            while (re.hasNextElement()) {
                try {
                    int nextId = re.nextRecordId();
                    byte[] tmp = rmsPlaylistControl.readStringData(nextId);
                    String data = "";
                    if (tmp != null) {
                        data = new String(tmp);
                        playlist.parsePlaylistData(data);
                        vMusicList.addElement(playlist.getTitle() + " [" + nextId + "]");
                    }
                } catch (InvalidRecordIDException iriE) {
                    iriE.printStackTrace();
                }
            }
        }

        this.removeAll();

        textLabel1.setText("Blueshmuzeec Playlist" + " (" + vMusicList.size() + ")" + " :");
        this.addComponent(textLabel1);

        this.removeAllCommands();
        if (vMusicList.size() > 0) {
            resetMusicStoppedCommand();
            
            list = new List(vMusicList);
            list.setSmoothScrolling(false);
            list.getStyle().setBorder(Border.getEmpty());
            list.getStyle().setBgTransparency(0);
            list.setListCellRenderer(new PlaylistRenderer());
            list.setOrientation(List.VERTICAL);
            list.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        if (!Blueshmuzeec.mp3Player.isPlaying()) {
                            int index = Integer.parseInt(getPlaylistIndex());
                            playMusic(index);
                        } else {
                            stopMusic();
                        }
                    } catch (NumberFormatException nfE) {

                    }
                }
            });
            this.addComponent(list);
        } else {
            this.addCommand(backCommand);
            this.addCommand(addCommand);
            this.setCommandListener(this);
        }

        this.revalidate();
    }

    class PlaylistRenderer extends Container implements ListCellRenderer {

        private Label title = new Label("");
        private Label pic = new Label("");
        private Image image = null;

        private Label focus = new Label("");

        public PlaylistRenderer() {
            setLayout(new BorderLayout());
            addComponent(BorderLayout.WEST, pic);
            Container cnt = new Container(new BoxLayout(BoxLayout.Y_AXIS));
            title.setFocusable(true);
            title.getStyle().setBgTransparency(0);
            cnt.addComponent(title);
            addComponent(BorderLayout.CENTER, cnt);

            focus.getStyle().setBgColor(0xffffff);
            focus.getStyle().setFgColor(0x313131);
            focus.getStyle().setBgTransparency(100);
            focus.setFocusable(true);

            pic.getStyle().setBgColor(0xffffff);
            pic.getStyle().setBgTransparency(50);

            try {
                image = Image.createImage("/list_16.png");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        public Component getListCellRendererComponent(List list, Object value, int index, boolean isSelected) {
            title.setText((String) value);
            pic.setIcon(image);
            return this;
        }

        public Component getListFocusComponent(List list) {
            return focus;
        }
    }

    public RMSControl getRmsPlaylistControl() {
        return rmsPlaylistControl;
    }

    public void playMusic(int index) {
        playlist.parsePlaylistData(new String(rmsPlaylistControl.readStringData(index)));

        Blueshmuzeec.mp3Player = new Mp3Player();
        Blueshmuzeec.mp3Player.play(playlist.getPath());
        resetMusicPlayedCommand();
    }

    public void stopMusic() {
        Blueshmuzeec.mp3Player.stop();
        resetMusicStoppedCommand();
    }

    public String getPlaylistIndex() {
        String tmp = (String) list.getSelectedItem();
        tmp = tmp.substring(tmp.lastIndexOf('[') + 1).trim();
        tmp = tmp.substring(0, tmp.lastIndexOf(']')).trim();

        return tmp;
    }

    public void resetMusicPlayedCommand() {
        this.removeAllCommands();
        this.addCommand(stopCommand);
        this.addCommand(backCommand);
        this.addCommand(infoCommand);
        this.addCommand(delCommand);
        this.addCommand(removeAllCommand);
        this.addCommand(addCommand);
        this.addCommand(nowPlayingCommand);
        this.setCommandListener(this);
    }

    public void resetMusicStoppedCommand() {
        this.removeAllCommands();
        this.addCommand(playCommand);
        this.addCommand(backCommand);
        this.addCommand(infoCommand);
        this.addCommand(delCommand);
        this.addCommand(removeAllCommand);
        this.addCommand(addCommand);
        this.addCommand(nowPlayingCommand);
        this.setCommandListener(this);
    }
}
