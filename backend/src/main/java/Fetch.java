import dtos.CityInfoDTO;
import dtos.PersonDTO;
import entities.CityInfo;

import java.util.ArrayList;
import java.util.List;

public class Fetch {
    public static void main(String[] args) {

        List<CityInfo> cityInfoDTOList = CityInfo.getCityInfo();

        System.out.println(cityInfoDTOList);
    }
}
