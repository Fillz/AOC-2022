import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Day11 {
	public static void main(String[] args) {
		new Day11();
	}

	public Day11() {
		ArrayList<String> input = ReadInput.read("res/input11.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Monkey> monkeys = parseInput(input);
		for(int round = 0; round < 20; round++) {
			for(Monkey monkey : monkeys) {
				for(int i = 0; i < monkey.items.size(); i++) {
					monkey.totalInspects++;
					monkey.performOperation(i);
					monkey.postOperationDivision(i, 3);
					int item = monkey.items.get(i);
					if(item % monkey.divisibleBy == 0) {
						monkeys.get(monkey.throwTrue).items.add(item);
					}
					else {
						monkeys.get(monkey.throwFalse).items.add(item);
					}
				}
				monkey.items = new ArrayList<Integer>();
			}
		}

		Collections.sort(monkeys, new Comparator<Monkey>() {
			public int compare(Monkey m1, Monkey m2) {
				if(m1.totalInspects == m2.totalInspects)
					return 0;
				return m1.totalInspects < m2.totalInspects ? -1 : 1;
			}
	   	});
		Collections.reverse(monkeys);
		System.out.println(monkeys.get(0).totalInspects * monkeys.get(1).totalInspects);
	}

	private void partTwo(ArrayList<String> input) {
		
	}

	private ArrayList<Monkey> parseInput(ArrayList<String> input) {
		ArrayList<Monkey> res = new ArrayList<Monkey>();
		for(int i = 0; i < input.size(); i+=7) {
			String itemsString = input.get(i+1).trim();
			String operationString = input.get(i+2).trim();
			String divisibleString = input.get(i+3).trim();
			String throwTrueString = input.get(i+4).trim();
			String throwFalseString = input.get(i+5).trim();

			ArrayList<Integer> items = new ArrayList<Integer>();
			for(String item : itemsString.split(":")[1].split(",")) {
				items.add(Integer.parseInt(item.trim()));
			}
			Monkey.Operation operation = operationString.contains("*") ? Monkey.Operation.MUL : Monkey.Operation.ADD;

			int operationValue;
			boolean operationOnOld;
			if(operationString.split(" ")[5].equals("old")) {
				operationValue = -1;
				operationOnOld = true;
			}
			else {
				operationValue = Integer.parseInt(operationString.split(" ")[5]);
				operationOnOld = false;
			}

			int divisibleBy = Integer.parseInt(divisibleString.split(" ")[3]);
			int throwTrue = Integer.parseInt(throwTrueString.split(" ")[5]);
			int throwFalse = Integer.parseInt(throwFalseString.split(" ")[5]);

			res.add(new Monkey(items, operation, operationValue, operationOnOld, divisibleBy, throwTrue, throwFalse));
		}
		return res;
	}

	private class Monkey {

		public int totalInspects;

		public int opValue, divisibleBy, throwTrue, throwFalse;
		private boolean operationOnOld;
		public ArrayList<Integer> items;
		private Operation operation;

		public enum Operation {
			MUL,
			ADD
		};

		public Monkey(ArrayList<Integer> items, Operation op, int opValue, boolean operationOnOld, int divisibleBy, int throwTrue, int throwFalse) {
			this.items = items;
			this.operation = op;
			this.opValue = opValue;
			this.operationOnOld = operationOnOld;
			this.divisibleBy = divisibleBy;
			this.throwTrue = throwTrue;
			this.throwFalse = throwFalse;
			this.totalInspects = 0;
		}

		public void performOperation(int index) {
			if(this.operation == operation.MUL) {
				if(this.operationOnOld)
					this.items.set(index, this.items.get(index) * this.items.get(index));
				else
					this.items.set(index, this.items.get(index) * this.opValue);
			}
			else {
				if(this.operationOnOld)
					this.items.set(index, this.items.get(index) + this.items.get(index));
				else
					this.items.set(index, this.items.get(index) + this.opValue);
			}
		}

		public void postOperationDivision(int index, int divBy) {
			this.items.set(index, this.items.get(index) / divBy);
		}

	}

}
