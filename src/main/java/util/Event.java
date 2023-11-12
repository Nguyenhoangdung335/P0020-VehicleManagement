package util;

import interfaces.IActionListener;
import java.util.LinkedList;
import java.util.List;

/*-----------------------------------------------------------------------------------
--------------The Event Class implemented the Observer Behavior Pattern--------------
-----------------------------------------------------------------------------------*/

public class Event {
    private final List<IActionListener> actionListeners;
    
    public Event () {
        actionListeners = new LinkedList();
    }
    
    public List getListenersList () {
        return actionListeners;
    }

    public void addListener (IActionListener listener) {
        this.actionListeners.add(listener);
    }
    
    public void removeListener (IActionListener listener) {
        this.actionListeners.remove(listener);
    }
    
    public void invoke (Object o) {
        for (var listener: actionListeners) {
            listener.update(o);
        }
    }
}