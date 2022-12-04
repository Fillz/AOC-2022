import java.util.ArrayList;
import java.util.HashMap;

public class Day3 {
	public static void main(String[] args) {
		new Day3();
	}

	public Day3() {
		ArrayList<String> input = ReadInput.read("res/input3.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Character> commonitems = new ArrayList<Character>();
		for(String line : input) {
			HashMap<Character, Boolean> alreadyChecked = new HashMap<Character, Boolean>();
			String c1 = line.substring(0,  line.length()/2);
			String c2 = line.substring(line.length()/2, line.length());
			for(char c : c1.toCharArray()) {
				if(c2.contains("" + c) && alreadyChecked.get(c) == null) {
					commonitems.add(c);
					alreadyChecked.put(c, true);
				}
			}
		}
		int res = 0;
		for(char item : commonitems) {
			res += getItemPriority(item);
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		ArrayList<Character> commonitems = new ArrayList<Character>();
		for(int i = 0; i < input.size(); i+=3) {
			String l1 = input.get(i);
			String l2 = input.get(i+1);
			String l3 = input.get(i+2);
			for(char item : l1.toCharArray()) {
				if(l2.contains("" + item) && l3.contains("" + item)) {
					commonitems.add(item);
					break;
				}

			}
		}
		int res = 0;
		for(char item : commonitems) {
			res += getItemPriority(item);
		}
		System.out.println(res);
	}

	private int getItemPriority(char item) {
		if(("" + item).toUpperCase().equals("" + item))
			return (int) item - 38;
		else
			return (int) item - 96;
	}

}
