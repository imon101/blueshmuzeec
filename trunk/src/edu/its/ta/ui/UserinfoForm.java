/**
 * 
 */
package edu.its.ta.ui;

import com.sun.lwuit.Command;
import com.sun.lwuit.Component;
import com.sun.lwuit.Container;
import com.sun.lwuit.Form;
import com.sun.lwuit.Label;
import com.sun.lwuit.TextField;
import com.sun.lwuit.animations.CommonTransitions;
import com.sun.lwuit.animations.Transition;
import com.sun.lwuit.events.ActionEvent;
import com.sun.lwuit.events.ActionListener;
import com.sun.lwuit.geom.Dimension;
import com.sun.lwuit.layouts.BorderLayout;
import com.sun.lwuit.layouts.BoxLayout;
import com.sun.lwuit.plaf.Border;

import edu.its.ta.Blueshmuzeec;
import edu.its.ta.control.RMSControl;
import edu.its.ta.data.Userinfo;

/**
 * @author satria
 *
 */
public class UserinfoForm extends Form implements ActionListener {

    private static final Command backCommand = new Command("Back", 1);
    private static final Command saveCommand = new Command("Save", 2);
    
    private TextField txNickname = null, txAge = null;
    private Label labelGenre = null, labelArtist = null, labelYear = null;
    
    private Userinfo userinfo = null;
    
    private RMSControl rmsUserinfoControl = null;
    
    public UserinfoForm(Blueshmuzeec b) {
        super("Blueshmuzeec");
        
        this.addCommand(backCommand);
        this.addCommand(saveCommand);
        this.setCommandListener(this);
        
        this.setBackCommand(backCommand);
        
        BoxLayout boxLayout = new BoxLayout(BoxLayout.Y_AXIS);
        this.setLayout(boxLayout);
        
        txNickname = new TextField();
        txNickname.getStyle().setBorder(Border.createLineBorder(1));
        txNickname.getStyle().setBgTransparency(100);
        
        txAge = new TextField();
        txAge.getStyle().setBorder(Border.createLineBorder(1));
        txAge.setConstraint(TextField.NUMERIC);
        txAge.setInputModeOrder(new String[] {"123"});
        txAge.getStyle().setBgTransparency(100);
        
        Container pairNickname = createPair("  Nick Name:", txNickname, 30);
        int largestW = pairNickname.getComponentAt(0).getPreferredW();
        
        Label textLabel1 = new Label("User Information :");
        this.addComponent(textLabel1);
        
        this.addComponent(pairNickname);
        this.addComponent(createPair("  Age:", txAge, largestW));
        
        Label textLabelSeparator = new Label("");
        this.addComponent(textLabelSeparator);
        
        Label textLabel2 = new Label("Music Preference :");
        this.addComponent(textLabel2);
        
        labelGenre = new Label("Rock");
        this.addComponent(createPair("  Genre:", labelGenre, largestW));
        
        labelArtist = new Label("Dewa19");
        this.addComponent(createPair("  Artist:", labelArtist, largestW));
        
        labelYear = new Label("1990-1999");
        this.addComponent(createPair("  Year:", labelYear, largestW));
        
        userinfo = new Userinfo();
        rmsUserinfoControl = new RMSControl(Userinfo.RS_NAME);
        
        Transition in, out;
        out = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, false, 500);
        in = CommonTransitions.createSlide(CommonTransitions.SLIDE_HORIZONTAL, true, 500);
        this.setTransitionInAnimator(in);
        this.setTransitionOutAnimator(out);
    }
    
    public void actionPerformed(ActionEvent arg0) {
        Command cmd = arg0.getCommand();
        switch (cmd.getId()) {
            case 1:
                Blueshmuzeec.mainMenu.show();
                break;
            case 2:
                String data = "{nickname:"+txNickname.getText()+", age:"+txAge.getText()+", genre:"+userinfo.getPreferenceGenre()+", artist:"+userinfo.getPreferenceArtist()+", year:"+userinfo.getPreferenceYear()+"}";
                rmsUserinfoControl.updateStringData(data, 1);
                Blueshmuzeec.mainMenu.show();
                break;
        }
    }
    
    public void preRender() {
        byte[] tmp = rmsUserinfoControl.readStringData(1);
        String data = "";
        if (tmp != null) {
            data = new String(tmp);
            if (!data.startsWith("{") || !data.endsWith("}")) {
                data = "{nickname:"+txNickname.getText()+", age:"+txAge.getText()+", genre:-, artist:-, year:-}";
                rmsUserinfoControl.updateStringData(data, 1);
            }
        } else {
            data = "{nickname:"+txNickname.getText()+", age:"+txAge.getText()+", genre:-, artist:-, year:-}";
            rmsUserinfoControl.addStringData(data);
        }
        userinfo.parseUserinfoData(data);
        txNickname.setText(userinfo.getNickname());
        txAge.setText(userinfo.getAge());
        labelArtist.setText(userinfo.getPreferenceArtist());
        labelGenre.setText(userinfo.getPreferenceGenre());
        labelYear.setText(userinfo.getPreferenceYear());
    }
    
    protected Container createPair(String label, Component c, int minWidth) {
        Container pair = new Container(new BorderLayout());
        Label l =  new Label(label);
        Dimension d = l.getPreferredSize();
        d.setWidth(Math.max(d.getWidth(), minWidth));
        l.setPreferredSize(d);
        l.getStyle().setBgTransparency(100);
        pair.addComponent(BorderLayout.WEST,l);
        pair.addComponent(BorderLayout.CENTER, c);
        return pair;
    }

    public RMSControl getRmsUserinfoControl() {
        return rmsUserinfoControl;
    }
}
