package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {
    private final PhotosApiClient apiClient;

    public static void main(String[] args) {
        PhotosApiClient apiClient = new PhotosApiClient(Constants.API_URL);
        App app = new App(apiClient);

        app.run();
    }

    public App(PhotosApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void run() {
        System.out.println("\n"+Constants.HELP_MESSAGE);
        try (Scanner userInput = new Scanner(System.in)) {
            boolean commandToQuitHasBeenIssued = false;
            while (!commandToQuitHasBeenIssued) {
                try {
                    System.out.print("> ");
                    String input = userInput.nextLine();

                    commandToQuitHasBeenIssued = processUserInput(input.toLowerCase());
                } catch (RuntimeException e) {
                    System.out.println("An unexpected error has occurred. Please try again.");
                }
            }
        }
    }

    public boolean processUserInput(String input) {
        final boolean exitProgram = true;
        final boolean continueExecution = false;

        String[] commandAndValue = input.split("\\s+");

        if (commandAndValue.length == 0 || commandAndValue[0].trim().length() == 0) {
            return continueExecution;
        }

        Command command = Command.fromString(commandAndValue[0]);

        if (command != Command.INVALID && command.length != commandAndValue.length) {
            System.out.println("Error: there should be " + (command.length-1) + " argument(s) for the "+command.command+" command.");
            return continueExecution;
        }

        try {
            switch (command) {
                case LIST_ALBUMS:
                    handleListAlbumsCommand();
                    break;
                case PHOTO_ALBUM:
                    handlePhotoAlbumCommand(commandAndValue[1]);
                    break;
                case PHOTO:
                    handlePhotoCommand(commandAndValue[1]);
                    break;
                case HELP:
                    System.out.println(Constants.HELP_MESSAGE);
                    break;
                case QUIT:
                    System.out.println("Exiting program.");
                    return exitProgram;
                default:
                    System.out.println(Constants.INVALID_COMMAND_MESSAGE);
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            System.out.println("An error occurred while attempting to retrieve photo data. Please check your internet connection and try again.");
        }

        return continueExecution;
    }

    private void handleListAlbumsCommand()  throws ExecutionException, InterruptedException, JsonProcessingException {
        List<Album> albums = apiClient.listAlbums();

        for (Album album : albums) {
            System.out.println("["+album.getId()+"] " + album.getTitle());
        }
    }

    private void handlePhotoCommand(String id) throws ExecutionException, InterruptedException, JsonProcessingException {

        if (!validateId(id, "photo")) {
            return;
        }

        Photo photo = apiClient.getPhoto(Long.parseLong(id));

        if (photo == null) {
            System.out.println("No photo exists with ID "+id+". Please try again.");
        } else {
            System.out.println("[" + photo.getId() + "] " + photo.getTitle());
            System.out.println("\tAlbum ID: " + photo.getAlbumId());
            System.out.println("\tURL: " + photo.getUrl());
            System.out.println("\tThumbnail: " + photo.getThumbnailUrl());
        }
    }

    private void handlePhotoAlbumCommand(String albumId) throws ExecutionException, InterruptedException, JsonProcessingException {
        if (!validateId(albumId, "album")) {
            return;
        }
        List<Photo> album = apiClient.getAlbum(Long.parseLong(albumId));
        if (album.size() == 0) {
            System.out.println("The album with ID "+albumId+ " does not exist or contains no photos. Please try again.");
        }
        for (Photo photo : album) {
            System.out.println("["+photo.getId()+"] "+photo.getTitle());
        }
    }

    private boolean validateId(String id, String idName) {
        if (!isInteger(id)) {
            System.out.println("The "+idName+" ID must be an integer.");
            return false;
        }
        if (id.length() > Constants.MAX_ID_LENGTH) {
            System.out.println("IDs longer than "+Constants.MAX_ID_LENGTH+" digits are not supported. Please try again.");
            return false;
        }
        return true;
    }

    private boolean isInteger(String text) {
        return text.matches("[1-9]+\\d*");
    }
}
