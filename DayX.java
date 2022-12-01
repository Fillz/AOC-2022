import java.util.ArrayList;
import java.util.Collections;

public class DayX {
	public static void main(String[] args) {
		new DayX();
	}

	public DayX() {
		ArrayList<String> input = ReadInput.read("res/input1.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		int highestCalCount = -1;
		int counter = 0;
		for(String s : input) {
			if(s.equals("")) {
				if(counter > highestCalCount)
					highestCalCount = counter;
				counter = 0;
				continue;
			}
			counter += Integer.parseInt(s);
		}
		System.out.println(highestCalCount);
	}

	private void partTwo(ArrayList<String> input) {
		ArrayList<Integer> calList = new ArrayList<Integer>();
		int counter = 0;
		for(String s : input) {
			if(s.equals("")) {
				calList.add(counter);
				counter = 0;
				continue;
			}
			counter += Integer.parseInt(s);
		}
		Collections.sort(calList);
		Collections.reverse(calList);
		System.out.println(calList.get(0) + calList.get(1) + calList.get(2));
	}
}
