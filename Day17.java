import java.util.ArrayList;

public class Day17 {
	public static void main(String[] args) {
		new Day17();
	}

	public Day17() {
		ArrayList<String> input = ReadInput.read("res/input17.txt");
		partOne(input);
		partTwo(input);
	}

	private String instructions;
	private int currentInstruction;
	private int currentShape;
	private int currentHighestPoint;
	private int[][] map;

	private void partOne(ArrayList<String> input) {
		init(input);
		for(int i = 0; i < 2022; i++) {
			ArrayList<Coord> coords = spawnNextShape();
			while(true) {
				if(getNextInstruction() == '<')
					moveLeft(coords);
				else
					moveRight(coords);

				if(rockHasStopped(coords)) {
					for(Coord c : coords)
						map[c.x][c.y] = 1;
					break;
				}
				moveDown(coords);
			}
		}
		calculateHighestPoint();
		System.out.println(currentHighestPoint + 1);
	}

	private void partTwo(ArrayList<String> input) {
		
	}

	private void moveLeft(ArrayList<Coord> coords) {
		for(Coord c : coords) {
			if(c.x - 1 == -1)
				return;
			if(map[c.x - 1][c.y] == 1)
				return;
		}
		for(Coord c : coords)
			c.x--;
	}

	private void moveRight(ArrayList<Coord> coords) {
		for(Coord c : coords) {
			if(c.x + 1 == 7)
				return;
			if(map[c.x + 1][c.y] == 1)
				return;
		}
		for(Coord c : coords)
			c.x++;
	}

	private void moveDown(ArrayList<Coord> coords) {
		for(Coord c : coords)
			c.y--;
	}

	private boolean rockHasStopped(ArrayList<Coord> coords) {
		for(Coord c : coords) {
			if(c.y - 1 == -1)
				return true;
			if(map[c.x][c.y - 1] == 1)
				return true;
		}
		return false;
	}
	
	private ArrayList<Coord> spawnNextShape() {
		int shape = getNextShape();
		calculateHighestPoint();
		ArrayList<Coord> coords = new ArrayList<Coord>();
		if(shape == 0) {
			coords.add(new Coord(2, this.currentHighestPoint + 4));
			coords.add(new Coord(3, this.currentHighestPoint + 4));
			coords.add(new Coord(4, this.currentHighestPoint + 4));
			coords.add(new Coord(5, this.currentHighestPoint + 4));
		}
		else if(shape == 1) {
			coords.add(new Coord(3, this.currentHighestPoint + 6));
			coords.add(new Coord(2, this.currentHighestPoint + 5));
			coords.add(new Coord(3, this.currentHighestPoint + 5));
			coords.add(new Coord(4, this.currentHighestPoint + 5));
			coords.add(new Coord(3, this.currentHighestPoint + 4));
		}
		else if(shape == 2) {
			coords.add(new Coord(2, this.currentHighestPoint + 4));
			coords.add(new Coord(3, this.currentHighestPoint + 4));
			coords.add(new Coord(4, this.currentHighestPoint + 4));
			coords.add(new Coord(4, this.currentHighestPoint + 5));
			coords.add(new Coord(4, this.currentHighestPoint + 6));
		}
		else if(shape == 3) {
			coords.add(new Coord(2, this.currentHighestPoint + 4));
			coords.add(new Coord(2, this.currentHighestPoint + 5));
			coords.add(new Coord(2, this.currentHighestPoint + 6));
			coords.add(new Coord(2, this.currentHighestPoint + 7));
		}
		else if(shape == 4) {
			coords.add(new Coord(2, this.currentHighestPoint + 4));
			coords.add(new Coord(3, this.currentHighestPoint + 4));
			coords.add(new Coord(2, this.currentHighestPoint + 5));
			coords.add(new Coord(3, this.currentHighestPoint + 5));
		}
		return coords;
	}

	private boolean firstCall;

	private void calculateHighestPoint() {
		if(firstCall) {
			firstCall = false;
			return;
		}
		int highest = -1;
		for(int y = 0; y < this.map[0].length; y++) {
			boolean hasRockPart = false;
			for(int x = 0; x < this.map.length; x++) {
				if(map[x][y] == 1)
					hasRockPart = true;
			}
			if(!hasRockPart) {
				this.currentHighestPoint = highest;
				return;
			}
			highest++;
		}
	}
	
	private char getNextInstruction() {
		this.currentInstruction++;
		if(this.currentInstruction == this.instructions.length())
			this.currentInstruction = 0;
		return instructions.charAt(this.currentInstruction);
	}
	
	private int getNextShape() {
		this.currentShape++;
		if(this.currentShape == 5)
			this.currentShape = 0;
		return this.currentShape;
	}

	private void init(ArrayList<String> input) {
		this.map = new int[7][100000];
		this.firstCall = true;
		this.currentHighestPoint = -1;
		this.currentInstruction = -1;
		this.currentShape = -1;
		this.instructions = input.get(0);
	}
	
	private class Coord {
		int x, y;
		public Coord(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
}
