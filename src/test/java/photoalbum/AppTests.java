package photoalbum;



import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AppTests {

    @Test
    public void inputPhotoAlbum1Test() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 1\nquit");

        App app = new App(apiClient);
        app.run();

        verify(apiClient, times(1)).getAlbum(eq(1));
    }

    @Test
    public void inputPhotoAlbum2Test() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 2\nquit");


        App app = new App(apiClient);
        app.run();

        verify(apiClient, times(1)).getAlbum(eq(2));
    }
    @Test
    public void inputMultiplePhotoAlbumCommandsTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 1\nphoto-album 2\nphoto-album 3\nquit");

        App app = new App(apiClient);
        app.run();

        verify(apiClient, times(1)).getAlbum(eq(1));
        verify(apiClient, times(1)).getAlbum(eq(2));
        verify(apiClient, times(1)).getAlbum(eq(3));
    }

    @Test
    public void earlyQuitStopsProcessingTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("quit\nphoto-album 1");

        App app = new App(apiClient);
        app.run();

        verify(apiClient, never()).getAlbum(anyInt());
    }

    private void setSystemInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}
