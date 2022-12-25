import java.util.ArrayList;

public class Day25 {
	public static void main(String[] args) {
		new Day25();
	}

	public Day25() {
		ArrayList<String> input = ReadInput.read("res/input25.txt");
		partOne(input);
		partTwo(input);
	}

	private void partOne(ArrayList<String> input) {
		long res = 0;
		for(String line : input)
			res += snafuToDecimal(line);
		System.out.println(decimalToSnafu(res));
	}

	private void partTwo(ArrayList<String> input) {
		// N/A
	}

	private long snafuToDecimal(String snafu) {
		long res = 0;
		for(int i = 0; i < snafu.length(); i++) {
			char c = snafu.charAt(snafu.length() - 1 - i);
			long m = 0;
			if(c == '-')
				m = -1;
			else if(c == '=')
				m = -2;
			else
				m = Long.parseLong("" + c);
			res += m * Math.pow(5, i);
		}
		return res;
	}

	private String decimalToSnafu(long decimal) {
		String res = "";
		int digits = 0;
		while(2 * (long) Math.pow(5, digits - 1) < decimal)
			digits++;
		
		res = calculateSnafu("", digits - 1, decimal);
		
		return res;
	}

	private String calculateSnafu(String currentSnafu, int index, long targetDecimal) {
		if(index == -1) {
			if(snafuToDecimal(currentSnafu) == targetDecimal)
				return currentSnafu;
			else
				return "";
		}

		StringBuilder sb = new StringBuilder();
		sb.append(currentSnafu);
		for(int i = 0; i <= index; i++)
			sb.append("0");
		long currentSnafuDecimal = snafuToDecimal(sb.toString());
		
		String s1, s2, s3;
		if(currentSnafuDecimal > targetDecimal) {
			long maxNegativeNumberLeft = 0;
			for(int i = 0; i <= index; i++)
				maxNegativeNumberLeft += Math.pow(5, i) * (-2);
			if(currentSnafuDecimal + maxNegativeNumberLeft > targetDecimal)
				return "";
			s1 = calculateSnafu(new StringBuilder().append(currentSnafu).append("=").toString(), index - 1, targetDecimal);
			s2 = calculateSnafu(new StringBuilder().append(currentSnafu).append("-").toString(), index - 1, targetDecimal);
			s3 = calculateSnafu(new StringBuilder().append(currentSnafu).append("0").toString(), index - 1, targetDecimal);
		}
		else {
			long maxPositiveNumberLeft = 0;
			for(int i = 0; i <= index; i++)
				maxPositiveNumberLeft += Math.pow(5, i) * 2;
			if(currentSnafuDecimal + maxPositiveNumberLeft < targetDecimal)
				return "";
			s1 = calculateSnafu(new StringBuilder().append(currentSnafu).append("0").toString(), index - 1, targetDecimal);
			s2 = calculateSnafu(new StringBuilder().append(currentSnafu).append("1").toString(), index - 1, targetDecimal);
			s3 = calculateSnafu(new StringBuilder().append(currentSnafu).append("2").toString(), index - 1, targetDecimal);
		}

		if(s1.length() > 0)
			return s1;
		if(s2.length() > 0)
			return s2;
		if(s3.length() > 0)
			return s3;
		return "";

	}
}
