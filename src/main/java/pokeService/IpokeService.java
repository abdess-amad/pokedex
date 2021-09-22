package pokeService;

import java.net.http.HttpResponse;

public interface IpokeService {
	public HttpResponse<String> getResponce(String urlAttachment);
}
