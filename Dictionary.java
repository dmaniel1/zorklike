package zorklike;

import zorklike.Zorklike;
import zorklike.Room;
import zorklike.Item;

public class Dictionary {
    private String[] roomNames;
    private String[] itemNames;
    public static String[] actions = {"go","find","search","look","examine","take","grab","in","into","take","get","open","close","drop","foreward","front","right","left","back","backwards"};
    public Dictionary() {
        roomNames = new String[Zorklike.rooms.size()];
        itemNames = new String[Zorklike.items.size()];
        for (int i=0;i<Zorklike.rooms.size();i++) {
            roomNames[i] = Zorklike.rooms.get(i).getName();
        }
        for (int i=0;i<Zorklike.items.size();i++) {
            itemNames[i] = Zorklike.items.get(i).getName();
        }
    }
    public boolean searchRooms(String roomName) {
        for (int i=0;i<roomNames.length;i++) {
            if (roomNames[i].equals(roomName)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
    public boolean searchItems(String itemName) {
        for (int i=0;i<itemNames.length;i++) {
            if (itemNames[i].equals(itemName)) {
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }
}