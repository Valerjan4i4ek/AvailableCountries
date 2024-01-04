package com.fenix.analyzer.main;

import com.fenix.analyzer.models.ParsingClassAndroid;
import com.fenix.analyzer.models.ParsingClassIOS;

import javax.xml.ws.Endpoint;
import java.util.Map;

public class Main {
    static String appStoreURL = "https://apps.apple.com/us/app/cash-tornado-slots-casino/id1480805172";
    static final String playLink = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&gl=US";

    public static void main(String[] args) {
        Endpoint.publish("http://localhost:8888/AvailableCountries/availableCountriesGeneratorRPC", new ParsingClassAndroid());
        Endpoint.publish("http://localhost:8888/AvailableCountries/availableCountriesGeneratorRPC", new ParsingClassIOS());
        available(appStoreURL);
    }

    public static void available(String link){
        Map<String, Boolean> map;
        if(link.contains("apps.apple.com")){
            ParsingClassIOS ios = new ParsingClassIOS();
            map = ios.getInfoAboutAvailable(link);
            for(Map.Entry<String, Boolean> entry : map.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        } else if (link.contains("play.google.com")) {
            ParsingClassAndroid android = new ParsingClassAndroid();
            System.out.println();
            map = android.getInfoAboutAvailable(link);
            for(Map.Entry<String, Boolean> entry : map.entrySet()){
                System.out.println(entry.getKey() + " " + entry.getValue());
            }
        }
        else{
            System.out.println("Incorrect link");
        }
    }
}
