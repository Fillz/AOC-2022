import java.util.ArrayList;

public class Day15 {
	public static void main(String[] args) {
		new Day15();
	}

	public Day15() {
		ArrayList<String> input = ReadInput.read("res/input15.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Sensor> sensors = parseInput(input);
		int minX = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		for(Sensor s : sensors) {
			if(s.x - s.beaconDist < minX)
				minX = s.x - s.beaconDist;
			if(s.x + s.beaconDist > maxX)
				maxX = s.x + s.beaconDist;
		}

		int coveredCoordinates = 0;
		int y = 10;
		for(int x = minX; x <= maxX; x++) {
			if(containsBeaconOrSensor(sensors, x, y))
				continue;
			for(Sensor s : sensors) {
				if(getManhattanDistance(x, y, s.x, s.y) <= s.beaconDist) {
					coveredCoordinates++;
					break;
				}
			}
		}
		System.out.println(coveredCoordinates);
	}

	private void partTwo(ArrayList<String> input) {
		ArrayList<Sensor> sensors = parseInput(input);
		for(int y = 0; y <= 4000000; y++) {	
			for(int x = 0; x <= 4000000; x++) {
				boolean reachableBySensor = false;
				for(Sensor s : sensors) {
					if(getManhattanDistance(x, y, s.x, s.y) <= s.beaconDist) {
						x = s.x + s.beaconDist - Math.abs(y - s.y);
						reachableBySensor = true;
						break;
					}

				}
				if(!reachableBySensor) {
					long ans = 4000000 * ((long) x) + y;
					System.out.println(ans);
				}
			}
		}
	}

	private int getManhattanDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

	private boolean containsBeaconOrSensor(ArrayList<Sensor> sensors, int x, int y) {
		for(Sensor s : sensors) {
			if((s.x == x && s.y == y) || (s.beaconX == x && s.beaconY == y))
				return true;
		}
		return false;
	}

	private ArrayList<Sensor> parseInput(ArrayList<String> input) {
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		for(String line : input) {
			String part1 = line.split(": ")[0];
			String part2 = line.split(": ")[1];
			int sensorX = Integer.parseInt(part1.split("x=")[1].split(",")[0]);
			int sensorY = Integer.parseInt(part1.split("y=")[1]);
			int beaconX = Integer.parseInt(part2.split("x=")[1].split(",")[0]);
			int beaconY = Integer.parseInt(part2.split("y=")[1]);
			sensors.add(new Sensor(sensorX, sensorY, beaconX, beaconY));
		}
		return sensors;
	}

	private class Sensor {
		public int x, y, beaconX, beaconY, beaconDist;
		public Sensor(int x, int y, int beaconX, int beaconY) {
			this.x = x;
			this.y = y;
			this.beaconX = beaconX;
			this.beaconY = beaconY;
			this.beaconDist = Math.abs(x - beaconX) + Math.abs(y - beaconY);
		}
	}
}
