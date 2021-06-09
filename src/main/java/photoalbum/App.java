package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {
    private final PhotosApiClient apiClient;

    public static void main(String[] args) {
        String apiUrl = "https://jsonplaceholder.typicode.com/photos";
        PhotosApiClient apiClient = new PhotosApiClient(apiUrl);
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
                String input = userInput.nextLine();

                commandToQuitHasBeenIssued = processUserInput(input.toLowerCase());
            }
        }
    }

    public boolean processUserInput(String input) {
        if (input.equals(Constants.QUIT_COMMAND)) {
            System.out.println("Exiting application.");
            return true;
        }

        String[] commandAndValue = input.split(" ");

        if (commandAndValue.length > 2) {
            System.out.println(Constants.INVALID_COMMAND_MESSAGE);
            return false;
        }


        if (commandAndValue.length == 0 || commandAndValue[0].trim().length() == 0) {
            // if the user only entered whitespace, don't send any message, just continue execution.
            return false;
        }

        String command = commandAndValue[0];

        switch (command) {
            case Constants.PHOTO_ALBUM_COMMAND:
                handlePhotoAlbumCommand(commandAndValue[1]);
                break;
            case Constants.PHOTO_COMMAND:
                handlePhotoCommand(commandAndValue[1]);
                break;
            case Constants.HELP_COMMAND:
                System.out.println(Constants.HELP_MESSAGE);
                break;
            default:
                System.out.println(Constants.INVALID_COMMAND_MESSAGE);
        }

        return false;
    }

    private void handlePhotoCommand(String id) {
        if (!id.matches("\\d+")) {
            System.out.println("The photo ID must be an integer.");
            return;
        }

        try {
            Photo photo = apiClient.getPhoto(Integer.parseInt(id));

            System.out.println("["+photo.getId()+"] "+photo.getTitle());
            System.out.println("\tAlbum ID: "+photo.getAlbumId());
            System.out.println("\tURL: "+photo.getUrl());
            System.out.println("\tThumbnail: "+photo.getThumbnailUrl());
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            System.out.println("An error occurred while attempting to retrieve photo data. Please check your internet connection and try again.");
        }
    }

    private void handlePhotoAlbumCommand(String albumId) {
        if (!albumId.matches("\\d+")) {
            System.out.println("The album ID must be an integer.");
            return;
        }

        try {
            List<Photo> album = apiClient.getAlbum(Integer.parseInt(albumId));
            for (Photo photo : album) {
                System.out.println("["+photo.getId()+"] "+photo.getTitle());
            }
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            System.out.println("An error occurred while attempting to retrieve photo data. Please check your internet connection and try again.");
        }
    }
}
