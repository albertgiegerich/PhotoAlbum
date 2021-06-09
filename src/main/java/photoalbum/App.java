package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.net.http.HttpClient;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

public class App {
    private PhotosApiClient apiClient;

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
        try (Scanner userInput = new Scanner(System.in)) {
            boolean commandToQuitHasBeenIssued = false;
            while (!commandToQuitHasBeenIssued) {
                String input = userInput.nextLine();

                commandToQuitHasBeenIssued = processUserInput(input);
            }
        }
    }

    public boolean processUserInput(String input) {
        if (input.equals("quit")) {
            return true;
        }

        String[] commands = input.split(" ");
        try {
            apiClient.getAlbum(Integer.parseInt(commands[1]));
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            System.out.println("An error occurred while attempting to retrieve photo data. Please check your internet connection and try again.");
        }

        return false;
    }
}
