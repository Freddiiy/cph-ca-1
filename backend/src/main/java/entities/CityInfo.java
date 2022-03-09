package entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.CityInfoDTO;
import utils.CityInfoApi;
import utils.EMF_Creator;

import javax.persistence.*;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Entity
public class CityInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @OneToMany(mappedBy = "cityInfo")
    private List<Address> address = new ArrayList<>();

    private int zipCode;
    private String city;

    public CityInfo(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfo()
    {
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

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    static EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();

    public static List<CityInfo> getCityInfo () {
        EntityManager em1 = emf.createEntityManager();
        TypedQuery<CityInfo> query = em1.createQuery("select c from CityInfo c", CityInfo.class);
        System.out.println(query.getResultList().size());

        if (query.getResultList().size() > 0) {
            return query.getResultList();
        }
        em1.close();

        try {
            URL url = new URL("https://api.dataforsyningen.dk/postnumre");
            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("ACCEPT", MediaType.APPLICATION_JSON);
            Scanner scanner = new Scanner(urlConnection.getInputStream());
            StringBuilder jsonString = new StringBuilder();

            while (scanner.hasNext()) {
                jsonString.append(scanner.nextLine());
            }
            scanner.close();

            CityInfoApi[] cities = GSON.fromJson(String.valueOf(jsonString), CityInfoApi[].class);

            List<CityInfo> convertedCities = new ArrayList<>();

            for (CityInfoApi cityInfoApi : cities) {
                convertedCities.add(
                        new CityInfo(
                                Integer.parseInt(cityInfoApi.getNr()),
                                cityInfoApi.getNavn()
                        )
                );
            }

            for (CityInfo cityInfo : convertedCities) {
                EntityManager em2 = emf.createEntityManager();
                try {
                    em2.getTransaction().begin();
                    em2.persist(cityInfo);
                    em2.getTransaction().commit();
                } finally {
                    em2.close();
                }
            }

            return convertedCities;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
