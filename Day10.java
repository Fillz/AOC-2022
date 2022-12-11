import java.util.ArrayList;

public class Day10 {
	public static void main(String[] args) {
		new Day10();
	}

	public Day10() {
		ArrayList<String> input = ReadInput.read("res/input10.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		int cycle = 1;
		int processingDoneCycle = -1;
		int processingValue = 0;
		int registryX = 1;

		int res = 0;

		for(int i = 0; i < input.size(); i++) {
			String instruction = input.get(i);

			if(cycle % 40 == 20) {
				res += cycle * registryX;
			}
			if(cycle > 220)
				break;
			
			if(processingDoneCycle > cycle) {
				i--;
				cycle++;
				registryX += processingValue;
				continue;
			}

			if(instruction.contains("addx")) {
				processingValue = Integer.parseInt(instruction.split(" ")[1]);
				processingDoneCycle = cycle + 2;
			}
			cycle++;
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		int cycle = 1;
		int processingDoneCycle = -1;
		int processingValue = 0;
		int registryX = 1;

		String output = "";

		for(int i = 0; i < input.size(); i++) {
			String instruction = input.get(i);

			if((cycle - 1) % 40 >= registryX - 1 && (cycle - 1) % 40 <= registryX + 1)
				output += "#";
			else
				output += ".";
			if(cycle % 40 == 0)
				output += "\n";
			if(cycle > 240)
				break;
			
			if(processingDoneCycle > cycle) {
				i--;
				cycle++;
				registryX += processingValue;
				continue;
			}

			if(instruction.contains("addx")) {
				processingValue = Integer.parseInt(instruction.split(" ")[1]);
				processingDoneCycle = cycle + 2;
			}
			cycle++;
		}
		System.out.println(output);
	}

}
