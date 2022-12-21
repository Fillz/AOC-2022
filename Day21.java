import java.util.ArrayList;
import java.util.HashMap;

public class Day21 {
	public static void main(String[] args) {
		new Day21();
	}

	public Day21() {
		ArrayList<String> input = ReadInput.read("res/input21.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		HashMap<String, Yell> monkeyYells = parseInput(input);
		System.out.println(calculateNumber("root", monkeyYells));
	}

	private void partTwo(ArrayList<String> input) {

	}

	private long calculateNumber(String currentMonkey, HashMap<String, Yell> monkeyYells) {
		Yell y = monkeyYells.get(currentMonkey);
		if(y instanceof YellNumber) {
			YellNumber yn = (YellNumber) y;
			return yn.number;
		}
		else {
			YellOperation yo = (YellOperation) y;
			long leftNumber = calculateNumber(yo.left, monkeyYells);
			long rightNumber = calculateNumber(yo.right, monkeyYells);
			if(yo.operation == '+')
				return leftNumber + rightNumber;
			else if(yo.operation == '-')
				return leftNumber - rightNumber;
			else if(yo.operation == '*')
				return leftNumber * rightNumber;
			else 
				return leftNumber / rightNumber;
		}
	}

	private HashMap<String, Yell> parseInput(ArrayList<String> input) {
		HashMap<String, Yell> map = new HashMap<String, Yell>();
		for(String line : input) {
			String name = line.split(":")[0];
			String s = line.split(": ")[1];
			if(s.contains("+") || s.contains("-") || s.contains("*") || s.contains("/")) {
				String left = s.split(" ")[0];
				char operation = s.split(" ")[1].charAt(0);
				String right = s.split(" ")[2];
				map.put(name, new YellOperation(operation, left, right));
			}
			else {
				map.put(name, new YellNumber(Integer.parseInt(s)));
			}
		}
		return map;
	}

	private interface Yell {}

	private class YellNumber implements Yell {
		public long number;
		public YellNumber(long number) {
			this.number = number;
		}
	}

	private class YellOperation implements Yell {
		private char operation;
		private String left, right;
		public YellOperation(char operation, String left, String right) {
			this.operation = operation;
			this.left = left;
			this.right = right;
		}
	}
}
