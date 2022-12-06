import java.util.ArrayList;
import java.util.Stack;

public class Day5 {
	public static void main(String[] args) {
		new Day5();
	}

	public Day5() {
		ArrayList<String> input = ReadInput.read("res/input5.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Stack<Character>> stacks = initStacks();
		for(String instruction : input) {
			String[] splitRow = instruction.split(" ");
			int amount = Integer.parseInt(splitRow[1]);
			int from = Integer.parseInt(splitRow[3]) - 1;
			int to = Integer.parseInt(splitRow[5]) - 1;
			for(int i = 0; i < amount; i++) {
				stacks.get(to).push(stacks.get(from).pop());
			}
		}
		StringBuilder res = new StringBuilder();
		for(Stack<Character> s : stacks) {
			res.append(s.peek());
		}
		System.out.println(res.toString());
	}

	private void partTwo(ArrayList<String> input) {
		ArrayList<Stack<Character>> stacks = initStacks();
		for(String instruction : input) {
			String[] splitRow = instruction.split(" ");
			int amount = Integer.parseInt(splitRow[1]);
			int from = Integer.parseInt(splitRow[3]) - 1;
			int to = Integer.parseInt(splitRow[5]) - 1;
			Stack<Character> tmpStack = new Stack<Character>();
			for(int i = 0; i < amount; i++) {
				tmpStack.push(stacks.get(from).pop());
			}
			for(int i = 0; i < amount; i++) {
				stacks.get(to).push(tmpStack.pop());
			}
		}
		StringBuilder res = new StringBuilder();
		for(Stack<Character> s : stacks) {
			res.append(s.peek());
		}
		System.out.println(res.toString());
	}

	private ArrayList<Stack<Character>> initStacks() {
		String input = "QWPSZRHD VBRWQHF CVSH HFG PGJBZ QTJHWFL ZTWDLVJN DTZCJGHF WPVMBH";
		ArrayList<Stack<Character>> stacks = new ArrayList<Stack<Character>>();
		for(String s : input.split(" ")) {
			stacks.add(new Stack<Character>());
			for(char c : s.toCharArray()) {
				stacks.get(stacks.size() - 1).push(c);
			}
		}
		return stacks;
	}

}
