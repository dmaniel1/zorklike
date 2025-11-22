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
		//init dictionary
		dictionary = new Dictionary();
		//game running
		while (run) {
			//input
			//action
			String action = null;
			//object (will not be room; if action is go or forward or any movement verb, it will use target not object)
			String object = null;
			//target (usually not items)
			String target = null;
			System.out.print("I would like to ");
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
					boolean checkRooms = dictionary.searchRooms(token.toLowerCase());
					boolean checkItems = dictionary.searchItems(token.toLowerCase());
					if (checkRooms) {
						target=token;
						break;
					}
					else if (checkItems) {
						object=token;
						break;
					}
					else if (token.equals("around")) {
						target="around";
						break;
					}
					else if (token.equals("foreward") || token.equals("front")) {
						target="foreward";
						break;
					}
					else if (token.equals("backwards") || token.equals("back")) {
						target="backwards";
						break;
					}
					else if (token.equals("left")) {
						target="left";
						break;
					}
					else if (token.equals("inventory")) {
						target="inventory";
						break;
					}
					else {
						break;
					}
				}
			}
			//response
			System.out.println(action);
			System.out.println(target);
			if (action.equals("inventory") || (action.equals("open") && target.equals("inventory"))) {
				if (inventory.size()==0) {
					System.out.println("Peeking into your backpack, you find nothing.");
				}
				else {
					for (String item : inventory) {
						String[] check = item.split("");
						if (check[0].toLowerCase().equals("a") || check[0].toLowerCase().equals("e") || check[0].toLowerCase().equals("i") || check[0].toLowerCase().equals("o") || check[0].toLowerCase().equals("u")) {
							System.out.println("An " + item);
						}
						else {
							System.out.println("A " + item);
						}
					}
				}
			}
		}
	}
}
