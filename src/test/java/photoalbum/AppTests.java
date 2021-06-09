package photoalbum;



import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;
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

    @Test
    public void longCommandsProduceErrorTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        setSystemInput("photo-album 1 1\nquit");

        OutputStream out = getSystemOutput();

        App app = new App(apiClient);
        app.run();

        assertTrue(out.toString().contains(Constants.INVALID_COMMAND_MESSAGE));
    }

    @Test
    public void nonexistentCommandProducesErrorTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);
        setSystemInput("photo-test 1\nquit");

        OutputStream out = getSystemOutput();

        App app = new App(apiClient);
        app.run();

        assertTrue(out.toString().contains(Constants.INVALID_COMMAND_MESSAGE));
    }

    @Test
    public void enteringWhitespaceDoesNotProduceErrorTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);
        setSystemInput("\n\nquit");

        OutputStream out = getSystemOutput();

        App app = new App(apiClient);
        app.run();

        assertFalse(out.toString().contains(Constants.INVALID_COMMAND_MESSAGE));
    }

    @Test
    public void passingAlphaToPhotoAlbumCommandProducesErrorTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);
        setSystemInput("photo-album abc\nquit");

        OutputStream out = getSystemOutput();

        App app = new App(apiClient);
        app.run();

        assertTrue(out.toString().contains("The album ID must be an integer."));
    }

    @Test
    public void albumsWillPrintPhotosTest() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        List<Photo> album = new ArrayList<>();
        Photo photo = new Photo();
        photo.setId(15);
        photo.setTitle("lorem ipsum");
        album.add(photo);

        OutputStream out = getSystemOutput();

        setSystemInput("photo-album 1\nquit");



        try {
            given(apiClient.getAlbum(anyInt())).willReturn(album);

            App app = new App(apiClient);
            app.run();

            assertTrue(out.toString().contains("[15] lorem ipsum"));
        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: " + e.getClass().getName());
        }
    }

    @Test
    public void exceptionsWillCauseErrorMessage() {
        PhotosApiClient apiClient = mock(PhotosApiClient.class);

        OutputStream out = getSystemOutput();

        setSystemInput("photo-album 1\nquit");

        try {
            given(apiClient.getAlbum(anyInt())).willThrow(ExecutionException.class);
            App app = new App(apiClient);
            app.run();

            assertTrue(out.toString().contains("An error occurred while attempting to retrieve photo data. Please check your internet connection and try again."));

        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("Caught exception: "+e.getClass().getName());
        }



    }

    private OutputStream getSystemOutput() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));
        return out;
    }

    private void setSystemInput(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
    }

}
