import java.util.ArrayList;

public class Day14 {
	public static void main(String[] args) {
		new Day14();
	}

	public Day14() {
		ArrayList<String> input = ReadInput.read("res/input14.txt");
		partOne(input);
		partTwo(input);
	}

	private int[][] map;
	private int maxY;

	private void partOne(ArrayList<String> input) {
		this.maxY = 0;
		this.map = parseInput(input);
		int counter = 0;
		boolean done = false;
		while(true) {
			int sandX = 500;
			int sandY = 0;
			map[sandX][sandY] = 2;
			while(true) {
				if(sandY == this.maxY) {
					done = true;
					break;
				}
				if(this.map[sandX][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX, sandY + 1);
					sandY += 1;
					continue;
				}
				else if(this.map[sandX - 1][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX - 1, sandY + 1);
					sandX -= 1;
					sandY += 1;
					continue;
				}
				else if(this.map[sandX + 1][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX + 1, sandY + 1);
					sandX += 1;
					sandY += 1;
					continue;
				}
				counter++;
				break;
			}
			if(done)
				break;
		}
		System.out.println(counter);
	}

	private void partTwo(ArrayList<String> input) {
		this.maxY = 0;
		this.map = parseInput(input);
		this.maxY++;
		int counter = 0;
		while(true) {
			if(map[500][0] == 2)
				break;

			int sandX = 500;
			int sandY = 0;
			map[sandX][sandY] = 2;
			while(true) {
				if(sandY == this.maxY) {
					counter++;
					break;
				}
				if(this.map[sandX][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX, sandY + 1);
					sandY += 1;
					continue;
				}
				else if(this.map[sandX - 1][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX - 1, sandY + 1);
					sandX -= 1;
					sandY += 1;
					continue;
				}
				else if(this.map[sandX + 1][sandY + 1] == 0) {
					moveSand(sandX, sandY, sandX + 1, sandY + 1);
					sandX += 1;
					sandY += 1;
					continue;
				}
				counter++;
				break;
			}
		}
		System.out.println(counter);
	}

	private void moveSand(int x1, int y1, int x2, int y2) {
		this.map[x1][y1] = 0;
		this.map[x2][y2] = 2;
	}

	private int[][] parseInput(ArrayList<String> input) {
		int[][] res = new int[1000][1000];
		for(String line : input) {
			String[] coords = line.split(" -> ");
			for(int i = 0; i < coords.length - 1; i++) {
				int x1 = Integer.parseInt(coords[i].split(",")[0]);
				int y1 = Integer.parseInt(coords[i].split(",")[1]);
				int x2 = Integer.parseInt(coords[i+1].split(",")[0]);
				int y2 = Integer.parseInt(coords[i+1].split(",")[1]);
				if(Math.max(y1, y2) > this.maxY)
					this.maxY = Math.max(y1, y2);
				if(x1 != x2) {
					if(x1 < x2) {
						for(int x = x1; x <= x2; x++) {
							res[x][y1] = 1;
						}
					}
					else {
						for(int x = x2; x <= x1; x++) {
							res[x][y1] = 1;
						}
					}
				}
				else {
					if(y1 < y2) {
						for(int y = y1; y <= y2; y++) {
							res[x1][y] = 1;
						}
					}
					else {
						for(int y = y2; y <= y1; y++) {
							res[x1][y] = 1;
						}
					}
				}
			}
		}
		return res;
	}

}
