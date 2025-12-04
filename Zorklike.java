package zorklike;

//import statements
import zorklike.Room;
import zorklike.Dictionary;
import zorklike.Connection;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Scanner;
import java.util.HashMap;

public class Zorklike {
	//init variables
	static Scanner scan;
	static Dictionary dictionary;
	static enum Type {
        KEY,
        CD,
        WEAPON
    };
	public static List<Item> inventory;
	public static List<Room> rooms;
	@FunctionalInterface
	public static interface Command {
		int command(String action, String object);
	}
	public static HashMap<String, Command> commandHashMap;
	public static void main(String[] args) {
		//declare init variables
		boolean run = true;
		inventory = new ArrayList<Item>();
		rooms = new ArrayList<Room>();
		commandHashMap = new HashMap<String, Command>();
		scan = new Scanner(System.in);
		//create rooms

		//testroom (requires key and axe from all connections)
		rooms.add(new Room("testroom","test room","its testroom oh yeah",new Connection("front","testroom2",true),new Connection("right","testroom3",true)));
		//testroom items
		rooms.get(0).addItems(new Item(Type.KEY,"key","A simple key.",true,0,0,null),new Item(Type.WEAPON,"axe","A fireaxe.",true,10,100,null));
		// in front of you, testroom2, to your right, testroom3

		//testroom2
		rooms.add(new Room("testroom2","testing room travel","yuhhhh",new Connection("back","testroom",true,"key","axe")));
		//testroom2 items
		rooms.get(1).addItems();
		// behind you, testroom

		//testroom3
		rooms.add(new Room("testroom3","testing room travel","yuh its testroom3", new Connection("right","testroom",true)));
		rooms.get(2).addItems(new Item(Type.CD,"flash drive","A small red 16gb flash drive",true,0,0,"123.cmd"));

		//current room variable (for travel)
		Room curRoom = rooms.get(0);

		//init dictionary
		dictionary = new Dictionary();
		List<String> movementL = Arrays.asList(Dictionary.directions);
		
		//command and populate hashmap
		//inventory
		Command checkInventory = (String target, String object) -> {
			if (inventory.size()==0) {
				System.out.println("Peeking into your backpack, you find nothing.");
				return 0;
			}
			else {
				System.out.println("You have a total of " + inventory.size() + " items in your backpack.");
				int current = 1;
				for (Item item : inventory) {
					String[] check = item.getName().split("");
					if (check[0].toLowerCase().equals("a") || check[0].toLowerCase().equals("e") || check[0].toLowerCase().equals("i") || check[0].toLowerCase().equals("o") || check[0].toLowerCase().equals("u")) {
						System.out.println(current + ": An " + item.getName());
					}
					else {
						System.out.println(current + ": A " + item.getName());
					}
					current++;
				}
				return 0;
			}
		};
		commandHashMap.put("inventory",checkInventory);
		
		//check items
		Command checkItemList = (String target, String object) -> {
			for (String itemName : dictionary.getItemNames()) {
				System.out.println(itemName);
			}
			return 0;
		};
		commandHashMap.put("list",checkItemList);

		//look around
		Command lookAround = (String object, String target) -> {
			String[] name = curRoom.getName().split("");
			name[0]=name[0].toUpperCase();
			String nameCap = String.join("",name);
			System.out.println("You look around the " + nameCap + " and see:");
			Item[] curItems = curRoom.getItemL();
			if (curItems.length==0) {
				System.out.println("Nothing...");
				return 0;
			}
			else {
				for (Item item : curItems) {
					String[] check = item.getName().split("");
					if (check[0].equalsIgnoreCase("a") || check[0].equalsIgnoreCase("e") || check[0].equalsIgnoreCase("i") || check[0].equalsIgnoreCase("o") || check[0].equalsIgnoreCase("u"))  {
						System.out.println("An " + item.getName());
					}
					else {
						System.out.println("A " + item.getName());
					}
				}
				return 0;
			}
		};
		commandHashMap.put("around",lookAround);
		
		//game running
		while (run) {
			//input
			// action
			String action = null;
			// target (usually not items)
			String target = null;
			// object (will not be room; if action is go or forward or any movement verb, it will use target not object)
			String object = null;
			System.out.print("> ");
			String input = scan.nextLine();
			input = input.toLowerCase();
			// parser logic
			String[] stringify = input.split(" ");
			ArrayList<String> tokenized = new ArrayList<String>(Arrays.asList(stringify));
			// delete action and all words before action after setting action
			int index = -1;
			for (int i=0;i<tokenized.size();i++) {
				String token = tokenized.get(i);
				for (String verb : Dictionary.actions) {
					if (token.equalsIgnoreCase(verb)) {
						action = token;
						index = i;
						break;
					}
				}
				if (index!=-1) break;
			}
			if (action!=null) {
				// remove unneccessary words
				for (int i=0;i<tokenized.size();i++) {
					String token = tokenized.get(i);
					for (String item : Dictionary.useless) {
						if (token.equals(item)) {
							tokenized.remove(i);
						}
					}
				}

				if (index==-1) {
					System.out.println("PARSING ERROR: ERROR CODE: 0");
				}
				else {
					tokenized.subList(0,index+1).clear();
				}
				boolean objAndTarg = false;
				for (String item : tokenized) {
					for (String compare : Dictionary.splitters) {
						if (item.equals(compare)) {
							objAndTarg = true;
						}
					}
				}
				if (objAndTarg) {
					ArrayList<String> targetList = new ArrayList<String>();
					ArrayList<String> objectList= new ArrayList<String>();
					boolean targl = true;
					if (targl) {
						for (int i=0;i<tokenized.size();i++) {
							String item = tokenized.get(i);
							for (String compare : Dictionary.splitters) {
								if (!item.equals(compare)) {
									targetList.add(item);
									tokenized.remove(i);
								}
								else {
									targl = false;
									tokenized.remove(i);
								}
							}
						}
					}
					else {
						for (int i=0;i<tokenized.size();i++) {
							String item = tokenized.get(i);
							objectList.add(item);
							tokenized.remove(i);
						}
					}
					String targetListString = String.join(" ",targetList);
					String objectListString = String.join(" ",objectList);
					target = targetListString;
					object = objectListString;
				}
				else {
					ArrayList<String> tokenList = new ArrayList<String>();
					for (String token : tokenized) {
						tokenList.add(token);
					}
					String token = String.join(" ",tokenList);
					System.out.println(token);
					boolean checkRooms = dictionary.searchRooms(token.toLowerCase());
					boolean checkItems = dictionary.searchItems(token.toLowerCase());
					System.out.println("checkRooms: " + checkRooms);
					System.out.println("checkItems: " + checkItems);

					if (checkRooms) {
						target = token;
					}
					else if (checkItems) {
						object = token;
					}
					else if (token.equals("around")) {
						action = "around";
					}
					else if (token.equals("foreward") || token.equals("front") || token.equals("forewards")) {
						action = "forewards";
					}
					else if (token.equals("backward") || token.equals("back") || token.equals("backwards")) {
						action = "backwards";
					}
					else if (token.equals("left")) {
						action = "left";
					}
					else if (token.equals("right")) {
						action = "right";
					}
					else if (token.equals("inventory")) {
						action = "inventory";
					}
					else if (token.equals("backpack")) {
						action = "inventory";
					}
				}
				if (action.equals("foreward") || action.equals("front") || action.equals("forewards")) {
					action = "forewards";
				}
				else if (action.equals("backward") || action.equals("back") || action.equals("backwards")) {
					action = "backwards";
				}
				else if (action.equals("backpack")) {
					action = "inventory";
				}
				//response
				// debug
				System.out.println("action: " + action);
				System.out.println("target: " + target);
				System.out.println("object: " + object);
				commandHashMap.get(action).command(target,object);
			}
			else {
				System.out.println("Sorry, not quite sure what \"" + input + "\" means. Try again?");
			}
		}
	}
}
