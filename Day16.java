import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Day16 {
	public static void main(String[] args) {
		new Day16();
	}

	public Day16() {
		ArrayList<String> input = ReadInput.read("res/input16.txt");

		this.map = parseInput(input);
		for(String line : input) {
			String room = line.split(" ")[1];
			for(String relevantRoom : this.relevantRooms) {
				distancesToRelevantRooms.put(room + relevantRoom, calculateShortestDistance(room, relevantRoom));
			}
		}

		partOne(input);
		partTwo(input);
	}

	private HashMap<String, Room> map;
	private HashMap<String, Integer> distancesToRelevantRooms = new HashMap<String, Integer>();
	private ArrayList<String> relevantRooms = new ArrayList<String>();

	private void partOne(ArrayList<String> input) {
		calculateMaximumPressure("AA", 30, 0, this.relevantRooms);
		System.out.println(maxPressure);
	}

	private void partTwo(ArrayList<String> input) {
		this.maxPressure = 0;
		calculateMaximumPressurePart2("AA", 26, "AA", 26, 0, this.relevantRooms);
		System.out.println(maxPressure);
	}

	private int maxPressure = 0;

	private void calculateMaximumPressure(String currentRoom, int minutesLeft, int currentTotalPressureRelease, ArrayList<String> relevantRoomsLeft) {
		if(relevantRoomsLeft.size() == 0) {
			if(currentTotalPressureRelease > this.maxPressure)
				this.maxPressure = currentTotalPressureRelease;
			return;
		}
		for(String relevantRoom : relevantRoomsLeft) {
			int dist = distancesToRelevantRooms.get(currentRoom + relevantRoom);
			if(dist >= minutesLeft - 1) {
				if(currentTotalPressureRelease > this.maxPressure)
					this.maxPressure = currentTotalPressureRelease;
				continue;
			}
			ArrayList<String> newRelevantRoomsLeft = new ArrayList<String>(relevantRoomsLeft);
			newRelevantRoomsLeft.remove(relevantRoom);
			calculateMaximumPressure(relevantRoom, minutesLeft - dist - 1, currentTotalPressureRelease + ((minutesLeft - dist - 1) * this.map.get(relevantRoom).flowRate), newRelevantRoomsLeft);
		}
	}

	private void calculateMaximumPressurePart2(String currentRoomYou, int minutesLeftYou, String currentRoomElephant, int minutesLeftElephant, int currentTotalPressureRelease, ArrayList<String> relevantRoomsLeft) {
		if(relevantRoomsLeft.size() == 0) {
			if(currentTotalPressureRelease > this.maxPressure)
				this.maxPressure = currentTotalPressureRelease;
			return;
		}
		for(String relevantRoomYou : relevantRoomsLeft) {
			for(String relevantRoomElephant : relevantRoomsLeft) {
				if(relevantRoomYou.equals(relevantRoomElephant))
					continue;
				int distYou = distancesToRelevantRooms.get(currentRoomYou + relevantRoomYou);
				int distElephant = distancesToRelevantRooms.get(currentRoomElephant + relevantRoomElephant);
				if(distYou >= minutesLeftYou - 1 && distElephant >= minutesLeftElephant - 1) {
					if(currentTotalPressureRelease > this.maxPressure)
						this.maxPressure = currentTotalPressureRelease;
					continue;
				}
				else if(distYou < minutesLeftYou - 1 && distElephant >= minutesLeftElephant - 1) {
					ArrayList<String> newRelevantRoomsLeft = new ArrayList<String>(relevantRoomsLeft);
					newRelevantRoomsLeft.remove(relevantRoomYou);
					calculateMaximumPressurePart2(relevantRoomYou, minutesLeftYou - distYou - 1, currentRoomElephant, minutesLeftElephant, currentTotalPressureRelease + ((minutesLeftYou - distYou - 1) * this.map.get(relevantRoomYou).flowRate), newRelevantRoomsLeft);
				}
				else if(distYou >= minutesLeftYou - 1 && distElephant < minutesLeftElephant - 1) {
					ArrayList<String> newRelevantRoomsLeft = new ArrayList<String>(relevantRoomsLeft);
					newRelevantRoomsLeft.remove(relevantRoomElephant);
					calculateMaximumPressurePart2(currentRoomYou, minutesLeftYou, relevantRoomElephant, minutesLeftElephant - distElephant - 1, currentTotalPressureRelease + ((minutesLeftElephant - distElephant - 1) * this.map.get(relevantRoomElephant).flowRate), newRelevantRoomsLeft);
				}
				else {
					ArrayList<String> newRelevantRoomsLeft = new ArrayList<String>(relevantRoomsLeft);
					newRelevantRoomsLeft.remove(relevantRoomYou);
					newRelevantRoomsLeft.remove(relevantRoomElephant);
					calculateMaximumPressurePart2(relevantRoomYou, minutesLeftYou - distYou - 1, relevantRoomElephant, minutesLeftElephant - distElephant - 1, currentTotalPressureRelease + ((minutesLeftYou - distYou - 1) * this.map.get(relevantRoomYou).flowRate) + ((minutesLeftElephant - distElephant - 1) * this.map.get(relevantRoomElephant).flowRate), newRelevantRoomsLeft);
				}
			}
		}
	}

	private int calculateShortestDistance(String from, String to) {
		Queue<Pair> queue = new LinkedList<Pair>();
		queue.add(new Pair(from, 0));
		while(queue.size() > 0) {
			Pair current = queue.poll();
			if(current.name.equals(to))
				return current.distance;
			for(String adjacentRoom : this.map.get(current.name).adjacentRooms) {
				queue.add(new Pair(adjacentRoom, current.distance + 1));
			}
		}
		return -1;
	}

	private HashMap<String, Room> parseInput(ArrayList<String> input) {
		HashMap<String, Room> map = new HashMap<String, Room>();
		for(String line : input) {
			String name = line.split(" ")[1];
			String rate = line.split(" ")[4];
			int flowRate = Integer.parseInt(rate.substring(5, rate.length() - 1));
			if(flowRate > 0)
				this.relevantRooms.add(name);
			Room r = new Room(flowRate);
			String[] rooms = line.contains("valves") ? line.split("valves ")[1].split(", ") : line.split("valve ")[1].split(", ");
			for(String room : rooms)
				r.adjacentRooms.add(room);
			map.put(name, r);
		}
		return map;
	}

	private class Room {
		public ArrayList<String> adjacentRooms;
		public int flowRate;
		public Room(int flowRate) {
			adjacentRooms = new ArrayList<String>();
			this.flowRate = flowRate;
		}
	}
	
	private class Pair {
		private String name;
		private int distance;
		public Pair(String name, int distance) {
			this.name = name;
			this.distance = distance;
		}
	}
}
