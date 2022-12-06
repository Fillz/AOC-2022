import java.util.ArrayList;
import java.util.LinkedList;

public class Day6 {
	public static void main(String[] args) {
		new Day6();
	}

	public Day6() {
		ArrayList<String> input = ReadInput.read("res/input6.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		System.out.println(getMarker(input.get(0), 4));
	}

	private void partTwo(ArrayList<String> input) {
		System.out.println(getMarker(input.get(0), 14));
	}

	private int getMarker(String input, int distinctCharacters) {
		LinkedList<Character> queue = new LinkedList<Character>();
		for(int i = 0; i < input.length(); i++) {
			char c = input.charAt(i);
			queue.add(c);
			if(queue.size() < distinctCharacters)
				continue;
			if(queueHasUniqueValues(queue)) {
				return i + 1;
			}
			queue.poll();
		}
		return -1;
	}

	private boolean queueHasUniqueValues(LinkedList<Character> queue) {
		char[] chars = new char[queue.size()];
		for(int i = 0; i < chars.length; i++)
			chars[i] = queue.get(i);
		
		for(int i = 0; i < chars.length; i++) {
			for(int j = i + 1; j < chars.length; j++) {
				if(chars[i] == chars[j])
					return false;
			}
		}
		return true;
	}

}
