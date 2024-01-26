package vttp2023.batch4.paf.assessment.services;

import java.io.StringReader;

import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonObject;

@Service
public class ForexService {
	
	private static final String url = "https://api.frankfurter.app/latest";


	// TODO: Task 5 //    SORRY I DO LATER!!!!!
	public float convert(String from, String to, float amount) {

		RestTemplate template = new RestTemplate();

		String uri = UriComponentsBuilder.fromUriString(url)
			.queryParam("amount", amount)
			.queryParam("from", from)
			.queryParam("to", to)
			.toUriString();

	RequestEntity<Void> request = RequestEntity.get(uri).build();
	ResponseEntity<String> response = template.exchange(request, String.class);
		JsonObject jo = Json.createReader(new StringReader(response.getBody())).readObject();
		JsonObject rates = jo.getJsonObject("rates");
		String curr = to.toUpperCase();
		return Float.valueOf(rates.get(curr).toString());

}
}

