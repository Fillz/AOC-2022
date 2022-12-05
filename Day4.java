import java.util.ArrayList;

public class Day4 {
	public static void main(String[] args) {
		new Day4();
	}

	public Day4() {
		ArrayList<String> input = ReadInput.read("res/input4.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		int res = 0;
		for(String s : input) {
			String s1 = s.split(",")[0];
			String s2 = s.split(",")[1];
			int left1 = Integer.parseInt(s1.split("-")[0]);
			int left2 = Integer.parseInt(s1.split("-")[1]);
			int right1 = Integer.parseInt(s2.split("-")[0]);
			int right2 = Integer.parseInt(s2.split("-")[1]);
			if(left1 <= right1 && left2 >= right2 || right1 <= left1 && right2 >= left2)
				res++;
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		int res = 0;
		for(String s : input) {
			String s1 = s.split(",")[0];
			String s2 = s.split(",")[1];
			int left1 = Integer.parseInt(s1.split("-")[0]);
			int left2 = Integer.parseInt(s1.split("-")[1]);
			int right1 = Integer.parseInt(s2.split("-")[0]);
			int right2 = Integer.parseInt(s2.split("-")[1]);

			if(left1 <= right2) {
				if(right1 <= left2)
					res++;
			}
			else {
				if(left1 <= right2)
					res++;
			}
		}
		System.out.println(res);
	}

}
