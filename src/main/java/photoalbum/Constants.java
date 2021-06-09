package photoalbum;

public class Constants {

    private Constants() {
        // not instantiable
    }

    public static final String HELP_COMMAND = "help";
    public static final String QUIT_COMMAND = "quit";
    public static final String PHOTO_ALBUM_COMMAND = "photo-album";
    public static final String PHOTO_COMMAND = "photo";


    public static final String INVALID_COMMAND_MESSAGE = "You have entered and invalid command. Please try again.";
    public static final String HELP_MESSAGE =
            "Welcome to the album management CLI. Please enter one of the following commands:\n" +
            "photo-album [id] - display the photos in the album with albumId [id]\n" +
            "photo [id] - display the details of the photo with the id [id]\n" +
            "help - display this message again\n" +
            "quit - exit the application\n";


}
