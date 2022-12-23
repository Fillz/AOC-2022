import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class Day23 {
	public static void main(String[] args) {
		new Day23();
	}

	public Day23() {
		ArrayList<String> input = ReadInput.read("res/input23.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		Map map = parseInput(input);

		// 0 = north, 1 = south, 2 = west, 3 = east
		LinkedList<Integer> directions = new LinkedList<Integer>();
		directions.add(0);
		directions.add(1);
		directions.add(2);
		directions.add(3);

		for(int i = 0; i < 10; i++) {
			calculateRound(map, directions);
			directions.add(directions.remove());
		}

		int xMin = Integer.MAX_VALUE;
		int xMax = Integer.MIN_VALUE;
		int yMin = Integer.MAX_VALUE;
		int yMax = Integer.MIN_VALUE;
		for(Elf elf : map.getAllElves()) {
			if(elf.x < xMin)
				xMin = elf.x;
			if(elf.x > xMax)
				xMax = elf.x;
			if(elf.y < yMin)
				yMin = elf.y;
			if(elf.y > yMax)
				yMax = elf.y;
		}
		int emptyTiles = 0;
		for(int y = yMin; y <= yMax; y++) {
			for(int x = xMin; x <= xMax; x++) {
				if(map.getElf(x, y) == null)
					emptyTiles++;
			}
		}
		System.out.println(emptyTiles);
	}

	private void partTwo(ArrayList<String> input) {
		Map map = parseInput(input);

		// 0 = north, 1 = south, 2 = west, 3 = east
		LinkedList<Integer> directions = new LinkedList<Integer>();
		directions.add(0);
		directions.add(1);
		directions.add(2);
		directions.add(3);

		int counter = 1;
		while(true) {
			if(!calculateRound(map, directions))
				break;
			directions.add(directions.remove());
			counter++;
		}
		System.out.println(counter);
	}

	private boolean calculateRound(Map map, LinkedList<Integer> directions) {
		ArrayList<Elf> elves = map.getAllElves();
		ArrayList<MoveInstruction> moveInstructions = new ArrayList<MoveInstruction>();
		HashMap<String, Boolean> existingMoveInstructions = new HashMap<String, Boolean>();
		for(Elf elf : elves) {
			if(map.getElf(elf.x - 1, elf.y - 1) == null && map.getElf(elf.x, elf.y - 1) == null && map.getElf(elf.x + 1, elf.y - 1) == null &&
			   map.getElf(elf.x - 1, elf.y) == null && map.getElf(elf.x + 1, elf.y) == null &&
			   map.getElf(elf.x - 1, elf.y + 1) == null && map.getElf(elf.x, elf.y + 1) == null && map.getElf(elf.x + 1, elf.y + 1) == null)
				continue;
			for(int direction : directions) {
				boolean addMoveInstruction = false;
				int newX = -1;
				int newY = -1;
				if(direction == 0) {
					if(map.getElf(elf.x - 1, elf.y - 1) == null && map.getElf(elf.x, elf.y - 1) == null && map.getElf(elf.x + 1, elf.y - 1) == null) {
						addMoveInstruction = true;
						newX = elf.x;
						newY = elf.y - 1;
					}
				}
				else if(direction == 1) {
					if(map.getElf(elf.x - 1, elf.y + 1) == null && map.getElf(elf.x, elf.y + 1) == null && map.getElf(elf.x + 1, elf.y + 1) == null) {
						addMoveInstruction = true;
						newX = elf.x;
						newY = elf.y + 1;
					}
				}
				else if(direction == 2) {
					if(map.getElf(elf.x - 1, elf.y - 1) == null && map.getElf(elf.x - 1, elf.y) == null && map.getElf(elf.x - 1, elf.y + 1) == null) {
						addMoveInstruction = true;
						newX = elf.x - 1;
						newY = elf.y;
					}
				}
				else {
					if(map.getElf(elf.x + 1, elf.y - 1) == null && map.getElf(elf.x + 1, elf.y) == null && map.getElf(elf.x + 1, elf.y + 1) == null) {
						addMoveInstruction = true;
						newX = elf.x + 1;
						newY = elf.y;
					}
				}
				if(addMoveInstruction) {
					if(existingMoveInstructions.get("" + newX + "," + newY) != null) {
						removeMoveInstruction(moveInstructions, newX, newY);
					}
					else {
						moveInstructions.add(new MoveInstruction(elf.x, elf.y, newX, newY));
						existingMoveInstructions.put("" + newX + "," + newY, true);
					}
					break;
				}
			}
		}
		
		for(MoveInstruction mi : moveInstructions)
			map.moveElf(mi);
		return moveInstructions.size() > 0;
	}

	private void removeMoveInstruction(ArrayList<MoveInstruction> moveInstructions, int x, int y) {
		for(int i = 0; i < moveInstructions.size(); i++) {
			if(moveInstructions.get(i).newX == x && moveInstructions.get(i).newY == y) {
				moveInstructions.remove(i);
				return;
			}
		}
	}

	private Map parseInput(ArrayList<String> input) {
		Map map = new Map();
		for(int y = 0; y < input.size(); y++) {
			String line = input.get(y);
			for(int x = 0; x < line.length(); x++) {
				if(line.charAt(x) == '#')
					map.setElf(x, y);
			}	
		}
		return map;
	}

	private class Elf {
		public int x, y;
		public Elf(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class Map {
		private HashMap<String, Elf> map;
		
		public Map() {
			map = new HashMap<String, Elf>();
		}

		public Elf getElf(int x, int y) {
			return map.get("" + x + "," + y);
		}

		public ArrayList<Elf> getAllElves() {
			ArrayList<Elf> elves = new ArrayList<Elf>();
			for(String key : this.map.keySet())
				elves.add(map.get(key));
			return elves;
		}

		public void setElf(int x, int y) {
			map.put("" + x + "," + y, new Elf(x, y));
		}

		public void moveElf(MoveInstruction mi) {
			Elf e = map.remove("" + mi.oldX + "," + mi.oldY);
			e.x = mi.newX;
			e.y = mi.newY;
			map.put("" + mi.newX + "," + mi.newY, e);
		}
	}

	private class MoveInstruction {
		int oldX, oldY, newX, newY;
		public MoveInstruction(int oldX, int oldY, int newX, int newY) {
			this.oldX = oldX;
			this.oldY = oldY;
			this.newX = newX;
			this.newY = newY;
		}
	}
}
