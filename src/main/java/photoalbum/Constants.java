package photoalbum;

public class Constants {

    private Constants() {
        // not instantiable
    }

    public static final String INVALID_COMMAND_MESSAGE = "Error: You have entered an invalid command. Please try again.";
    public static final String HELP_MESSAGE =
            "Welcome to the album management CLI. Enter \"help\" to see this message again at any time.\n" +
            "Please enter one of the following commands:\n\n" +
            "Command                   Description                                               Output Format\n" +
            "----------------------------------------------------------------------------------------------------------------------\n" +
            "list-albums               Display all albums                                        [album ID]: [album description]\n" +
            "photo-album [album ID]    Display the photos in the album with the given ID         [photo ID]: [photo description]\n" +
            "photo [photo ID]          Display the details of the photo with the given ID        [photo ID]: [photo title]\n" +
            "                                                                                        Album ID: [album ID]\n" +
            "                                                                                        URL: [photo URL]\n" +
            "                                                                                        Thumbnail: [photo thumblinail URL]\n" +
            "help                      Display this message again\n" +
            "quit                      Exit the application\n";

    public static final String API_URL = "https://jsonplaceholder.typicode.com/";
}
