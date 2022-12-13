import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Stack;

public class Day13 {
	public static void main(String[] args) {
		new Day13();
	}

	public Day13() {
		ArrayList<String> input = ReadInput.read("res/input13.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<ItemList> packets = parseInput(input);
		int index = 1;
		int res = 0;
		for(int i = 0; i < packets.size(); i+=2) {
			int correctOrder = compareItems(packets.get(i), packets.get(i + 1));
			if(correctOrder == 1)
				res += index;
			index++;
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		input.add("[[2]]");
		input.add("[[6]]");
		ArrayList<ItemList> packets = parseInput(input);
		Collections.sort(packets, new Comparator<ItemList>(){
			public int compare(ItemList p1, ItemList p2){
				  return compareItems(p2, p1);
			}});
		
		int indexDiv1 = -1;
		int indexDiv2 = -1;
		for(int i = 0; i < packets.size(); i++) {
			if(packets.get(i).name.equals("[[2]]"))
				indexDiv1 = i + 1;
			if(packets.get(i).name.equals("[[6]]"))
				indexDiv2 = i + 1;
		}
		System.out.println(indexDiv1 * indexDiv2);
	}

	private int compareItems(Item p1, Item p2) {
		if(p1 instanceof ItemValue && p2 instanceof ItemValue) {
			if(((ItemValue) p1).value < ((ItemValue) p2).value)
				return 1;
			if(((ItemValue) p1).value == ((ItemValue) p2).value)
				return 0;
			return -1;
		}
		else if(p1 instanceof ItemList && p2 instanceof ItemList) {
			for(int i = 0; i < ((ItemList) p1).items.size(); i++) {
				if(i == ((ItemList) p2).items.size())
					return -1;
				int res = compareItems(((ItemList) p1).items.get(i), ((ItemList) p2).items.get(i));
				if(res != 0)
					return res;
			}
			return ((ItemList) p1).items.size() == ((ItemList) p2).items.size() ? 0 : 1;
		}
		else if(p1 instanceof ItemValue) {
			ItemList p1List = new ItemList();
			p1List.items.add(p1);
			return compareItems(p1List, (ItemList) p2);
		}
		else {
			ItemList p2List = new ItemList();
			p2List.items.add(p2);
			return compareItems((ItemList) p1, p2List);
		}
	}

	private boolean compareLists(ItemList l1, ItemList l2) {
		for(int i = 0; i < (l1.items.size() > l2.items.size() ? l2.items.size() : l1.items.size()); i++) {
			ItemValue v1 = (ItemValue) l1.items.get(i);
			ItemValue v2 = (ItemValue) l2.items.get(i);
			if(v1.value > v2.value)
				return false;
		}
		return true;
	}

	private ArrayList<ItemList> parseInput(ArrayList<String> input) {
		ArrayList<ItemList> res = new ArrayList<ItemList>();
		for(String line : input) {
			if(line.equals(""))
				continue;
			Stack<ItemList> stack = new Stack<ItemList>();
			ItemList root = new ItemList(line);
			stack.push(root);
			for(int i = 1; i < line.length() - 1; i++) {
				char c = line.charAt(i);
				if(c == '[') {
					ItemList il = new ItemList();
					if(stack.size() > 0)
						stack.peek().items.add(il);
					stack.push(il);
				}
				else if(c == ']') {
					stack.pop();
				}
				else if(c != ',') {
					int numberEndIndex = findEndIndex(line, i);
					int number = Integer.parseInt(line.substring(i, numberEndIndex + 1));
					stack.peek().items.add(new ItemValue(number));
					i = numberEndIndex;
				}
			}
			res.add(root);
		}
		return res;
	}

	private int findEndIndex(String line, int currentIndex) {
		for(int i = currentIndex + 1; i < line.length(); i++) {
			char c = line.charAt(i);
			if(c == ',' || c == ']')
				return i - 1;
		}
		return -1;
	}

	private class Item {
		
	}

	private class ItemValue extends Item {
		public int value;
		public ItemValue(int value) {
			this.value = value;
		}
	}

	private class ItemList extends Item {
		public ArrayList<Item> items;
		public String name;
		public ItemList() {
			items = new ArrayList<Item>();
		}
		public ItemList(String name) {
			items = new ArrayList<Item>();
			this.name = name;
		}
	}

}
