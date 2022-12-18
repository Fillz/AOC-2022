import java.util.ArrayList;
import java.util.HashMap;

public class Day18 {
	public static void main(String[] args) {
		new Day18();
	}

	public Day18() {
		ArrayList<String> input = ReadInput.read("res/input18.txt");
		partOne(input);
		partTwo(input);
	}

	private int partOneAnswer;

	private void partOne(ArrayList<String> input) {
		ArrayList<Cube> cubes = parseInput(input);
		int sides = cubes.size()*6;
		for(int i = 0; i < cubes.size(); i++) {
			for(int j = i + 1; j < cubes.size(); j++) {
				Cube c1 = cubes.get(i);
				Cube c2 = cubes.get(j);
				if(c1.x + 1 == c2.x && c1.y == c2.y && c1.z == c2.z)
					sides -= 2;
				else if(c1.x - 1 == c2.x && c1.y == c2.y && c1.z == c2.z)
					sides -= 2;
				else if(c1.x == c2.x && c1.y + 1 == c2.y && c1.z == c2.z)
					sides -= 2;
				else if(c1.x == c2.x && c1.y - 1 == c2.y && c1.z == c2.z)
					sides -= 2;
				else if(c1.x == c2.x && c1.y == c2.y && c1.z + 1 == c2.z)
					sides -= 2;
				else if(c1.x == c2.x && c1.y == c2.y && c1.z - 1 == c2.z)
					sides -= 2;
			}
		}
		this.partOneAnswer = sides;
		System.out.println(sides);
	}

	int minX = Integer.MAX_VALUE;
	int minY = Integer.MAX_VALUE;
	int minZ = Integer.MAX_VALUE;
	int maxX = Integer.MIN_VALUE;
	int maxY = Integer.MIN_VALUE;
	int maxZ = Integer.MIN_VALUE;

	private void partTwo(ArrayList<String> input) {
		ArrayList<Cube> cubes = parseInput(input);
		
		for(Cube c : cubes) {
			if(c.x > maxX)
				maxX = c.x;
			else if(c.x < minX)
				minX = c.x;
			if(c.y > maxY)
				maxY = c.y;
			else if(c.y < minY)
				minY = c.y;
			if(c.z > maxZ)
				maxZ = c.z;
			else if(c.z < minZ)
				minZ = c.z;
		}

		// create map of each coordinate with lava or air
		HashMap<String, Integer> map = new HashMap<String, Integer>();

		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z <= maxZ; z++) {
					// 0 = air, 1 = lava
					if(containsCube(cubes, x, y, z))
						map.put("" + x + "," + y + "," + z, 1);
					else
						map.put("" + x + "," + y + "," + z, 0);
				}
			}
		}

		// create map with all coordinates ouf air outside of lava
		HashMap<String, Boolean> outerAir = new HashMap<String, Boolean>();

		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				int z = minZ;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}
		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				int z = maxZ;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}
		for(int x = minX; x <= maxX; x++) {
			for(int z = minZ; z <= maxZ; z++) {
				int y = minY;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}
		for(int x = minX; x <= maxX; x++) {
			for(int z = minZ; z <= maxZ; z++) {
				int y = maxY;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}
		for(int y = minY; y <= maxY; y++) {
			for(int z = minZ; z <= maxZ; z++) {
				int x = minX;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}
		for(int y = minY; y <= maxY; y++) {
			for(int z = minZ; z <= maxZ; z++) {
				int x = maxX;
				if(map.get("" + x + "," + y + "," + z) == 0)
					findAdjacentAir(x, y, z, map, outerAir);
			}
		}

		// create map of coordinates of air inside of lava
		ArrayList<Cube> innerAir = new ArrayList<Cube>();

		for(int x = minX; x <= maxX; x++) {
			for(int y = minY; y <= maxY; y++) {
				for(int z = minZ; z < maxZ; z++) {
					if(outerAir.get("" + x + "," + y + "," + z) != null)
						continue;
					if(map.get("" + x + "," + y + "," + z) == 0)
						innerAir.add(new Cube(x, y, z));
				}
			}
		}

		// calculate surface area of air cubes, like in part one (but for air)
		int airSides = innerAir.size()*6;
		for(int i = 0; i < innerAir.size(); i++) {
			for(int j = i + 1; j < innerAir.size(); j++) {
				Cube c1 = innerAir.get(i);
				Cube c2 = innerAir.get(j);
				if(c1.x + 1 == c2.x && c1.y == c2.y && c1.z == c2.z)
					airSides -= 2;
				else if(c1.x - 1 == c2.x && c1.y == c2.y && c1.z == c2.z)
					airSides -= 2;
				else if(c1.x == c2.x && c1.y + 1 == c2.y && c1.z == c2.z)
					airSides -= 2;
				else if(c1.x == c2.x && c1.y - 1 == c2.y && c1.z == c2.z)
					airSides -= 2;
				else if(c1.x == c2.x && c1.y == c2.y && c1.z + 1 == c2.z)
					airSides -= 2;
				else if(c1.x == c2.x && c1.y == c2.y && c1.z - 1 == c2.z)
					airSides -= 2;
			}
		}
		System.out.println(partOneAnswer - airSides);
	}

	private ArrayList<Cube> parseInput(ArrayList<String> input) {
		ArrayList<Cube> cubes = new ArrayList<Cube>();
		for(String line : input) {
			cubes.add(new Cube(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1]), Integer.parseInt(line.split(",")[2])));
		}
		return cubes;
	}

	private boolean containsCube(ArrayList<Cube> cubes, int x, int y, int z) {
		for(Cube c : cubes) {
			if(c.x == x && c.y == y && c.z == z)
				return true;
		}
		return false;
	}

	private void findAdjacentAir(int x, int y, int z, HashMap<String, Integer> map, HashMap<String, Boolean> outerAir) {
		if(x > maxX || x < minX || y > maxY || y < minY || z > maxZ || z < minZ)
			return;
		if(outerAir.get("" + x + "," + y + "," + z) != null)
			return;
		if(map.get("" + x + "," + y + "," + z) == 1)
			return;
		outerAir.put("" + x + "," + y + "," + z, true);
		findAdjacentAir(x + 1, y, z, map, outerAir);
		findAdjacentAir(x - 1, y, z, map, outerAir);
		findAdjacentAir(x, y + 1, z, map, outerAir);
		findAdjacentAir(x, y - 1, z, map, outerAir);
		findAdjacentAir(x, y, z + 1, map, outerAir);
		findAdjacentAir(x, y, z - 1, map, outerAir);
	}

	private class Cube {
		public int x, y, z;
		public Cube(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
	}
}
