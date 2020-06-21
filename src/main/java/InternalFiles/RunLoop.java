// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;

import static InternalFiles.DAMForm.adaptiveOpMode;
import static InternalFiles.DAMForm.runningLoop;

public class RunLoop implements Runnable {
    public void run() {
        while (runningLoop)
        {
            adaptiveOpMode.loop();
            DAMForm.i += 1;
        }
    }
}
