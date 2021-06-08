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

        String input = "photo-album 1\nquit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        App app = new App(apiClient);
        app.run();

        verify(apiClient, times(1)).getAlbum(eq(1));
    }

    @Test
    public void inputPhotoAlbum2Test() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        String input = "photo-album 2\nquit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        App app = new App(apiClient);
        app.run();

        verify(apiClient, times(1)).getAlbum(eq(2));
    }
}
