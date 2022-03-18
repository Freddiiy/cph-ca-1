package dtos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.CityInfo;
import utils.CityInfoApi;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CityInfoDTO {
    private int zipCode;
    private String city;

    public CityInfoDTO(int zipCode, String city) {
        this.zipCode = zipCode;
        this.city = city;
    }

    public CityInfoDTO(CityInfo cityInfo) {
        this.zipCode = cityInfo.getZipCode();
        this.city = cityInfo.getCity();
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

    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "zipCode=" + zipCode +
                ", city='" + city + '\'' +
                '}';
    }
}
