import java.util.ArrayList;
import java.util.HashMap;

public class Day9 {
	public static void main(String[] args) {
		new Day9();
	}

	public Day9() {
		ArrayList<String> input = ReadInput.read("res/input9.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		HashMap<String, Boolean> tailVisited = new HashMap<String, Boolean>();
		int res = 0;
		Coord h = new Coord();
		Coord t = new Coord();
		for(String line : input) {
			char instruction = line.split(" ")[0].charAt(0);
			int dist = Integer.parseInt(line.split(" ")[1]);
			for(int i = 0; i < dist; i++) {
				moveHead(h, instruction);
				moveTail(h, t, instruction);
				if(tailVisited.get("" + t.x + "," + t.y) == null)
					res++;
				tailVisited.put("" + t.x + "," + t.y, true);
			}
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		HashMap<String, Boolean> tailVisited = new HashMap<String, Boolean>();
		int res = 0;
		ArrayList<Coord> knots = new ArrayList<Coord>();
		for(int i = 0; i < 10; i++)
			knots.add(new Coord());
		for(String line : input) {
			char instruction = line.split(" ")[0].charAt(0);
			int dist = Integer.parseInt(line.split(" ")[1]);
			for(int counter = 0; counter < dist; counter++) {
				moveHead(knots.get(0), instruction);
				for(int i = 0; i < knots.size() - 1; i++) {
					moveTail(knots.get(i), knots.get(i + 1), instruction);
					if(i == knots.size() - 2) {
						Coord t = knots.get(knots.size() - 1);
						if(tailVisited.get("" + t.x + "," + t.y) == null)
							res++;
						tailVisited.put("" + t.x + "," + t.y, true);
					}
				}
			}
		}
		System.out.println(res);
	}

	private void moveHead(Coord h, char instruction) {
		if(instruction == 'U')
			h.y++;
		else if(instruction == 'D')
			h.y--;
		else if(instruction == 'R')
			h.x++;
		else
			h.x--;
	}

	private void moveTail(Coord h, Coord t, char instruction) {
		int xDiff = Math.abs(h.x - t.x);
		int yDiff = Math.abs(h.y - t.y);
		if(xDiff > 1 || yDiff > 1) {
			if(xDiff == 0) {
				t.y = h.y > t.y ? h.y - 1 : h.y + 1;
			}
			else if(yDiff == 0) {
				t.x = h.x > t.x ? h.x - 1 : h.x + 1;
			}
			else if(h.x > t.x && h.y > t.y) {
				t.x++;
				t.y++;
			}
			else if(h.x > t.x && h.y < t.y) {
				t.x++;
				t.y--;
			}
			else if(h.x < t.x && h.y > t.y) {
				t.x--;
				t.y++;
			}
			else {
				t.x--;
				t.y--;
			}
		}
	}

	private class Coord {
		public int x, y;

		public Coord() {
			this.x = 0;
			this.y = 0;
		}
	}

}
