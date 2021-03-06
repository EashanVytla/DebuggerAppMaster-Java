package YourCode;

import InternalFiles.OpMode;

public class EXAMPLE_PID extends OpMode {
    double kp = 0.04;
    double kd = 0.00;
    double power = 0.0;
    double odoleft = 0.0;
    double odoright = 0.0;
    double preverror = 0.0;

    public void init()
    {

    }

    public void loop()
    {
        odoleft = Robot.getLeftOdo();
        odoright = Robot.getRightOdo();

        double error = 72 - ((odoright + odoleft) / 2);
        preverror = error;

        power = (kp * error) + (kd * (error - preverror));

        Robot.setPower(0.0, power, 0.0);
        telemetry.addData("left: ", String.valueOf(odoleft));
        telemetry.addData("right: ", String.valueOf(odoright));
    }
}
