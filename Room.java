package zorklike;

import zorklike.Item;
import zorklike.Zorklike;
import java.util.Iterator;


public class Room {
    private String name;
    private String desc;
    private String extdesc;
    private String[] require;
    private String[] connections;
    private Object[/* number of items */][/* item metadata */] iteml;
    private boolean open;
    public Room(String nm, String dc, String edc, String[] req, String[] connect, Object[][] itl, boolean op) {
        name=nm;
        desc=dc;
        extdesc=edc;
        require=req;
        connections=connect;
        iteml=itl;
        open=op;
    }
    public Room() {
        name="null";
        desc="N/A";
        extdesc="N/A";
        require=null;
        connections=null;
        iteml=null;
        open=false;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return desc;
    }
    public String getExtDescription() {
        return extdesc;
    }
    public String[] getRequirement() {
        return require;
    }
    public String[] getConnections() {
        return connections;
    }
    public Object[][] getItemL() {
        return iteml;
    }
    public boolean isOpen() {
        return open;
    }
    public boolean useItem(String item) {
        if (require == null) {
            return false;
        }
        for (int i=0; i<require.length; i++) {
            if (require[i].equals("NULL")) {
                return false;
            }
            else if (require[i].equals(item)) {
                require[i] = "NULL";
                Iterator<Item> iterate = Zorklike.items.iterator();
                while (iterate.hasNext()) {
                    Item currentItem = iterate.next();
                    if (currentItem.getName().equals(item)) {
                        iterate.remove();
                        break;
                    }
                }
                return true;
            }
        }
        return false;
    }
    public int openClose() {
        String ref = "NULL";
        for (String req : require) {
            if (!ref.equals(req)) {
                return 0;
            }
        }
        if (open==true) {
            open=false;
            return 1;
        }
        else if (open==false) {
            return 2;
        }
        return -1;
    }
}