// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;
import java.awt.*;

public class Telemetry {
    int numItems = 0;
    String[] caption = new String[20];
    String[] message = new String[20];
    int j = 0;
    int c = 0;
    boolean first = false;
    int index = 0;

    public boolean contains(String[] strarr, String inquiry){
        int i = 0;
        for (String item : strarr) {
            if(item == inquiry){
                return true;
            }
            i++;
        }
        return false;
    }

    public void addData(String caption, String message)
    {
        if (DAMForm.i == 0 || !contains(this.caption, caption))
        {
            this.caption[numItems] = caption;
            numItems += 1;
        }

        if (!contains(this.message, message))
        {
            for(int i = 0; i < numItems; i++)
            {
                if(this.caption[i] == caption)
                {
                    index = i;
                }
            }
            this.message[index] = message;
        }
    }

    DefaultListModel demoList = new DefaultListModel();

    public void update(JList list)
    {
        demoList.clear();
        for(int i = 0; i < numItems; i++)
        {
            demoList.addElement(this.caption[i] + ": " + this.message[i]);
            list.setModel(demoList);
        }
    }
}
