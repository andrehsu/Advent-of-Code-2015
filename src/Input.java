import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by andre on 12/12/2016.
 */
public class Input {
	private Input() {
		
	}
	
	public static List<String> readAllLines(String fileURL) {
		try {
			return Collections.unmodifiableList(Files.readAllLines(Paths.get(fileURL)));
		} catch (IOException e) {
			System.err.printf("Exception occurred when opening \"%s\", exiting%n", fileURL);
			System.exit(1);
			throw new RuntimeException(e);
		}
	}
	
	public static String readFirstLine(String fileURL) {
		return readAllLines(fileURL).get(0);
	}
}
