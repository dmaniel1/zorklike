package zorklike;

import zorklike.Zorklike;
import java.util.Iterator;


public class Item {
    private Zorklike.Type type;
    private String name;
    private String desc;
    private boolean obtain;
    private int bdmg;
    private int dura;
    private String file;
    public Item(Zorklike.Type ty, String nm, String dc, boolean obt, int dg, int db, String fl) {
        type=ty;
        name=nm;
        desc=dc;
        obtain=obt;
        if (ty == Zorklike.Type.KEY) {
            bdmg=0;
            dura=0;
            file=null;
        }
        else if (ty == Zorklike.Type.CD) {
            bdmg=0;
            dura=0;
            file=fl;
        }
        else if (ty == Zorklike.Type.WEAPON) {
            bdmg=dg;
            dura=db;
            file=null;
        }
    }
    public Item() {
        name=null;
        desc=null;
        bdmg=0;
        file=null;
    }
    public Zorklike.Type getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return desc;
    }
    public boolean isObtainable() {
        return obtain;
    }
    public int getDamage() {
        return bdmg;
    }
    public int getDurability() {
        return dura;
    }
    public String getFile() {
        return file;
    }
    public void changeDescription(String newdesc) {
        desc=newdesc;
    }
    public void changeObtainability(boolean obt) {
        obtain=obt;
    }
    public void changeDamage(int dmg) {
        bdmg=dmg;
    }
    public void damageWeapon(int dmg) {
        dura-=dmg;
        Iterator<Item> iterate = Zorklike.items.iterator();
        while (iterate.hasNext()) {
            Item currentItem = iterate.next();
            if (currentItem.getName().equals(name)) {
                iterate.remove();
                break;
            }
        }
    }
    public void fixWeapon(int heal) {
        dura+=heal;
    }
}