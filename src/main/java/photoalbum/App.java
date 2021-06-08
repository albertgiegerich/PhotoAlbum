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

            String input = userInput.nextLine();
            String[] commands = input.split(" ");

            apiClient.getAlbum(Integer.parseInt(commands[1]));
        }
    }
}
