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
		HashMap<String, Yell> monkeyYells = parseInput(input);
		calculateNumber("root", monkeyYells);
		YellOperation rootYell = (YellOperation) monkeyYells.get("root");

		long soughtValue;
		String startMonkey;
		if(rootYell.humanSide.equals("left")) {
			soughtValue = calculateNumber(rootYell.right, monkeyYells);
			startMonkey = rootYell.left;
		}
		else {
			soughtValue = calculateNumber(rootYell.left, monkeyYells);
			startMonkey = rootYell.right;
		}

		long res = calculateHumanYell(startMonkey, soughtValue, monkeyYells);
		System.out.println(res);

	}

	private long calculateHumanYell(String currentMonkeyName, long currentValue, HashMap<String, Yell> monkeyYells) {
		YellOperation currentMonkey = (YellOperation) monkeyYells.get(currentMonkeyName);
		if(currentMonkey.humanSide.equals("left")) {
			long rightValue = calculateNumber(currentMonkey.right, monkeyYells);
			if(currentMonkey.operation == '+') {
				if(monkeyYells.get(currentMonkey.left) instanceof YellNumber)
					return currentValue - rightValue;
				return calculateHumanYell(currentMonkey.left, currentValue - rightValue, monkeyYells);
			}
			else if(currentMonkey.operation == '-') {
				if(monkeyYells.get(currentMonkey.left) instanceof YellNumber)
					return currentValue + rightValue;
				return calculateHumanYell(currentMonkey.left, currentValue + rightValue, monkeyYells);
			}
			else if(currentMonkey.operation == '*') {
				if(monkeyYells.get(currentMonkey.left) instanceof YellNumber)
					return currentValue / rightValue;
				return calculateHumanYell(currentMonkey.left, currentValue / rightValue, monkeyYells);
			}
			else {
				if(monkeyYells.get(currentMonkey.left) instanceof YellNumber)
					return currentValue * rightValue;
				return calculateHumanYell(currentMonkey.left, currentValue * rightValue, monkeyYells);
			}
		}
		else {
			long leftValue = calculateNumber(currentMonkey.left, monkeyYells);
			if(currentMonkey.operation == '+') {
				if(monkeyYells.get(currentMonkey.right) instanceof YellNumber)
					return currentValue - leftValue;
				return calculateHumanYell(currentMonkey.right, currentValue - leftValue, monkeyYells);
			}
			else if(currentMonkey.operation == '-') {
				if(monkeyYells.get(currentMonkey.right) instanceof YellNumber)
					return currentValue + leftValue;
				return calculateHumanYell(currentMonkey.right, leftValue - currentValue, monkeyYells);
			}
			else if(currentMonkey.operation == '*') {
				if(monkeyYells.get(currentMonkey.right) instanceof YellNumber)
					return currentValue / leftValue;
				return calculateHumanYell(currentMonkey.right, currentValue / leftValue, monkeyYells);
			}
			else {
				if(monkeyYells.get(currentMonkey.right) instanceof YellNumber)
					return leftValue / currentValue;
				return calculateHumanYell(currentMonkey.right, leftValue / currentValue, monkeyYells);
			}
		}
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
			Yell left = monkeyYells.get(yo.left);
			Yell right = monkeyYells.get(yo.right);

			if((left instanceof YellNumber && ((YellNumber) left).isHuman) || (left instanceof YellOperation) && ((YellOperation) left).containsHuman) {
				yo.containsHuman = true;
				yo.humanSide = "left";
			}
			else if((right instanceof YellNumber && ((YellNumber) right).isHuman) || (right instanceof YellOperation) && ((YellOperation) right).containsHuman) {
				yo.containsHuman = true;
				yo.humanSide = "right";
			}

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
				YellNumber yn = new YellNumber(Long.parseLong(s));
				if(name.equals("humn"))
					yn.isHuman = true;

				map.put(name, yn);
			}
		}
		return map;
	}

	private interface Yell {}

	private class YellNumber implements Yell {
		public boolean isHuman;
		public long number;
		public YellNumber(long number) {
			this.number = number;
			this.isHuman = false;
		}
	}

	private class YellOperation implements Yell {
		public boolean containsHuman;
		public String humanSide;
		public char operation;
		public String left, right;
		public YellOperation(char operation, String left, String right) {
			this.operation = operation;
			this.left = left;
			this.right = right;
		}
	}
}
