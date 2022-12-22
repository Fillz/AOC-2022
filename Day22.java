import java.util.ArrayList;

public class Day22 {
	public static void main(String[] args) {
		new Day22();
	}

	public Day22() {
		ArrayList<String> input = ReadInput.read("res/input22.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		Tile[][] map = parseMap(input);
		fixWraparounds(map);
		ArrayList<Instruction> instructions = parseInstructions(input.get(input.size() - 1));
		
		int facing = 0; // 0 = right, 1 = down, 2 = left, 3 = up
		Tile current = findFirstTileInRow(map, 1);
		for(Instruction instruction : instructions) {
			if(instruction instanceof TurnInstruction)
				facing = changeFacing(facing, ((TurnInstruction) instruction).turn);
			else if(instruction instanceof MoveInstruction) {
				MoveInstruction moveInstruction = (MoveInstruction) instruction;
				for(int i = 0; i < moveInstruction.distance; i++) {
					if(facing == 0) {
						if(current.right != null && !(current.right instanceof WallTile))
							current = current.right;
						else
							break;
					}
					else if(facing == 1) {
						if(current.down != null && !(current.down instanceof WallTile))
							current = current.down;
						else
							break;
					}
					else if(facing == 2) {
						if(current.left != null && !(current.left instanceof WallTile))
							current = current.left;
						else
							break;
					}
					else {
						if(current.up != null && !(current.up instanceof WallTile))
							current = current.up;
						else
							break;
					}
				} 
			}
		}
		System.out.println(1000 * current.y + 4 * current.x + facing);
	}

	private void partTwo(ArrayList<String> input) {
		
	}

	private Tile findFirstTileInRow(Tile[][] map, int row) {
		for(int x = 0; x < map.length; x++) {
			if(map[x][row] != null)
				return map[x][row];
		}
		return null;
	}

	private int changeFacing(int currentFacing, char turn) {
		int res = currentFacing;
		if(turn == 'R')
			res++;
		else
			res--;
		res = res % 4;
		return res < 0 ? res + 4 : res;
		
	}

	private Tile[][] parseMap(ArrayList<String> input) {
		int height = input.size() - 2;
		int width = 0;
		for(int i = 0; i < input.size() - 2; i++) {
			if(input.get(i).length() > width)
				width = input.get(i).length();
		}
		Tile[][] map = new Tile[width + 2][height + 2];

		for(int y = 1; y < height + 1; y++) {
			for(int x = 1; x < width + 1; x++) {
				if(x - 1 > input.get(y - 1).length() - 1 || input.get(y - 1).charAt(x - 1) == ' ')
					continue;
				else if(input.get(y - 1).charAt(x - 1) == '#') {
					map[x][y] = new WallTile(x, y);
				}
				else {
					Tile t = new Tile(x, y);
					map[x][y] = t;
					if(map[x - 1][y] != null)
						map[x - 1][y].right = t;
					if(map[x][y - 1] != null)
						map[x][y - 1].down = t;
					t.left = map[x - 1][y];
					t.up = map[x][y - 1];
				}
			}
		}
		return map;
	}

	private void fixWraparounds(Tile[][] map) {
		for(int y = 1; y < map[0].length - 1; y++) {
			Tile first = null;
			for(int x = 1; x < map.length - 1; x++) {
				if(map[x][y] != null) {
					first = map[x][y];
					break;
				}
			}
			Tile last = null;
			for(int x = map.length - 2; x > 0; x--) {
				if(map[x][y] != null) {
					last = map[x][y];
					break;
				}
			}
			first.left = last;
			last.right = first;
		}
		for(int x = 1; x < map.length - 1; x++) {
			Tile first = null;
			for(int y = 1; y < map[x].length - 1; y++) {
				if(map[x][y] != null) {
					first = map[x][y];
					break;
				}
			}
			Tile last = null;
			for(int y = map[x].length - 2; y > 0; y--) {
				if(map[x][y] != null) {
					last = map[x][y];
					break;
				}
			}
			first.up = last;
			last.down = first;
		}
	}

	private ArrayList<Instruction> parseInstructions(String in) {
		ArrayList<Instruction> res = new ArrayList<Instruction>();
		for(int i = 0; i < in.length(); i++) {
			int lastDigitIndex = i;
			if(!Character.isDigit(in.charAt(i))) {
				res.add(new TurnInstruction(in.charAt(i)));
				continue;
			}
			else {
				for(int j = i + 1; j < in.length(); j++) {
					if(!Character.isDigit(in.charAt(j))) {
						lastDigitIndex = j - 1;
						break;
					}
					if(j == in.length() - 1)
						lastDigitIndex = j;
				}
				res.add(new MoveInstruction(Integer.parseInt(in.substring(i, lastDigitIndex + 1))));
				i = lastDigitIndex;
			}
		}
		return res;
	}

	private interface Instruction {}

	private class TurnInstruction implements Instruction {
		public char turn;
		public TurnInstruction(char turn) {
			this.turn = turn;
		}
	}

	private class MoveInstruction implements Instruction {
		public int distance;
		public MoveInstruction(int distance) {
			this.distance = distance;
		}
	}

	private class Tile {
		public int x, y;
		public Tile up, left, right, down;
		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	private class WallTile extends Tile {
		public WallTile(int x, int y) {
			super(x, y);
		}
	}
}
