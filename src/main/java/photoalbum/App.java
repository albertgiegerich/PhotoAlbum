package photoalbum;

public class App {
    private PhotosApiClient apiClient;

    public static void main(String[] args) {
        System.out.println("Hello world!");
    }

    public App(PhotosApiClient apiClient) {
        this.apiClient = apiClient;
    }

    public void run() {
        apiClient.getAlbum(1);
    }
}
