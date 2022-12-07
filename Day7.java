import java.util.ArrayList;
import java.util.Collections;

public class Day7 {
	public static void main(String[] args) {
		new Day7();
	}

	public Day7() {
		ArrayList<String> input = ReadInput.read("res/input7.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		Dir root = new Dir("/", null);
		initFileSystem(root, input);
		ArrayList<Dir> allDirs = getAllDirs(root);
		
		int res = 0;
		for(Dir d : allDirs) {
			int size = d.getSize();
			if(size <= 100000)
				res += size;
		}
		System.out.println(res);
	}

	private void partTwo(ArrayList<String> input) {
		Dir root = new Dir("/", null);
		initFileSystem(root, input);
		ArrayList<Dir> allDirs = getAllDirs(root);
		
		int currentUnusedSpace = 70000000 - root.getSize();
		ArrayList<Integer> sizes = new ArrayList<Integer>();
		for(Dir d : allDirs) {
			sizes.add(d.getSize());
		}
		Collections.sort(sizes);
		for(int size : sizes) {
			if(size + currentUnusedSpace >= 30000000) {
				System.out.println(size);
				return;
			}
		}
	}

	private ArrayList<Dir> getAllDirs(Dir root) {
		ArrayList<Dir> res = new ArrayList<Dir>();
		res.add(root);
		getAllDirsHelper(root, res);
		return res;
	}

	private void getAllDirsHelper(Dir current, ArrayList<Dir> res) {
		for(File f : current.getFiles()) {
			if(f instanceof Dir) {
				res.add((Dir) f);
				getAllDirsHelper((Dir) f, res);
			}
		}
	}

	private void initFileSystem(Dir root, ArrayList<String> input) {
		Dir currentDir = root;
		int i = 0;
		while(i < input.size()) {
			int nextInstruction = i + 1;
			String line = input.get(i);
			if(line.contains("$ ls")) {
				nextInstruction = findNextInstruction(i + 1, input);
				for(int j = i + 1; j < nextInstruction; j++) {
					String fileLine = input.get(j);
					if(fileLine.split(" ")[0].contains("dir")) {
						currentDir.addFile(new Dir(fileLine.split(" ")[1], currentDir));
					}
					else {
						currentDir.addFile(new PlainFile(Integer.parseInt(fileLine.split(" ")[0]), fileLine.split(" ")[1]));
					}
				}
			}
			else if(line.contains("$ cd")) {
				if(line.contains("/")) {
					currentDir = root;
				}
				else if(line.contains("..")) {
					currentDir = currentDir.getParentDir();
				}
				else {
					String dirname = line.split(" ")[2];
					currentDir = currentDir.getDir(dirname);
				}
			}

			i = nextInstruction;
		}
	}

	private int findNextInstruction(int index, ArrayList<String> input) {
		for(int i = index; i < input.size(); i++) {
			if(input.get(i).contains("$"))
				return i;
		}
		return input.size();
	}

	private interface File {
		public int getSize();
		public String getName();
	}

	private class Dir implements File {

		private ArrayList<File> files;
		private String name;
		private Dir parentDir;

		public Dir(String name, Dir parentDir) {
			this.name = name;
			this.parentDir = parentDir;
			this.files = new ArrayList<File>();
		}

		@Override
		public int getSize() {
			int size = 0;
			for(File f : this.files)
				size += f.getSize();
			return size;
		}

		public ArrayList<File> getFiles() {
			return this.files;
		}

		public void addFile(File f) {
			this.files.add(f);
		}

		@Override
		public String getName() {
			return this.name;
		}

		public Dir getParentDir() {
			return this.parentDir;
		}

		public Dir getDir(String name) {
			for(File f : this.files) {
				if(f.getName().equals(name))
					return (Dir) f;
			}
			return null;
		}
	}

	private class PlainFile implements File {

		private int size;
		private String name;

		public PlainFile(int size, String name) {
			this.size = size;
			this.name = name;
		}

		@Override
		public int getSize() {
			return this.size;
		}

		@Override
		public String getName() {
			return this.name;
		}
	}




}
