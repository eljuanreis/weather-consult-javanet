package br.com.juan;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.Set;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class WeatherApp extends Application {

	public static void main(String[] args) {
		Application.launch(WeatherApp.class, args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// pegar os dados da temperatura
		LinkedHashMap<String, String> data = this.getWeatherData();
		
		Set<String> keys = data.keySet();
		Pane pn = new Pane();
		
		int positionY = 10; //Pros dados irem pra um de baixo do outro
		
		for (String key : keys) {
			String date = this.convertDatetime(key); //já com o formato certo
			String temperature = data.get(key) + "ºC";
			
			Label hora = new Label("Horário: " + date);
			hora.relocate(10, positionY);
			hora.setFont(new Font("Arial", 16));
			
			Label temperatura = new Label("| Temperatura: " + temperature);
			temperatura.relocate(200, positionY);
			temperatura.setFont(new Font("Arial", 16));
			
			positionY += 30;
			
			pn.getChildren().add(hora);
			pn.getChildren().add(temperatura);
		}
		
		Scene sc = new Scene(pn, 400, 600);
		
		stage.setScene(sc);
		stage.show();
	}

	private String convertDatetime(String inputDateTime) {
		DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
		LocalDateTime dateTime = LocalDateTime.parse(inputDateTime, inputFormatter);
		
		DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		String formattedDataTime = dateTime.format(outputFormatter);
		
		return formattedDataTime;

	}

	private LinkedHashMap<String, String> getWeatherData() throws Exception {
		APIConnection aCon = new APIConnection();
		
		return aCon.getWeather();
	}

}
