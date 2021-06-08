package photoalbum;

import java.util.Scanner;

public class App {
    private PhotosApiClient apiClient;

    public static void main(String[] args) {
        System.out.println("Hello world!");
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
        apiClient.getAlbum(Integer.parseInt(commands[1]));

        return false;
    }
}
