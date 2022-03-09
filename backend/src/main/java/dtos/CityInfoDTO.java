package dtos;

import entities.CityInfo;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CityInfoDTO {
    private int zipCode;
    private String city;

    public CityInfoDTO(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        if (cityInfo.getCity() != null) {
            this.zipCode = cityInfo.getZipCode();
            this.city = cityInfo.getCity();
        }
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public CityInfoDTO getCityInfo () {
        try {
            URL url = new URL("https://api.dataforsyningen.dk/postnumre");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("ACCEPT", MediaType.APPLICATION_JSON);
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            String jsonString = null;



            if (scanner.hasNext()) {
                jsonString = scanner.nextLine();
            }
            scanner.close();


        } catch (IOException | IOException e) {
            e.printStackTrace();
        }

    }
}
