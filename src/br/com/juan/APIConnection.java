package br.com.juan;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.LinkedHashMap;

import org.json.JSONArray;
import org.json.JSONObject;

public class APIConnection {
	private static final String API_ENDPOINT = "https://api.open-meteo.com/v1/forecast?latitude=-23.5475&longitude=-46.6361&hourly=temperature_2m,rain"; // link
																																							// gerado
																																							// d
																																							// //
																																							// open-meteo

	public synchronized LinkedHashMap<String, String> getWeather() throws Exception {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().GET().timeout(Duration.ofSeconds(10))
				.uri(URI.create(API_ENDPOINT)).build();

		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		if (response.statusCode() != 200) {
			throw new Exception("Houve um erro na requisição:" + response.body());
		}

		// deu tudo certo e retornou um json na response
		JSONObject obj = new JSONObject(response.body());

		JSONArray tempHorario = obj.getJSONObject("hourly").getJSONArray("temperature_2m");
		JSONArray horarios = obj.getJSONObject("hourly").getJSONArray("time");

		// montado estrutura de dados
		int sizeTemp = tempHorario.length();

		// a gente vai iterar ordenado já
		LinkedHashMap<String, String> temperaturas = new LinkedHashMap<>();

		for (int i = 0; i < sizeTemp; i++) {
			temperaturas.put(String.valueOf(horarios.get(i)), String.valueOf(tempHorario.get(i)));
		}

		return temperaturas;
	}
}
