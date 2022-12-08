import java.util.ArrayList;
import java.util.Collections;

public class Day8 {
	public static void main(String[] args) {
		new Day8();
	}

	public Day8() {
		ArrayList<String> input = ReadInput.read("res/input8.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		int[][] trees = new int[input.get(0).length()][input.size()];
		for(int i = 0; i < trees.length; i++) {
			for(int j = 0; j < trees[0].length; j++) {
				trees[i][j] = Integer.parseInt(Character.toString(input.get(j).charAt(i)));
			}
		}
		System.out.println(countVisibleTrees(trees));
	}

	private void partTwo(ArrayList<String> input) {
		int[][] trees = new int[input.get(0).length()][input.size()];
		for(int i = 0; i < trees.length; i++) {
			for(int j = 0; j < trees[0].length; j++) {
				trees[i][j] = Integer.parseInt(Character.toString(input.get(j).charAt(i)));
			}
		}

		ArrayList<Integer> scores = calculateScenicScores(trees);
		Collections.sort(scores);
		System.out.println(scores.get(scores.size() - 1));
	}

	private int countVisibleTrees(int[][] trees) {
		int res = 0;

		for(int i = 0; i < trees.length; i++) {
			for(int j = 0; j < trees[0].length; j++) {
				if(i == 0 || i == trees.length - 1 || j == 0 || j == trees[0].length -  1)
					res++;
				else if(isVisible(trees, i, j, "UP") || isVisible(trees, i, j, "DOWN") ||
					isVisible(trees, i, j, "LEFT") || isVisible(trees, i, j, "RIGHT"))
					res++;
			}
		}

		return res;
	}
	
	private boolean isVisible(int[][] trees, int x, int y, String direction) {
		if(direction.equals("UP")) {
			for(int i = trees[0].length - 2; i >= y; i--) {
				if(trees[x][i + 1] >= trees[x][y])
					return false;
			}
		}
		else if(direction.equals("DOWN")) {
			for(int i = 1; i <= y; i++) {
				if(trees[x][i - 1] >= trees[x][y])
					return false;
			}
		}
		else if(direction.equals("LEFT")) {
			for(int i = trees.length - 2; i >= x; i--) {
				if(trees[i + 1][y] >= trees[x][y])
					return false;
			}
		}
		else {
			for(int i = 1; i <= x; i++) {
				if(trees[i - 1][y] >= trees[x][y])
					return false;
			}
		}
		return true;
	}

	private ArrayList<Integer> calculateScenicScores(int[][] trees) {
		ArrayList<Integer> scores = new ArrayList<Integer>();

		for(int i = 1; i < trees.length - 1; i++) {
			for(int j = 1; j < trees[0].length - 1; j++) {
				scores.add(countTrees(trees, i, j, "UP") * countTrees(trees, i, j, "DOWN") *
					countTrees(trees, i, j, "LEFT") * countTrees(trees, i, j, "RIGHT"));
			}
		}

		return scores;
	}

	private int countTrees(int[][] trees, int x, int y, String direction) {
		int res = 0;
		if(direction.equals("UP")) {
			for(int i = y - 1; i >= 0; i--) {
				res++;
				if(trees[x][i] >= trees[x][y])
					break;
			}
		}
		else if(direction.equals("DOWN")) {
			for(int i = y + 1; i < trees[0].length; i++) {
				res++;
				if(trees[x][i] >= trees[x][y])
					break;
			}
		}
		else if(direction.equals("LEFT")) {
			for(int i = x - 1; i >= 0; i--) {
				res++;
				if(trees[i][y] >= trees[x][y])
					break;
			}
		}
		else {
			for(int i = x + 1; i < trees.length; i++) {
				res++;
				if(trees[i][y] >= trees[x][y])
					break;
			}
		}
		return res;
	}
}
