package photoalbum;



import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class AppTests {

    @Test
    public void inputPhotoAlbum1Test() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 1\nquit");

        App app = new App(apiClient);
        app.run();

        try {
            verify(apiClient, times(1)).getAlbum(eq(1));
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: "+e.getClass().getName());
        }
    }

    @Test
    public void inputPhotoAlbum2Test() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 2\nquit");


        App app = new App(apiClient);
        app.run();
        try {
            verify(apiClient, times(1)).getAlbum(eq(2));
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: "+e.getClass().getName());
        }
    }
    @Test
    public void inputMultiplePhotoAlbumCommandsTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 1\nphoto-album 2\nphoto-album 3\nquit");

        App app = new App(apiClient);
        app.run();

        try {
            verify(apiClient, times(1)).getAlbum(eq(1));
            verify(apiClient, times(1)).getAlbum(eq(2));
            verify(apiClient, times(1)).getAlbum(eq(3));
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: "+e.getClass().getName());
        }
    }

    @Test
    public void earlyQuitStopsProcessingTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("quit\nphoto-album 1");

        App app = new App(apiClient);
        app.run();

        try {
            verify(apiClient, never()).getAlbum(anyInt());
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: "+e.getClass().getName());
        }
    }

    private void setSystemInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }
}
