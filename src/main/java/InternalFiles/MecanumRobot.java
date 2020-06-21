package InternalFiles;

public class MecanumRobot
{
    public boolean odoquery;
    public boolean gyroquery;
    public boolean posequery;
    private boolean centric;

    public static boolean leftreciever;
    public static boolean rightreciever;
    public static boolean strafereciever;
    public static boolean gyroreciever;
    public static boolean posereciever;
    private boolean firststart = true;

    public ClientSNCH msngr;

    public MecanumRobot(Telemetry telemetry)
    {
        msngr = new ClientSNCH(telemetry);

        odoquery = false;
        gyroquery = false;
        centric = false;

        leftreciever = false;
        rightreciever = false;
        strafereciever = false;
        gyroreciever = false;
    }



    public double RangeClip(double value, double min, double max)
    {
        if(value >= max)
        {
            return max;
        }else if(value <= min)
        {
            return min;
        }
        else
        {
            return value;
        }
    }

    private void sendPower(double ul, double bl, double ur, double br)
    {
        /////Sending the packages
        if (odoquery && !DAMForm.stopper)
        {
            msngr.StartClient("O,");
            odoquery = false;
        }else if (gyroquery && !DAMForm.stopper)
        {
            msngr.StartClient("G,");
            gyroquery = false;
        }else if(posequery && !DAMForm.stopper)
        {
            msngr.StartClient("P,");
            posequery = false;
        }
        else
        {
            msngr.StartClient("rp" + RangeClip(ul, -1.0f, 1.0f) + "|" + RangeClip(ur, -1.0f, 1.0f) + "|" + RangeClip(bl, -1.0f, 1.0f) + "|" + RangeClip(br, -1.0f, 1.0f) + ",");
        }
    }

    public void setPower(double UpLeft, double BackLeft, double UpRight, double BackRight)
    {
        centric = false;
        sendPower(UpLeft, BackLeft, UpRight, BackRight);
    }

    public void setPower(double x, double y, double rot)
    {
        double FrontLeftVal = y - x + rot;
        double FrontRightVal = y + x - rot;
        double BackLeftVal = y + x + rot;
        double BackRightVal = y - x - rot;

        setPower(FrontLeftVal, BackLeftVal, FrontRightVal, BackRightVal);
    }

    private boolean first = true;
    private boolean firstg = true;
    private boolean firstp = true;

    public double getLeftOdo()
    {
        if (first)
        {
            odoquery = true;
            leftreciever = true;
            first = false;
        }
        return msngr.left * 2.18181818182;
    }

    public double getRightOdo()
    {
        if (first)
        {
            odoquery = true;
            first = false;
            rightreciever = true;
        }
        return msngr.right * 2.18181818182;
    }

    public double getStrafeOdo()
    {
        if (first)
        {
            odoquery = true;
            strafereciever = true;
            first = false;
        }
        return msngr.strafe * 2.18181818182;
    }

    public Vector3 getPose()
    {
        if (firstp)
        {
            posequery = true;
            posereciever = true;
            firstp = false;
        }
        return msngr.pose;
    }

    public double getHeading()
    {
        if (firstg)
        {
            gyroquery = true;
            gyroreciever = true;
            firstg = false;
        }
        return msngr.gyro;
    }
}