/**
 * 
 */
package edu.its.ta.ui;

import java.io.IOException;
import java.util.Vector;

import javax.microedition.media.MediaException;
import javax.microedition.media.control.MetaDataControl;

import com.sun.lwuit.CheckBox;
import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Dialog;
import com.sun.lwuit.Form;
import com.sun.lwuit.Image;
import com.sun.lwuit.Label;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.events.FocusListener;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.util.Resources;

import edu.its.ta.Blueshmuzeec;
import edu.its.ta.control.FileControl;
import edu.its.ta.control.Mp3Player;
import edu.its.ta.control.RMSControl;
import edu.its.ta.data.Playlist;

/**
 * @author satria
 * 
 */
public class FileBrowserForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);
    private static final Command addCommand = new Command("Add", 2);
    private static final Command selectCommand = new Command("Select", 3);

    private Vector vFileList = new Vector();
    private CheckBox cb = null;
    
    private Image[] images = new Image[2];
    
    private String focused = ""; 

    private Label textLabel1 = new Label("Blueshmuzeec File Browser :");
    
    private RMSControl rmsPlaylistControl = null;
    
    public FileBrowserForm(Blueshmuzeec b) {
        super("Blueshmuzeec");

        this.addCommand(backCommand);
        this.addCommand(addCommand);
        this.setCommandListener(this);
        
        this.setBackCommand(backCommand);

        this.setScrollableX(false);
        this.setScrollableY(true);

        this.setSmoothScrolling(true);
        
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        
        try {
            Resources r = Resources.open("/blueshmuzeec.res");
            textLabel1.getStyle().setBgImage(r.getImage("Image1"), true);
            images[0] = Image.createImage("/folder_16.png");
            images[1] = Image.createImage("/file_16.png");
        } catch (Exception e) {
            System.out.println(e);
        }

        this.addComponent(textLabel1);
    }

    public void reRender() {
        FileControl fc = Blueshmuzeec.filecontrol;
        vFileList.removeAllElements();
        
        if (fc.getCurrentDirEnum() != null) {
            this.removeAll();
            this.addComponent(textLabel1);
            
            if (!fc.isRoot()) {
                cb = createCostumizeCheckBox(" ..", images[0]);
                vFileList.addElement(cb);
                this.addComponent(cb);
            }

            while (fc.getCurrentDirEnum().hasMoreElements()) {
                String s = (String) fc.getCurrentDirEnum().nextElement();
                String ext = s.substring(s.length()-3);
                //if (s.endsWith("/") || ext.equalsIgnoreCase("mp3")) {
                    cb = createCostumizeCheckBox(" " + s, (s.endsWith("/") ? images[0] : images[1]));
                    vFileList.addElement(cb);
                    this.addComponent(cb);
                //}
            }
        }
        focused = "";
        this.revalidate();
    }

    public void actionPerformed(ActionEvent arg0) {
        switch (arg0.getCommand().getId()) {
        case 1:
            if (Blueshmuzeec.filecontrol.isRoot()) {
                Blueshmuzeec.playlist.show();
            } else {
                gotoPreviousDir();
                reRender();
            }
            break;
        case 2:
            rmsPlaylistControl = new RMSControl(Playlist.RS_NAME);
            int i = this.getContentPane().getComponentCount();
            int j = 0;
            int  count = 0;
            while(j < i) {
                if (this.getContentPane().getComponentAt(j) instanceof CheckBox) {
                    CheckBox cbox = (CheckBox)this.getContentPane().getComponentAt(j);
                    boolean isSupportedContent = true, isValid = true;
                    if (cbox.isSelected()) {
                        count++;
                        Blueshmuzeec.mp3Player = new Mp3Player(Blueshmuzeec.filecontrol.getCurrentDirectory() + cbox.getText().trim());
                        MetaDataControl mdc = null;
                        String data = "";
                        try {
                            mdc = Blueshmuzeec.mp3Player.getMetaDataControlAlt();
                            if (mdc != null) {
                                data = parseMetaData(mdc, cbox.getText().trim(), Blueshmuzeec.filecontrol.getCurrentDirectory() + cbox.getText().trim());
                            } else {
                                data = getDefaultData(cbox.getText().trim(), Blueshmuzeec.filecontrol.getCurrentDirectory() + cbox.getText().trim());
                            }
                            Blueshmuzeec.mp3Player.close();
                            isValid = true;
                        } catch (MediaException mE) {
                            Dialog.show("Media Error", mE.toString(), "OK", null);
                            isSupportedContent = false;
                            isValid = false; 
                        } catch (IOException ioE) {
                            try {
                                mdc = Blueshmuzeec.mp3Player.getMetaDataControlAlt();
                                if (mdc != null) {
                                    data = parseMetaData(mdc, cbox.getText().trim(), Blueshmuzeec.filecontrol.getCurrentDirectory() + cbox.getText().trim());
                                } else {
                                    data = getDefaultData(cbox.getText().trim(), Blueshmuzeec.filecontrol.getCurrentDirectory() + cbox.getText().trim());
                                }
                                Blueshmuzeec.mp3Player.close();
                                isValid = true;
                            } catch (MediaException mE) {
                                Dialog.show("Media Error", mE.toString(), "OK", null);
                                isSupportedContent = false;
                                isValid = false;
                            } catch (IOException ioe) {
                                Dialog.show("I/O Error", ioe.toString(), "OK", null);
                                isValid = false;
                            }
                        }
                        if (isValid && isSupportedContent) {
                            if (!rmsPlaylistControl.isDataExist(data)) {
                                rmsPlaylistControl.addStringData(data);
                            }
                        }
                        mdc = null;
                    }
                    cbox = null;
                }
                j++;
            }
            rmsPlaylistControl.closeRecordStore();
            if (count > 0) {
                Blueshmuzeec.playlist.preRender();
                Blueshmuzeec.playlist.show();
            } else {
                this.setTintColor(0x8f000000);
                Dialog.show("Warning", "There is no file(s) selected. Please select one or more file(s).", "OK", null);
            }
            break;
        case 3:
            if (focused.endsWith("/")) {
                Blueshmuzeec.filecontrol.getDirContent(Blueshmuzeec.filecontrol.getCurrentDirectory() + focused.trim());
            } else {
                gotoPreviousDir();
            }
            reRender();
            break;
        }
    }
    
    private CheckBox createCostumizeCheckBox(String caption, Image icon) {
        final CheckBox cb = new CheckBox(caption, icon);
        cb.addFocusListener(new FocusListener() {
            public void focusGained(Component arg0) {
                focused = ((CheckBox)arg0).getText().trim();
                if (focused.endsWith("/") || focused.equalsIgnoreCase("..")) {
                    Blueshmuzeec.browser.removeCommand(addCommand);
                    Blueshmuzeec.browser.addCommand(selectCommand);
                } else {
                    Blueshmuzeec.browser.removeCommand(selectCommand);
                    Blueshmuzeec.browser.addCommand(addCommand);
                }
            }
            public void focusLost(Component arg0) {
            }
        });
        cb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                if (cb.getText().endsWith("/") || cb.getText().trim().equalsIgnoreCase("..")) {
                    cb.setSelected(false);
                    if (cb.getText().endsWith("/")) {
                        Blueshmuzeec.filecontrol.getDirContent(Blueshmuzeec.filecontrol.getCurrentDirectory() + cb.getText().trim());
                    } else {
                        gotoPreviousDir();
                    }
                    reRender();
                }
            }
        });
        cb.setSmoothScrolling(true);
        return cb;
    }
    
    private void gotoPreviousDir() {
        String prevDir = "";
        System.out.println("Curr: " + Blueshmuzeec.filecontrol.getCurrentDirectory());
        if (!Blueshmuzeec.filecontrol.getCurrentDirectory().trim().equals("")) {
            prevDir = Blueshmuzeec.filecontrol.getCurrentDirectory().substring(0, Blueshmuzeec.filecontrol.getCurrentDirectory().length()-1);
        }
        if (prevDir.lastIndexOf('/') < 0) {
            prevDir = "/";
        } else {
            prevDir = prevDir.substring(0, prevDir.lastIndexOf('/') + 1);
        }
        System.out.println("prev: " + prevDir);
        Blueshmuzeec.filecontrol.getDirContent(prevDir);
    }
    
    private String parseMetaData(MetaDataControl mdc, String defaultTitle, String path) {
        String title = defaultTitle, artist = "Unknown", genre = "Other", year = "Unknown", album = "Unknown";
        long duration = -99;
        
        String[] metaKeys= mdc.getKeys();
        int total = metaKeys.length;
        int cnt = 0;
        System.out.println("AAAAAAAAAA");
        while (cnt < total) {
            if (metaKeys[cnt].equalsIgnoreCase("title")) {
                title = mdc.getKeyValue("title");
            } else if (metaKeys[cnt].equalsIgnoreCase("artist")) {
                artist = mdc.getKeyValue("artist");
            } else if (metaKeys[cnt].equalsIgnoreCase("author")) {
                artist = mdc.getKeyValue("author");
            } else if (metaKeys[cnt].equalsIgnoreCase("genre")) {
                genre = mdc.getKeyValue("genre");
            } else if (metaKeys[cnt].equalsIgnoreCase("copyright")) {
                genre = mdc.getKeyValue("copyright");
            } else if (metaKeys[cnt].equalsIgnoreCase("year")) {
                year = mdc.getKeyValue("year");
            } else if (metaKeys[cnt].equalsIgnoreCase("date")) {
                year = mdc.getKeyValue("date");
            } else if (metaKeys[cnt].equalsIgnoreCase("album")) {
                album = mdc.getKeyValue("album");
            } else if (metaKeys[cnt].equalsIgnoreCase("duration")) {
                duration = Long.parseLong(mdc.getKeyValue("duration"));
            }
            cnt++;
        }
        
        String data = "{title:"+title+", artist:"+artist+", genre:"+genre+", year:"+year+", album:"+album+", duration:"+duration+", path:"+path+"}";
        
        return data;
    }
    
    private String getDefaultData(String defaultTitle, String path) {
        String title = defaultTitle, artist = "Unknown", genre = "Other", year = "Unknown", album = "Unknown";
        long duration = -99;
        
        String data = "{title:"+title+", artist:"+artist+", genre:"+genre+", year:"+year+", album:"+album+", duration:"+duration+", path:"+path+"}";
        
        return data;
    }
}
