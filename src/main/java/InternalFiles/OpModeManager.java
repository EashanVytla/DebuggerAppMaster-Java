// Eashan Vytla
// 3/29/2020
// Purpose: This program allows the user to program a virtual robot to debug their code before deploying

package InternalFiles;

import YourCode.MyOpModeManager;

import java.util.*;

public class OpModeManager extends MyOpModeManager {
    public Map<Integer, Class> map = new HashMap<Integer, Class>();

    private Class[] opModeClasses = new Class[opModeNames.length];
    int j = 0;

    public void create() throws ClassNotFoundException {
        for (String opModeName : opModeNames) {
            opModeClasses[j] = Class.forName("YourCode." + opModeName);
            j += 1;
        }
    }

    public void register()
    {
        for (int i = 0; i < opModeClasses.length; i++)
        {
            map.put(i, opModeClasses[i]);
        }
    }
}
