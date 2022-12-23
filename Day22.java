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

	int topX = 51;
	int topY = 51;
	int upX = 51;
	int upY = 1;
	int downX = 51;
	int downY = 101;
	int rightX = 101;
	int rightY = 1;
	int leftX = 1;
	int leftY = 101;
	int botX = 1;
	int botY = 151;

	private void partTwo(ArrayList<String> input) {
		// OBS! Only works for my specific input.
		Tile[][] map = parseMap(input);
		fixWraparoundsPart2(map);
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
						if(current.right != null && !(current.right instanceof WallTile)) {
							facing = updateFacingIfChangedCubeSide(facing, current.x, current.y);
							current = current.right;
						}
						else
							break;
					}
					else if(facing == 1) {
						if(current.down != null && !(current.down instanceof WallTile)) {
							facing = updateFacingIfChangedCubeSide(facing, current.x, current.y);
							current = current.down;
						}
						else
							break;
					}
					else if(facing == 2) {
						if(current.left != null && !(current.left instanceof WallTile)) {
							facing = updateFacingIfChangedCubeSide(facing, current.x, current.y);
							current = current.left;
						}
						else
							break;
					}
					else {
						if(current.up != null && !(current.up instanceof WallTile)) {
							facing = updateFacingIfChangedCubeSide(facing, current.x, current.y);
							current = current.up;
						}
						else
							break;
					}
				} 
			}
		}
		System.out.println(1000 * current.y + 4 * current.x + facing);
	}

	private int updateFacingIfChangedCubeSide(int facing, int x, int y) {
		// up, left side
		if(x == upX && y >= upY && y <= upY + 49 && facing == 2)
			return 0;
		// up, up side
		else if(x >= upX && x <= upX + 49 && y == upY && facing == 3)
			return 0;
		// right, up side
		//else if(x >= rightX && x <= rightX + 49 && y == rightY && facing == 3)
		//	return 3;
		// right, right side
		else if(x == rightX + 49 && y >= rightY && y <= rightY + 49 && facing == 0)
			return 2;
		// right, down side
		else if(x >= rightX && x <= rightX + 49 && y == rightY + 49 && facing == 1)
			return 2;
		// top, left side
		else if(x == topX && y >= topY && y <= topY + 49 && facing == 2)
			return 1;
		// top, right side
		else if(x == topX + 49 && y >= topY && y <= topY + 49 && facing == 0)
			return 3;
		// down, right side 
		else if(x == downX + 49 && 	y >= downY && y <= downY + 49 && facing == 0)
			return 2;
		// down, down side
		else if(x >= downX && x <= downX + 49 && y == downY + 49 && facing == 1)
			return 2;
		// left, up side
		else if(x >= leftX && x <= leftX + 49 && y == leftY && facing == 3)
			return 0;
		// left, left side
		else if(x == leftX && y >= leftY && y <= leftY + 49 && facing == 2)
			return 0;
		// bot, left side
		else if(x == botX && y >= botY && y <= botY + 49 && facing == 2)
			return 1;
		//bot, bot side
		//else if(x >= botX && x <= botX + 49 && y == botY + 49 && facing == 1)
		//	return 1;
		//bot, right side
		else if(x == botX + 49 && y >= botY && y <= botY + 49 && facing == 0)
			return 3;

		return facing;
	}

	private void fixWraparoundsPart2(Tile[][] map) {
		for(int i = 0; i < 50; i++) {
			// wrap up and bot
			Tile upUpTile = map[upX + i][upY];
			Tile botLeftTile = map[botX][botY + i];
			upUpTile.up = botLeftTile;
			botLeftTile.left = upUpTile;

			// wrap up and left
			Tile upLeftTile = map[upX][upY + i];
			Tile leftLeftTile = map[leftX][leftY + 49 - i];
			upLeftTile.left = leftLeftTile;
			leftLeftTile.left = upLeftTile;

			// wrap right and bot
			Tile rightUpTile = map[rightX + i][rightY];
			Tile botDownTile = map[botX + i][botY + 49];
			rightUpTile.up = botDownTile;
			botDownTile.down = rightUpTile;

			// wrap right and down
			Tile rightRightTile = map[rightX + 49][rightY + i];
			Tile downRightTile = map[downX + 49][downY + 49 - i];
			rightRightTile.right = downRightTile;
			downRightTile.right = rightRightTile;

			// wrap right and top
			Tile rightDownTile = map[rightX + i][rightY + 49];
			Tile topRightTile = map[topX + 49][topY + i];
			rightDownTile.down = topRightTile;
			topRightTile.right = rightDownTile;

			// wrap top and left
			Tile topLeftTile = map[topX][topY + i];
			Tile leftTopTile = map[leftX + i][leftY];
			topLeftTile.left = leftTopTile;
			leftTopTile.up = topLeftTile;

			//wrap down and bot
			Tile downDownTile = map[downX + i][downY + 49];
			Tile botRightTile = map[botX + 49][botY + i];
			downDownTile.down = botRightTile;
			botRightTile.right = downDownTile;
		}
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
