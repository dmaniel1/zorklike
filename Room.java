package zorklike;

import zorklike.Item;
import zorklike.Zorklike;
import zorklike.Connection;
import java.util.Iterator;


public class Room {
    private String name;
    private String desc;
    private String extdesc;
    private Connection[] connections;
    // private Object[/* number of items */][/* item metadata */] iteml;
    private Item[] iteml;
    public Room(String nm, String dc, String edc, Connection... connect) {
        name=nm;
        desc=dc;
        extdesc=edc;
        connections=connect;
    }
    public Room() {
        name=null;
        desc=null;
        extdesc=null;
        connections=null;
        iteml=null;
    }
    public void addItems(Item... item) {
        if (item==null || item.length==0) {
            iteml=null;
        }
        else {
            iteml=item;
        }
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
    public Connection[] getConnections() {
        return connections;
    }
    public Item[] getItemL() {
        return iteml;
    }
}
