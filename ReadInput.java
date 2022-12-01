import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class ReadInput {
    public static ArrayList<String> read(String input) {
        ArrayList<String> list = new ArrayList<String>();
        try {
			BufferedReader br = new BufferedReader(new FileReader(input));
			String in;
			while ((in = br.readLine()) != null) {
				list.add(in);
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
        return list;
    }
}
