package input;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class Input extends JComponent {

    private boolean[] map; // array which persist buttons state (false = released, true = pressed).

    public Input(){
        map = new boolean[256]; // length equals 256 for 128 different keys multiply by 2 state (is pressed/ is released).

        for (int i = 0; i < map.length; i++) {

            final int KEY_CODE = i;

            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, false), i * 2);
            getActionMap().put(i * 2, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    map[KEY_CODE] = true;
                }
            });

            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(i, 0, true), i * 2 + 1);
            getActionMap().put(i * 2 + 1, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    map[KEY_CODE] = false;
                }
            });
        }
    }

    // get copy of model of keyboard state.

    public boolean[] getMap(){
        return map;
    }

    // get state for special key.
    // when map[special_key] return true, it mean special button was pressed,
    // and special Action for this button have to execute.

    public boolean getKey(int KeyCode){
        return map[KeyCode];
    }
}
