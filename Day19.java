import java.util.ArrayList;

public class Day19 {
	public static void main(String[] args) {
		new Day19();
	}

	public Day19() {
		ArrayList<String> input = ReadInput.read("res/input19.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		ArrayList<Blueprint> blueprints = parseInput(input);
		int res = 0;
		this.totalTime = 24;
		for(int i = 0; i < blueprints.size(); i++) {
			this.maxValue = 0;
			System.out.println("" + (i + 1) + "/" + blueprints.size());
			res += (i + 1) * calculateMaxGeodes(blueprints.get(i), 1, 0, 0, 0, 0, 1, 0, 0, 0);
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		ArrayList<Blueprint> blueprints = parseInput(input);
		int res = 1;
		this.totalTime = 32;
		for(int i = 0; i < 3; i++) {
			this.maxValue = 0;
			System.out.println("" + (i + 1) + "/" + blueprints.size());
			res *= calculateMaxGeodes(blueprints.get(i), 1, 0, 0, 0, 0, 1, 0, 0, 0);
		}
		System.out.println(res);
	}

	private int maxValue;
	private int totalTime;

	private int calculateMaxGeodes(Blueprint blueprint, int minute, int ore, int clay, int obsidian, int geode, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots) {
		int initialOreAmount = ore;
		int initialClayAmount = clay;
		int initialObsidianAmount = obsidian;
		ore += oreRobots;
		clay += clayRobots;
		obsidian += obsidianRobots;
		geode += geodeRobots;

		if(minute == this.totalTime) {
			if(geode > maxValue)
				maxValue = geode;
			return geode;
		}

		int minutesLeft = this.totalTime - minute;
		if(geode + minutesLeft * geodeRobots + ((minutesLeft * (minutesLeft + 1)) / 2) < maxValue)
			return Integer.MIN_VALUE;

		int route1 = -1;
		// enough to build geode robot
		if(initialOreAmount >= blueprint.geodeRobotOreCost && initialObsidianAmount >= blueprint.geodeRobotObsidianCost)
			route1 = calculateMaxGeodes(blueprint, minute + 1, ore - blueprint.geodeRobotOreCost, clay, obsidian - blueprint.geodeRobotObsidianCost, geode, oreRobots, clayRobots, obsidianRobots, geodeRobots + 1);

		int route2 = -1;
		// enough to build obsidian robot
		if(initialOreAmount >= blueprint.obsidianRobotOreCost && initialClayAmount >= blueprint.obsidianRobotClayCost)
			route2 = calculateMaxGeodes(blueprint, minute + 1, ore - blueprint.obsidianRobotOreCost, clay - blueprint.obsidianRobotClayCost, obsidian, geode, oreRobots, clayRobots, obsidianRobots + 1, geodeRobots);

		int route3 = -1, route4 = -1, route5 = -1;
		// build no robot
		if(initialOreAmount <= 4)
			route3 = calculateMaxGeodes(blueprint, minute + 1, ore, clay, obsidian, geode, oreRobots, clayRobots, obsidianRobots, geodeRobots);

		// build ore robot
		if(initialOreAmount >= blueprint.oreRobotOreCost)
			route4 = calculateMaxGeodes(blueprint, minute + 1, ore - blueprint.oreRobotOreCost, clay, obsidian, geode, oreRobots + 1, clayRobots, obsidianRobots, geodeRobots);

		// build clay robot
		if(initialOreAmount >= blueprint.clayRobotOreCost)
			route5 = calculateMaxGeodes(blueprint, minute + 1, ore - blueprint.clayRobotOreCost, clay, obsidian, geode, oreRobots, clayRobots + 1, obsidianRobots, geodeRobots);
		
		return Math.max(route1, Math.max(route2, Math.max(route3, Math.max(route4, route5))));
	}

	private ArrayList<Blueprint> parseInput(ArrayList<String> input) {
		ArrayList<Blueprint> res = new ArrayList<Blueprint>();
		for(String line : input) {
			String[] parts = line.split("\\.");
			int oreRobotOreCost = Integer.parseInt(parts[0].split(" ")[parts[0].split(" ").length - 2]);
			int clayRobotOreCost = Integer.parseInt(parts[1].split(" ")[parts[1].split(" ").length - 2]);
			int obsidianRobotOreCost = Integer.parseInt(parts[2].split(" ")[parts[2].split(" ").length - 5]);
			int obsidianRobotClayCost = Integer.parseInt(parts[2].split(" ")[parts[2].split(" ").length - 2]);
			int geodeRobotOreCost = Integer.parseInt(parts[3].split(" ")[parts[3].split(" ").length - 5]);
			int geodeRobotObsidianCost = Integer.parseInt(parts[3].split(" ")[parts[3].split(" ").length - 2]);
			res.add(new Blueprint(oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost));
		}

		return res;
	}

	private class Blueprint {
		public int oreRobotOreCost;
		public int clayRobotOreCost;
		public int obsidianRobotOreCost, obsidianRobotClayCost;
		public int geodeRobotOreCost, geodeRobotObsidianCost;

		public Blueprint(int oreRobotOreCost, int clayRobotOreCost, int obsidianRobotOreCost, int obsidianRobotClayCost, int geodeRobotOreCost, int geodeRobotObsidianCost) {
			this.oreRobotOreCost = oreRobotOreCost;
			this.clayRobotOreCost = clayRobotOreCost;
			this.obsidianRobotOreCost = obsidianRobotOreCost;
			this.obsidianRobotClayCost = obsidianRobotClayCost;
			this.geodeRobotOreCost = geodeRobotOreCost;
			this.geodeRobotObsidianCost = geodeRobotObsidianCost;
		}
	}
}
