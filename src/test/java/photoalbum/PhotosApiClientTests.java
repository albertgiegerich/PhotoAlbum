package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class PhotosApiClientTests {

    private static final String PHOTO_JSON = "[{" +
            "\"albumId\": 1," +
            "\"id\": 40," +
            "\"title\": \"titleTest\"," +
            "\"url\": \"urlTest\"," +
            "\"thumbnailUrl\": \"thumbnailUrlTest\"" +
            "}]";

    private static final String ALBUM_JSON = "[" +
            "{" +
            "\"userId\": 1," +
            "\"id\": 2,\n" +
            "\"title\": \"testtesttest\"" +
            "}]";


    @Test
    public void getAlbumPhotoJsonIsConvertedToPhotoTest() {
        PhotosApiClient photosApiClient = mock(PhotosApiClient.class);

        try {
            given(photosApiClient.makeHttpRequest(any(String.class))).willReturn(PHOTO_JSON);
            given(photosApiClient.getAlbum(anyInt())).willCallRealMethod();

            List<Photo> album = photosApiClient.getAlbum(1);

            assertEquals(1, album.size());

            Photo photo = album.get(0);

            assertEquals(1, photo.getAlbumId());
            assertEquals(40, photo.getId());
            assertEquals("titleTest", photo.getTitle());
            assertEquals("urlTest", photo.getUrl());
            assertEquals("thumbnailUrlTest", photo.getThumbnailUrl());

        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("An exception was thrown: "+e.getClass().getName());
        }
    }

    @Test
    public void getPhotoJsonIsConvertedToPhotoTest() {
        PhotosApiClient photosApiClient = mock(PhotosApiClient.class);

        try {
            given(photosApiClient.makeHttpRequest(any(String.class))).willReturn(PHOTO_JSON);
            given(photosApiClient.getPhoto(anyInt())).willCallRealMethod();

            Photo photo = photosApiClient.getPhoto(1);

            assertEquals(1, photo.getAlbumId());
            assertEquals(40, photo.getId());
            assertEquals("titleTest", photo.getTitle());
            assertEquals("urlTest", photo.getUrl());
            assertEquals("thumbnailUrlTest", photo.getThumbnailUrl());

        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("An exception was thrown: "+e.getClass().getName());
        }
    }

    @Test
    public void getAlbumJsonIsConvertedToAlbumTest() {
        PhotosApiClient photosApiClient = mock(PhotosApiClient.class);

        try {
            given(photosApiClient.makeHttpRequest(any(String.class))).willReturn(ALBUM_JSON);
            given(photosApiClient.listAlbums()).willCallRealMethod();

            List<Album> albums = photosApiClient.listAlbums();
            Album album = albums.get(0);

            assertEquals(2, album.getId());
            assertEquals(1, album.getUserId());
            assertEquals("testtesttest", album.getTitle());



        } catch (ExecutionException | InterruptedException | JsonProcessingException e) {
            fail("An exception was thrown: "+e.getClass().getName());
        }
    }

}
