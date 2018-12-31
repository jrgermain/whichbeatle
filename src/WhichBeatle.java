import java.util.LinkedList;

public class WhichBeatle {
    static boolean findWriter = false;
    static boolean findSinger = false;
	static boolean findAlbum = false;
	static LinkedList<String> queries = new LinkedList<String>();
	
    public static void main(String[] args) {
		// Parse the input and strip out any flags. Save the rest as the search phrase.
		String searchKey = parseInput(args);
		// Perform a search for the specified key
		search(searchKey);
    }

    private static String parseInput(String[] input) {
		// Initialize the search key to an empty string
        String searchKey = "";
		// Process flags and build search string
		for (String s : input) {
			/* The help flag calls the displayUsage function which will also quit
			 * the application. This means calling help will skip all other flags 
			 * to present the user with usage instructions.
			 */
			if (s.equals("--help") || s.equals("-h")) {
                displayUsage(0);
			}
			
			/* The rest of the flags below can be stacked -- e.g. the user can search 
			 * for both the writer and the album the song appeared on using "-wa".
			 * Flags starting with a single '-' can be combined arbitrarily, but flags
			 * starting with "--" must be separated. (-wa == -aw == -a -w) (--singer 
			 * --album is valid, but --singeralbum is invalid) If the input isn't a 
			 * valid flag, then add it to our search string.
			 */
			if (s.charAt(0) == '-' && s.charAt(1) != '-') {
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
			} else if (s.substring(0,2).equals("--")) {
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
			} else {
				/* Put wildcards in between words and one at the end.
				 * e.g. "Anna Go to Him" and "Anna" will both map to "Anna (Go to Him)"
				 */
				searchKey += s + "%";
			}             
		}
		// If the user hasn't specified which information they want, show all
		if (!(findWriter || findSinger || findAlbum)) {
			findWriter = true;
			findSinger = true;
			findAlbum = true;
		}

		// If our search is empty, then show an error
		if (searchKey.length() == 0) {
            System.err.println("Error: no search criteria specified");
            displayUsage(1);
		}

        return searchKey;
    }

    private static void displayUsage(int exitCode) {
		System.out.println("Usage: whichbeatle [option] [songname]");
		System.out.println("Option");
		System.out.println("  -w, --wrote\t display who wrote the song");
		System.out.println("  -s, --sang \t display who sang the song");
		System.out.println("  -a, --album\t display the album on which the song first appeared");
		// Quit the program, giving exit code 1 for error and 0 for success
        System.exit(exitCode);
    }

    private static void search(String key) {
		// Turn list of queries into a string, then remove brackets and whitespace using regex
		String q = queries.toString();
		q = q.replaceAll("\\s|\\[|\\]","");

		// Build query for database
		String query = "SELECT " + q + " FROM beatlesdb WHERE Name LIKE '" + key + "'";

		// Debug: print out query instead of running it (for now)
		System.out.println(query);

		//TODO: Load database and perform the search
		
    }
}