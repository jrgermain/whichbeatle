import java.util.LinkedList;
import java.util.Scanner;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WhichBeatle {
	private static boolean findWriter, findSinger, findAlbum = false;
	private static LinkedList<String> queries = new LinkedList<String>();

	public static void main(String[] args) throws SQLException {
		// If the user provided arguments, use them as input. If no arguments have been given, read from stdin.
		String[] input = args.length == 0 ? readIn() : args;

		// Read the arguments and strip out any flags. Save the rest as the search phrase.
		String searchKey = parseInput(input);

		// Perform the search on the database
		search(searchKey);
	}

	private static String parseInput(String[] input) {
		// Initialize the search key to an empty string
		StringBuilder searchKey = new StringBuilder();

		// Process flags and build search string
		for (String s : input) {
			// The help flag calls the displayUsage function and quits the application. This will skip all other flags.
			if (s.equals("--help") || s.equals("-h")) {
				displayUsage();
				System.exit(0);
			}

			/* The rest of the flags below can be combined -- e.g. the user can search
			 * for both the writer and the album the song appeared on using "-wa". Flags
			 * starting with a single '-' can be combined arbitrarily, but flags starting
			 * with "--" must be separated. This is a standard convention in unix.
			 * (-wa == -aw == -a -w)(--singer --album is valid, --singeralbum is invalid).
			 * If the input isn't a valid flag, then add it to our search string.
			 */
			if (s.length() > 1 && s.charAt(0) == '-' && s.charAt(1) != '-') {
				if (s.contains("w")) {
					findWriter = true;
					queries.add("Composer");
				}
				if (s.contains("s")) {
					findSinger = true;
					queries.add("Singer");
				}
				if (s.contains("a")) {
					findAlbum = true;
					queries.add("Album");
				}
			} else if (s.length() > 2 && s.substring(0, 2).equals("--")) {
				if (s.equals("--wrote")) {
					findWriter = true;
					queries.add("Composer");
				} else if (s.equals("--sang")) {
					findSinger = true;
					queries.add("Singer");
				} else if (s.equals("--album")) {
					findAlbum = true;
					queries.add("Album");
				}
			} else if (!s.equalsIgnoreCase("whichbeatle")) {
				/* If the user types the program name (might happen in interactive mode after no arguments), ignore it.
				 * When adding to search, pad the words with wildcards to ignore irregular spacing and punctuation.
				 */
				searchKey.append(s);
				searchKey.append('%');
			}
		}

		// If the user hasn't specified which information they want, show all
		if (!(findWriter || findSinger || findAlbum)) {
			findWriter = findSinger = findAlbum = true;
			queries.add("Composer");
			queries.add("Singer");
			queries.add("Album");
		}

		// If our search is empty, then show an error
		if (searchKey.length() == 0) {
			System.err.println("Error: no search criteria specified");
			displayUsage();
			System.exit(1);
		}

		return searchKey.toString();
	}

	private static void displayUsage() {
		System.out.println("Usage: whichbeatle [option] [songname]");
		System.out.println("Option");
		System.out.println("  -w, --wrote\t display who wrote the song");
		System.out.println("  -s, --sang \t display who sang the song");
		System.out.println("  -a, --album\t display the album on which the song first appeared");
	}

	private static String[] readIn() {
		displayUsage();
		Scanner stdin = new Scanner(System.in);
		String input = stdin.nextLine();
		stdin.close();
		return input.split(" ");
	}

	private static void search(String key) throws SQLException {
		// Turn list of queries into a string, then remove brackets and whitespace using regex
		String q = queries.toString();
		q = q.replaceAll("\\s|\\[|]", "");

		// Sanitize input so apostrophe doesn't break SQL statement
		key = key.replace('\'','_');

		/* Build the query for the database. We're using SQLite's 'LIKE' so we can use the wildcards we added in
		 * parseInput. This gives the user a little more flexibility; the user can find "Anna (Go to Him)" without an
		 * exact match by typing "Anna Go to Him", "Anna  (Go to  Him)", or even just "Anna".
		 */
		String query = "SELECT Song," + q + " FROM beatlesdb WHERE Song LIKE '" + key + "';";

		// Search the database
		Statement stmt = null;
		try {
			Connection conn = DriverManager.getConnection("jdbc:sqlite:beatles.db");
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);

			// If our ResultSet's cursor is not pointing to the first row, we know the set is empty
			if (!rs.isBeforeFirst()) {
				System.out.println("Error: no results found");
				System.exit(2);
			}

			// For every result our query returns:
			while (rs.next()) {
				// Print all the fields we wanted to display
				for (int i = 0; i < queries.size(); i++) {
					System.out.println(queries.get(i) + ": " + rs.getString(i+2)); // Skip over "Song"
				}
				System.out.println();
			}
		} catch (SQLException e) {
			System.err.println("SQLException: " + e.getMessage());
		} finally {
			if (stmt != null) stmt.close();
		}
	}
}