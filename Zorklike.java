package zorklike;

//import statements
import zorklike.Room;
import zorklike.Dictionary;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Zorklike {
	//init variables
	static Scanner scan;
	static Dictionary dictionary;
	static enum Type{
        KEY,
        CD,
        WEAPON
    };
	public static List<String> inventory;
	public static List<Room> rooms;
	public static List<Item> items;
	public static void main(String[] args) {
		//declare init variables
		boolean run = true;
		inventory = new ArrayList<String>();
		rooms = new ArrayList<Room>();
		items = new ArrayList<Item>();
		scan = new Scanner(System.in);
		dictionary = new Dictionary();
		//create rooms
		//testroom
		String[] roomRq = {"axe","key"};
		String[][] roomCn = {
			{"front","testroom2"},
			{"right","testroom3"}
		};
		Object[][] roomIt = {
			{Type.KEY,"key","A simple key.",true,0,0,null},
			{Type.WEAPON,"axe","A fireaxe.",true,10,100,null}
		};
		rooms.add(new Room("testroom","test room","in front of you, testroom2, to your right, testroom3",roomRq,roomCn,roomIt,false));
		//testroom2
		String[] testRq = null;
		String[][] testCn = {
			{"back","testroom"}
		};
		Object[][] testIt = null;
		rooms.add(new Room("testroom2","testing room travel","behind you, testroom",testRq,testCn,testIt,true));
		//testroom3
		String[] tRq = null;
		String[][] tCn = {
			{"left","testroom"}
		};
		Object[][] tIt = {
			{Type.CD,"flash drive","A small red 16gb flash drive",true,0,0,"123.CMD"}
		};
		rooms.add(new Room("testroom3","testing room travel","to your right, testroom",tRq,tCn,tIt,true));
		//current room variable (for travel)
		Room curRoom = rooms.get(0);
		//creating items
		for (int i=0;i<rooms.size();i++) {
			if (rooms.get(i).getItemL()!=null) {
				Type type;
				String name;
				String description;
				boolean obtainable;
				int damage;
				int durability;
				String file;
				Object[][] itemstocreate = rooms.get(i).getItemL();
				for (int x=0;x<itemstocreate.length;x++) {
					type=(Type)itemstocreate[x][0];
					name=(String)itemstocreate[x][1];
					description=(String)itemstocreate[x][2];
					obtainable=(boolean)itemstocreate[x][3];
					damage=(int)itemstocreate[x][4];
					durability=(int)itemstocreate[x][5];
					file=(String)itemstocreate[x][6];
					items.add(new Item(type,name,description,obtainable,damage,durability,file));
				}
			}
			else {
				//System.out.println("not created");
			}
		}
		//game running
		while (run) {
			//input
			//action
			String action = null;
			//object (will not be room; if action is go or forward or any movement verb, it will use target not object)
			String object = null;
			//target (usually not items)
			String target = null;
			System.out.print("type command \n> ");
			String input = scan.nextLine();
			//split input and analyze
			String[] stringify = input.split(" ");
			for (String token : stringify) {
				if (action==null) {
					for (int i=0;i<Dictionary.actions.length;i++) {
						if (token.equals(Dictionary.actions[i])) {
							action = token;
							break;
						}
					}
				}
				else if (action!=null && object==null && target==null) {
					boolean checkRooms = dictionary.searchRooms(token);
					boolean checkItems = dictionary.searchItems(token);
					if (checkRooms) {
						target=token;
					}
					else if (checkItems) {
						object=token;
					}
					else {
						break;
					}
				}
			}
			//response
			if (input.equals("t")) {
				System.out.println("correct");
				boolean tryuse = curRoom.useItem("key");
				boolean tryuse1 = curRoom.useItem("axe");
				System.out.println(curRoom.getName());
				if (tryuse) {
					System.out.println("works");
					curRoom.openClose();
					System.out.println(curRoom.isOpen());
				}
			}
			else if (input.equals("inv")) {
				if (inventory.size() == 0) {
					System.out.println("Peeking into your backpack, you find nothing.");
				}
				else {
					for (String item : inventory) {
						System.out.println("A " + item);
					}
				}
			}
			else if (input.equals("mv")) {
				for (int i=0;i<rooms.size();i++) {
					if (rooms.get(i).getName().equals("testroom2")) {
						curRoom = rooms.get(i);
						System.out.println(curRoom.getName());
						break;
					}
 				}
			}
		}
	}
}
