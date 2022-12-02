import java.util.ArrayList;

public class Day2 {
	public static void main(String[] args) {
		new Day2();
	}

	public Day2() {
		ArrayList<String> input = ReadInput.read("res/input2.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		int totalPoints = 0;
		for(String game : input) {
			char opponentHand = game.split(" ")[0].charAt(0);
			char myHand = game.split(" ")[1].charAt(0);
			int result = calculateResult(myHand, opponentHand);
			if(myHand == 'X')
				totalPoints += 1;
			else if(myHand == 'Y')
				totalPoints += 2;
			else
				totalPoints += 3;
			if(result == 0)
				totalPoints += 3;
			else if(result == 1)
				totalPoints += 6;
		}
		System.out.println(totalPoints);
	}

	private void partTwo(ArrayList<String> input) {
		int totalPoints = 0;
		for(String game : input) {
			char opponentHand = game.split(" ")[0].charAt(0);
			char outcome = game.split(" ")[1].charAt(0);

			if(outcome == 'X') {
				if(opponentHand == 'A')
					totalPoints += 3;
				else if(opponentHand == 'B')
					totalPoints += 1;
				else
					totalPoints += 2;
			}
			else if(outcome == 'Y') {
				totalPoints += 3;
				if(opponentHand == 'A')
					totalPoints += 1;
				else if(opponentHand == 'B')
					totalPoints += 2;
				else
					totalPoints += 3;
			}
			else {
				totalPoints += 6;
				if(opponentHand == 'A')
					totalPoints += 2;
				else if(opponentHand == 'B')
					totalPoints += 3;
				else
					totalPoints += 1;
			}
		}
		System.out.println(totalPoints);
	}

	private int calculateResult(char myHand, char opponentHand) {
		if(myHand == 'X') {
			if(opponentHand == 'A')
				return 0;
			else if(opponentHand == 'B')
				return -1;
			else
				return 1;
		}
		else if(myHand == 'Y') {
			if(opponentHand == 'A')
				return 1;
			else if(opponentHand == 'B')
				return 0;
			else
				return -1;
		}
		else {
			if(opponentHand == 'A')
				return -1;
			else if(opponentHand == 'B')
				return 1;
			else
				return 0;
		}
	}

}
