package photoalbum;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class PhotosApiClient {

    private final HttpClient httpClient;
    private final String apiUrl;

    public PhotosApiClient(String apiUrl) {
        this.httpClient =  HttpClient.newBuilder().build();
        this.apiUrl = apiUrl;
    }

    public List<Album> listAlbums() throws ExecutionException, InterruptedException, JsonProcessingException {
        return makeApiCall("albums", new TypeReference<>() {});
    }

    public List<Photo> getAlbum(long albumId) throws ExecutionException, InterruptedException, JsonProcessingException {
        return makeApiCall("photos?albumId=" + albumId, new TypeReference<>() {});
    }

    public Photo getPhoto(long id)  throws ExecutionException, InterruptedException, JsonProcessingException {
        List<Photo> photos = makeApiCall("photos?id=" + id, new TypeReference<>() {});
        return photos.size() > 0 ? photos.get(0) : null;
    }

    private <T> List<T> makeApiCall(String queryString, TypeReference<List<T>> type) throws ExecutionException, InterruptedException, JsonProcessingException {
        String json = makeHttpRequest(apiUrl + queryString);
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, type);
    }

    public String makeHttpRequest(String url) throws ExecutionException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .get();
    }
}
