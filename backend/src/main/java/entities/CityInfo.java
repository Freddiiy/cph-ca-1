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

    public CityInfo(CityInfoDTO cityInfoDTO) {
        if (cityInfoDTO.getCity() != null) {
            this.zipCode = cityInfoDTO.getZipCode();
            this.city = cityInfoDTO.getCity();
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
}
