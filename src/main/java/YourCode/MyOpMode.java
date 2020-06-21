package YourCode;

import InternalFiles.ElapsedTime;
import InternalFiles.OpMode;

public class MyOpMode extends OpMode {
    //IMPORTANT:
    //If you wish to ever make another OpMode that you want to run then put it into the MyOpModeManager which is located in the "YourCode" folder

    public void init()
    {

    }

    public void loop()
    {
        Robot.setPower(1.0, 0.0, 0.0);
    }
}
