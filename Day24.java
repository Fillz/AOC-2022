import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Objects;

public class Day24 {
	public static void main(String[] args) {
		new Day24();
	}

	public Day24() {
		ArrayList<String> input = ReadInput.read("res/input24.txt");
		partOne(input);
		partTwo(input);
	}

	private Tile startTile;
	private Tile endTile;

	private void partOne(ArrayList<String> input) {
		Map map = parseInput(input);
		int shortest = shortestPathBFS(startTile, map, 0);
		System.out.println(shortest);
	}

	private void partTwo(ArrayList<String> input) {
		Map map = parseInput(input);
		int shortestFirst = shortestPathBFS(startTile, map, 0);

		Tile tmp = this.startTile;
		this.startTile = this.endTile;
		this.endTile = tmp;
		int shortestSecond = shortestPathBFS(startTile, map, shortestFirst);

		tmp = this.startTile;
		this.startTile = this.endTile;
		this.endTile = tmp;
		int shortestThird = shortestPathBFS(startTile, map, shortestSecond);
		System.out.println(shortestThird);
	}

	private int shortestPathBFS(Tile firstTile, Map map, int startTime) {
		LinkedList<BFSState> queue = new LinkedList<BFSState>();
		HashMap<BFSState, Boolean> visitedStates = new HashMap<BFSState, Boolean>();
		queue.add(new BFSState(firstTile, startTime));

		while(!queue.isEmpty()) {
			BFSState currentState = queue.remove();
			Tile currentTile = currentState.tile;
			int time = currentState.time;

			if(visitedStates.get(currentState) != null)
				continue;

			if(currentTile.equals(this.endTile))
				return time;

			Tile rightTile = map.getTile(currentTile.x + 1, currentTile.y);
			if(rightTile != null && !tileHasWindAtTime(rightTile, time + 1, map))
				queue.add(new BFSState(rightTile, time + 1));

			Tile leftTile = map.getTile(currentTile.x - 1, currentTile.y);
			if(leftTile != null && !tileHasWindAtTime(leftTile, time + 1, map))
				queue.add(new BFSState(leftTile, time + 1));

			Tile downTile = map.getTile(currentTile.x, currentTile.y + 1);
			if(downTile != null && !tileHasWindAtTime(downTile, time + 1, map))
				queue.add(new BFSState(downTile, time + 1));
			
			Tile upTile = map.getTile(currentTile.x, currentTile.y - 1);
			if(upTile != null && !tileHasWindAtTime(upTile, time + 1, map))
				queue.add(new BFSState(upTile, time + 1));
			
			// dont move
			if(!tileHasWindAtTime(currentTile, time + 1, map))
				queue.add(new BFSState(currentTile, time + 1));

			visitedStates.put(currentState, true);
		}
		
		return -1;
	}

	private boolean tileHasWindAtTime(Tile t, int time, Map map) {
		int modHorizontalTime = time % map.innerWidth;
		int modVerticalTime = time % map.innerHeight;
		for(int verticalWindTime : t.verticalWindTimes) {
			if(verticalWindTime == modVerticalTime)
				return true;
		}
		for(int horizontalWindTime : t.horizontalWindTimes) {
			if(horizontalWindTime == modHorizontalTime)
				return true;
		}
		return false;
	}

	private Map parseInput(ArrayList<String> input) {
		Map map = new Map(input.get(0).length(), input.size());
		// Add tiles
		for(int y = 0; y < map.height; y++) {
			for(int x = 0; x < map.width; x++) {
				if(input.get(y).charAt(x) == '#')
					continue;
				else
					map.setTile(x, y);				
			}
		}

		// Add winds
		for(int y = 0; y < map.height; y++) {
			for(int x = 0; x < map.width; x++) {
				char tile = input.get(y).charAt(x);
				if(tile == '>' || tile == '<' || tile == '^' || tile == 'v')
					addWindToMap(map, x, y, tile);
			}
		}

		// Find startTile
		for(int x = 0; x < input.get(0).length(); x++) {
			if(map.getTile(x, 0) != null) {
				startTile = map.getTile(x, 0);
				break;
			}
		}
		// Find endtile
		for(int x = 0; x < input.get(0).length(); x++) {
			if(map.getTile(x, input.size() - 1) != null) {
				endTile = map.getTile(x, input.size() - 1);
				break;
			}
		}
		return map;
	}

	private void addWindToMap(Map map, int x, int y, char wind) {
		int counter = 1;
		if(wind == '>') {
			map.getTile(x, y).horizontalWindTimes.add(0);
			int xIndex = (x + 1) % map.width;
			while(xIndex != x) {
				if(map.getTile(xIndex, y) != null) {
					map.getTile(xIndex, y).horizontalWindTimes.add(counter);
					counter++;
				}
				xIndex = (xIndex + 1) % map.width;
			}
		}
		else if(wind == '<') {
			map.getTile(x, y).horizontalWindTimes.add(0);
			int xIndex = x == 0 ? map.width - 1 : x - 1;
			while(xIndex != x) {
				if(map.getTile(xIndex, y) != null) {
					map.getTile(xIndex, y).horizontalWindTimes.add(counter);
					counter++;
				}
				xIndex = xIndex == 0 ? map.width - 1 : xIndex - 1;
			}
		}
		else if(wind == 'v') {
			map.getTile(x, y).verticalWindTimes.add(0);
			int yIndex = (y + 1) % map.height;
			while(yIndex != y) {
				if(map.getTile(x, yIndex) != null) {
					map.getTile(x, yIndex).verticalWindTimes.add(counter);
					counter++;
				}
				yIndex = (yIndex + 1) % map.height;
			}
		}
		else {
			map.getTile(x, y).verticalWindTimes.add(0);
			int yIndex = y == 0 ? map.height - 1 : y - 1;
			while(yIndex != y) {
				if(map.getTile(x, yIndex) != null) {
					map.getTile(x, yIndex).verticalWindTimes.add(counter);
					counter++;
				}
				yIndex = yIndex == 0 ? map.height - 1 : yIndex - 1;
			}
		}
	}

	private class Map {
		private HashMap<String, Tile> map;
		public int width, height;
		public int innerWidth, innerHeight;
		
		public Map(int width, int height) {
			map = new HashMap<String, Tile>();
			this.width = width;
			this.height = height;
			this.innerWidth = width - 2;
			this.innerHeight = height - 2;
		}

		public Tile getTile(int x, int y) {
			return map.get("" + x + "," + y);
		}

		public void setTile(int x, int y) {
			map.put("" + x + "," + y, new Tile(x, y));
		}
	}

	private class Tile {
		public int x, y;
		public ArrayList<Integer> horizontalWindTimes;
		public ArrayList<Integer> verticalWindTimes;
		public Tile(int x, int y) {
			this.x = x;
			this.y = y;
			horizontalWindTimes = new ArrayList<Integer>();
			verticalWindTimes = new ArrayList<Integer>(); 
		}
	}

	private class BFSState {
		public Tile tile;
		public int time;
		public BFSState(Tile tile, int time) {
			this.tile = tile;
			this.time=  time;
		}

		@Override
		public boolean equals(Object obj) {
			BFSState bfsstate = (BFSState) obj;
			return bfsstate.tile.x == this.tile.x && bfsstate.tile.y == this.tile.y && bfsstate.time == this.time;
		}

		@Override
		public int hashCode() {
			return Objects.hash(this.tile.x, this.tile.y, this.time);
		}
	}
}
