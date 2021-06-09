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

    @Test
    public void photoJsonIsConvertedToPhotoTest() {
        String apiUrl = "https://jsonplaceholder.typicode.com/photos";
        PhotosApiClient photosApiClient = mock(PhotosApiClient.class);
        String json = "[{" +
                "\"albumId\": 1," +
                "\"id\": 40," +
                "\"title\": \"titleTest\"," +
                "\"url\": \"urlTest\"," +
                "\"thumbnailUrl\": \"thumbnailUrlTest\"" +
                "}]";


        try {
            given(photosApiClient.makeHttpRequest(any(String.class))).willReturn(json);
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

}
