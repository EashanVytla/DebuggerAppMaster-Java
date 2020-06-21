// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class DAMForm {
    private JButton StrtBtn;
    private JPanel mainpanel;
    private JComboBox OpModeDrp;
    private JList TelemetryLst;
    private Timer timer1;
    public static int i;
    public static boolean runningLoop = true;
    public static boolean stopper = false;
    private static String fullTelemetry;

    private static JFrame frame = new JFrame(("DAMForm"));

    OpModeManager mngr = new OpModeManager();

    public static OpMode adaptiveOpMode = null;

    enum State{
        STATE_INIT,
        STATE_START,
        STATE_STOP
    }

    public static State mAppState = State.STATE_INIT;

    public DAMForm() {
        StrtBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(mAppState == State.STATE_INIT){
                    if(OpModeDrp.getSelectedIndex() != -1)
                    {
                        adaptiveOpMode = (OpMode)CreateInstance((mngr.map.get(OpModeDrp.getSelectedIndex())));
                        if(System.getProperty("os.name").startsWith("Windows")){
                            adaptiveOpMode.Robot.msngr.setupIPWindows();
                        }else {
                            adaptiveOpMode.Robot.msngr.setupIPMac();
                        }
                        adaptiveOpMode.init();
                        StrtBtn.setText("Start");
                        mAppState = State.STATE_START;
                        adaptiveOpMode.Robot.msngr.StartClient("v1.1,");
                        adaptiveOpMode.Robot.msngr.StartClient("start,");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(null,"Please select an OpMode to run");
                    }
                }else if(mAppState == State.STATE_START){
                    StrtBtn.setText("Stop");
                    runningLoop = true;
                    adaptiveOpMode.onStart();
                    new Thread(new RunLoop()).start();
                    new Thread(new TelemetryRunnable(TelemetryLst)).start();
                    mAppState = State.STATE_STOP;
                }else if(mAppState == State.STATE_STOP){
                    try {
                        stopper = true;
                        System.out.println("Safely Stopped");
                        Thread.sleep(250);
                        runningLoop = false;
                        adaptiveOpMode.Robot.msngr.stop();
                        System.exit(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        frame.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent windowEvent) {
                try {
                    mngr.create();
                } catch (ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
                mngr.register();
                for ( Map.Entry<Integer, Class> kvp : mngr.map.entrySet() )
                {
                    OpModeDrp.addItem(kvp.getKey() + " - " + kvp.getValue());
                }
            }

            @Override
            public void windowClosing(WindowEvent windowEvent) {
                try {
                    stopper = true;
                    System.out.println("Safely Stopped");
                    Thread.sleep(250);
                    runningLoop = false;
                    adaptiveOpMode.Robot.msngr.stop();
                    System.exit(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent windowEvent) {

            }

            @Override
            public void windowIconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeiconified(WindowEvent windowEvent) {

            }

            @Override
            public void windowActivated(WindowEvent windowEvent) {

            }

            @Override
            public void windowDeactivated(WindowEvent windowEvent) {

            }
        });
    }

    public static Object CreateInstance(Class type) {
        Constructor constr = null;
        System.out.println(type);
        try {
            constr = type.getConstructor();
            if ( constr == null){
                System.out.println("Null constructor");
            }
            Object instance = constr.newInstance();
            if(instance == null){
                System.out.println("Null Instance");
            }
            return instance;
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            JOptionPane.showMessageDialog(null, "OpMode Could Not Be Found: Please make sure that you put the right OpMode name into MyOpModeManager");
            return null;
        }
    }


    public static void main(String[] args) {
        frame.setContentPane(new DAMForm().mainpanel);
        frame.setMinimumSize(new Dimension(275, 300));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
