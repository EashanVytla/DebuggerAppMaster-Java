// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package YourCode;

import java.lang.reflect.Type;

public abstract class MyOpModeManager {
    //This is where you are going to register your OpMode into the program:
        //Intructions:
        //If you wish to ever make two OpModes that you want to be registered then add an element to the array
        //Type "typeof(YOUR OPMODE NAME)"
        //If you do not do this then it will not show up on the screen
        //Ex: public Type[] opModeNames = { typeof(MyOpMode), typeof(OdometryTester), typeof(MyExampleOpMode)... };
    public String[] opModeNames = { "MyOpMode", "EXAMPLE_PID" };
}
