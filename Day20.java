import java.util.ArrayList;

public class Day20 {
	public static void main(String[] args) {
		new Day20();
	}

	public Day20() {
		ArrayList<String> input = ReadInput.read("res/input20.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Number> numbers = parseInput(input);
		for(int i = 0; i < numbers.size(); i++) {
			int currentIndex = getIndexOfNumberWithStartIndex(numbers, i);
			Number currentNumber = numbers.get(currentIndex);
			if(currentNumber.number == 0)
				continue;

			
			if(currentNumber.number > 0) {
				int targetIndex = (currentIndex + currentNumber.number) % numbers.size();
				if(currentIndex + currentNumber.number >= numbers.size())
					targetIndex++;

				if(targetIndex == currentIndex)
					continue;
				if(targetIndex > currentIndex) {
					moveLeft(numbers, currentIndex, targetIndex);
					numbers.set(targetIndex, currentNumber);
				}
				else {
					moveRight(numbers, targetIndex, currentIndex);
					numbers.set(targetIndex, currentNumber);
				}
				/*if(currentIndex + (currentNumber.number % numbers.size()) > numbers.size() - 1) {
					int targetIndex = (currentIndex + currentNumber.number) % numbers.size() + 1;
					moveRight(numbers, targetIndex, currentIndex);
					numbers.set(targetIndex, currentNumber);
				}
				else {
					int targetIndex = currentIndex + currentNumber.number;
					moveLeft(numbers, currentIndex, targetIndex);
					numbers.set(targetIndex, currentNumber);
				}*/
			}
			else {
				int targetIndex = (currentIndex + currentNumber.number) % numbers.size();
				if(currentIndex + currentNumber.number <= 0)
					targetIndex--;
				targetIndex = targetIndex < 0 ? targetIndex + numbers.size() : targetIndex;
				if(targetIndex == currentIndex)
					continue;
				if(targetIndex > currentIndex) {
					moveLeft(numbers, currentIndex, targetIndex);
					numbers.set(targetIndex, currentNumber);
				}
				else {
					moveRight(numbers, targetIndex, currentIndex);
					numbers.set(targetIndex, currentNumber);
				}
				/*if(currentIndex + (currentNumber.number % numbers.size()) <= 0) {
					int mod = ((currentIndex + currentNumber.number) % numbers.size()) - 1;
					int targetIndex = mod < 0 ? mod + numbers.size() : mod;
					moveLeft(numbers, currentIndex, targetIndex);
					numbers.set(targetIndex, currentNumber);
				}
				else {
					int targetIndex = currentIndex + currentNumber.number;
					moveRight(numbers, targetIndex, currentIndex);
					numbers.set(targetIndex, currentNumber);
				}*/
			}
		}
		int zeroIndex = -1;
		for(int i = 0; i < numbers.size(); i++) {
			if(numbers.get(i).number == 0)
				zeroIndex = i;
		}
		int index1 = (zeroIndex + 1000) % numbers.size();
		int index2 = (zeroIndex + 2000) % numbers.size();
		int index3 = (zeroIndex + 3000) % numbers.size();
		System.out.println(numbers.get(index1).number + numbers.get(index2).number + numbers.get(index3).number);
	}

	private void partTwo(ArrayList<String> input) {

	}

	private void moveRight(ArrayList<Number> numbers, int left, int right) {
		for(int i = right; i > left; i--)
			numbers.set(i, numbers.get(i - 1));
	}

	private void moveLeft(ArrayList<Number> numbers, int left, int right) {
		for(int i = left; i < right; i++)
			numbers.set(i, numbers.get(i + 1));
	}

	private int getIndexOfNumberWithStartIndex(ArrayList<Number> numbers, int startIndex) {
		for(int i = 0; i < numbers.size(); i++) {
			if(numbers.get(i).startIndex == startIndex)
				return i;
		}
		return -1;
	}

	private ArrayList<Number> parseInput(ArrayList<String> input) {
		ArrayList<Number> res = new ArrayList<Number>();
		for(int i = 0; i < input.size(); i++)
			res.add(new Number(i, Integer.parseInt(input.get(i))));
		return res;
	}

	private class Number {
		public int startIndex;
		public int number;
		public Number(int startIndex, int number) {
			this.startIndex = startIndex;
			this.number = number;
		}
	}

}
