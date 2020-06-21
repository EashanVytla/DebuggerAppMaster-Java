// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;

import static InternalFiles.DAMForm.adaptiveOpMode;
import static InternalFiles.DAMForm.runningLoop;

public class TelemetryRunnable implements Runnable{
    JList TList;

    public TelemetryRunnable(JList list) {
        TList = list;
    }

    public void run() {
        while(runningLoop){
            adaptiveOpMode.telemetry.update(TList);
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
