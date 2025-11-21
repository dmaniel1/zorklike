package zorklike;

import zorklike.Room;

import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zorklike {
	static Scanner scan;
	static enum Type{
        KEY,
        CD,
        WEAPON
    };
	static List<String> inventory;
	static List<Room> rooms;
	static List<Item> items;
	public static void main(String[] args) {
		boolean run = true;
		inventory = new ArrayList<String>();
		rooms = new ArrayList<Room>();
		items = new ArrayList<Item>();
		scan = new Scanner(System.in);
		String[] roomRq = {"axe","key"};
		String[] roomCn = {"testroom2"};
		Object[][] roomIt = {
			{Type.KEY,"key","A simple key.",0,0,null},
			{Type.WEAPON,"axe","A fireaxe.",10,100,null}
		};
		rooms.add(new Room("testroom","test room","testing the room class",roomRq,roomCn,roomIt,false));
		String[] testRq = null;
		String[] testCn = {"testroom"};
		Object[][] testIt = null;
		rooms.add(new Room("testroom2","testing room travel","testing the ability to travel between rooms",testRq,testCn,testIt,true));

		roomIt = null;
		Room curRoom = rooms.get(0);
		for (int i=0;i<rooms.size();i++) {
			if (rooms.get(i).getItemL()!=null) {
				Type type;
				String name;
				String description;
				int damage;
				int durability;
				String file;
				Object[][] itemstocreate = rooms.get(i).getItemL();
				for (int x=0;x<itemstocreate.length;x++) {
					type=(Type)itemstocreate[x][0];
					name=(String)itemstocreate[x][1];
					description=(String)itemstocreate[x][2];
					damage=(int)itemstocreate[x][3];
					durability=(int)itemstocreate[x][4];
					file=(String)itemstocreate[x][5];
					items.add(new Item(type,name,description,damage,durability,file));
				}
			}
		}
		while (run) {
			System.out.print("type command \n> ");
			String input = scan.nextLine();
			if (input.equals("t")) {
				System.out.println("correct");
				boolean tryuse = curRoom.useItem("key");
				boolean tryuse1 = curRoom.useItem("axe");
				if (tryuse) {
					System.out.println("works");
					curRoom.openClose();
					System.out.println(curRoom.isOpen());
				}
			}
			else if (input.equals("inv")) {
				if (items.size() == 0) {
					System.out.println("Peeking into your backpack, you find nothing.");
				}
				else {
					for (Item item : items) {
						System.out.println(item.getName());
					}
				}
			}
		}
	}
}
