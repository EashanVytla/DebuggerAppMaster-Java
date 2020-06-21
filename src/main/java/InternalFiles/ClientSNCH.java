// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying
package InternalFiles;

import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.Scanner;

class ClientSNCH
{
    public String recieved_message;
    public Telemetry t;

    public String[] odos = new String[4];

    public double left;
    public double right;
    public double strafe;
    public double gyro;
    public int counter;
    Vector3 pose;

    SocketAddress remoteEP;
    Socket sender;

    DataInputStream din;
    DataOutputStream dos;

    // Data buffer for incoming data.
    private byte[] bytes = new byte[1024];
    BufferedReader reader;
    boolean first = true;

    public ClientSNCH(Telemetry telemetry)
    {
        t = telemetry;
        left = 0;
        right = 0;
        strafe = 0;
        pose = new Vector3(0, 0);
    }

    String ipstring;

    public void setupIPMac(){
        try {
            reader = new BufferedReader(new FileReader(System.getProperty("user.home") + "/Library/Application Support/Robot Studio/Robot Studio/RobotStudio_IP_Config.txt"));
            ipstring = reader.readLine();
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setupIPWindows(){
        try {
            ipstring = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void StartClient(String message)
    {
        // Data buffer for incoming data.
        byte[] bytes = new byte[1024];

        // Connect to a remote device.
        try
        {
            // Establish the remote endpoint for the socket.
            // I used port 8719 on the local computer.
            /*do{
                InetAddress address = InetAddress.getLocalHost();

                if(!address.isLoopbackAddress()){
                    remoteEP = new InetSocketAddress(InetAddress.getByName(InetAddress.getLocalHost().getHostName()).getHostAddress(), 8719);
                    break;
                }
            }while(remoteEP == null);*/

            if(first){
                remoteEP = new InetSocketAddress(InetAddress.getByName(ipstring).getHostAddress(), 8719);
                sender = new Socket(InetAddress.getByName(ipstring).getHostAddress(), 8719);
                sender.setKeepAlive(true);
            }

            first = false;

            din = new DataInputStream(new BufferedInputStream(sender.getInputStream()));
            dos = new DataOutputStream(sender.getOutputStream());

            // Connect the socket to the remote endpoint. Catch any errors.
            try
            {
                if(!sender.isConnected()){
                    sender.connect(remoteEP);
                }

                // Encode the data string into a byte array.
                byte[] msg = message.getBytes();

                if(DAMForm.stopper){
                    message = "stop,";
                    msg = message.getBytes();
                }

                // Send the data through the socket.
                dos.write(msg);

                if(!DAMForm.stopper){
                    // Receive the response from the remote device.
                    din.read(bytes);
                    recieved_message = new String(bytes);
                    parse(recieved_message);
                }
            }
            catch (IllegalArgumentException ane)
            {
                JOptionPane.showMessageDialog(null, "ArgumentNullException : " + ane.toString());
            }
            catch (SocketException se)
            {
                JOptionPane.showMessageDialog(null,"SocketException : " + se.toString());
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Unexpected exception : " + e.toString());
            }

        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Error: " + e.toString());
        }
    }

    public void stop(){
        try {
            sender.close();
            din.close();
            dos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String recieved)
    {
        odos = recieved.split(",");

        if (odos[0].equals("O"))
        {
            if (odos[1].equals("G"))
            {
                if(odos[2].equals("P"))
                {
                    left = Double.parseDouble(odos[3]);
                    right = Double.parseDouble(odos[4]);
                    strafe = Double.parseDouble(odos[5]);
                    pose = new Vector3(Double.parseDouble(odos[6]), Double.parseDouble(odos[7]));
                    gyro = Double.parseDouble(odos[8]);
                }
                else
                {
                    left = Double.parseDouble(odos[2]);
                    right = Double.parseDouble(odos[3]);
                    strafe = Double.parseDouble(odos[4]);
                    gyro = Double.parseDouble(odos[5]);
                }
            }
            else if(odos[1].equals("P"))
            {
                left = Double.parseDouble(odos[2]);
                right = Double.parseDouble(odos[3]);
                strafe = Double.parseDouble(odos[4]);
                pose = new Vector3(Double.parseDouble(odos[5]), Double.parseDouble(odos[6]));
            }
            else
            {
                left = Double.parseDouble(odos[1]);
                right = Double.parseDouble(odos[2]);
                strafe = Double.parseDouble(odos[3]);
            }
        }
        else if (odos[0].equals("G"))
        {
            gyro = Double.parseDouble(odos[1]);
        }else if(odos[0].equals("P"))
        {
            if(odos[1].equals("G"))
            {
                pose = new Vector3(Double.parseDouble(odos[2]), Double.parseDouble(odos[3]));
                gyro = Double.parseDouble(odos[4]);
            }
            else
            {
                pose = new Vector3(Double.parseDouble(odos[1]), Double.parseDouble(odos[2]));
            }
        }
        else if (recieved.equals("stop"))
        {
            stop();
            System.exit(0);
        }
    }
}
