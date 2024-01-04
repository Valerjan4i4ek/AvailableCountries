package com.fenix.analyzer.models;

import com.fenix.analyzer.exceptions.NoOneCountryException;
import com.fenix.analyzer.services.AvailableCountriesGeneratorRPC;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


import java.io.IOException;
import java.util.*;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService(endpointInterface = "com.fenix.analyzer.services.AvailableCountriesGeneratorRPC")
public class ParsingClassAndroid implements AvailableCountriesGeneratorRPC {
//    static final String playLink = "https://play.google.com/store/apps/details?id=com.mojang.minecraftpe&gl=US";
//    private String link;

    static String[] availableCountries = {
            "ae", "ag", "ai", "al", "am", "ao", "ar", "at", "au", "az", "bb", "be", "bf", "bg", "bh", "bj", "bm", "bn", "bo", "br",
            "bs", "bt", "bw", "by", "bz", "ca", "ch", "cl", "cn", "co", "cr", "cv", "cy", "cz", "de", "dk", "dm", "do",
            "dz", "ec", "ee", "eg", "es", "fi", "fj", "fm", "fr", "ga", "gb", "gd", "gh", "gm", "gn", "gq", "gr", "gt", "gw", "gy",
            "hk", "hn", "hr", "ht", "hu", "id", "ie", "il", "in", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "kn", "kw",
            "kz", "la", "lb", "lc", "li", "lk", "ls", "lt", "lu", "lv", "md", "mg", "mh", "ml", "mn", "mo", "mr", "mt", "mu", "mv",
            "mw", "mx", "my", "mz", "na", "ne", "ng", "ni", "nl", "no", "np", "nr", "nz", "om", "pa", "pe", "pg", "ph", "pk", "pl",
            "pt", "pw", "py", "qa", "ro", "ru", "sa", "sb", "sc", "se", "sg", "si", "sk", "sl", "sn", "sr", "st", "sv", "sz", "tc",
            "td", "th", "tn", "to", "tr", "tt", "tw", "tz", "ua", "ug", "us", "uy", "uz", "vc", "ve", "vn", "vu", "ws", "za", "zm", "zw"};

    private final static int AVAILABLE = 100;
    private final static int NOT_AVAILABLE = 200;
    public static boolean isAvailable(String playLink) throws IOException {
        Document doc = Jsoup.connect(playLink)
                .userAgent("Chrome/104.0.0.0")
                .referrer("http://www.google.com")
                .get();

        boolean installButtonFound = false;
        for (Element button : doc.select("button")) {
//            System.out.println(button);
            if (button.text().contains("Install") || button.text().contains("Buy")) {
                installButtonFound = true;
                break;
            }
        }
        return installButtonFound;
    }
    public static String interfaceLanguageCheck(String link){
        if(link.contains("&hl")){
            StringBuffer sb = new StringBuffer(link);
            int i = sb.indexOf("&hl");
            sb.delete(i, i+6);
//            System.out.println(sb);
            return sb.toString();
        }
        else{
//            System.out.println("OLOLO");
            return link;
        }
    }
    public static String replaceCountry(String link, String country){
        StringBuffer sb = new StringBuffer(link);

        if(link.contains("&gl")){
            int i = sb.indexOf("&gl");
            sb.replace(i + 4, i + 6, country);
//            System.out.println(sb);
            return sb.toString();
        }
        else {
            sb.append("&gl=" + country);
            return sb.toString();
        }

    }
    public static List<String> countriesDB(){
        List<String> list = new ArrayList<>();
        Collections.addAll(list, availableCountries);
        return list;
    }
    public static String fixLink(String link, String country){
        link = interfaceLanguageCheck(link);
        link = replaceCountry(link, country);
        return link;
    }
    @Override
    public Map<String, Boolean> getInfoAboutAvailable(String appLink){
        List<String> list = countriesDB();
        String link;
        Map<String, Boolean> map = new HashMap<>();

        for(String country : list){
            link = fixLink(appLink, country);
            try {
                if (isAvailable(link)) {
//                    System.out.println("Доступно в обраному регіоні. " + country);
                    map.put(country, true);
                } else {
//                    System.out.println("Недоступно в обраному регіоні.  " + country);
                    map.put(country, false);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(!map.containsValue(true)){
            try {
                throw new NoOneCountryException("NOT_AVAILABLE");
            } catch (NoOneCountryException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }
    //    public List<String> mappingAvailable(String link){
//        Map<String, Integer> map = getInfoAboutAvailable(link);
//        List<String> list = new ArrayList<>();
//        if(!map.containsValue(AVAILABLE)){
//            list.add("NOT_AVAILABLE");
//        }
//        else {
//            for(Map.Entry<String, Integer> entry : map.entrySet()){
//                if(entry.getValue() == AVAILABLE){
//                    list.add(entry.getKey());
//                }
//            }
//        }
//        return list;
//    }

//    public Map<String, Integer> getInfoAboutAvailable(String appLink){
//        List<String> list = countriesDB();
//        String link;
//        Map<String, Integer> map = new HashMap<>();
//
//        for(String country : list){
//            link = fixLink(appLink, country);
//            try {
//                if (isAvailable(link)) {
////                    System.out.println("Доступно в обраному регіоні. " + country);
//                    map.put(link, AVAILABLE);
//                } else {
////                    System.out.println("Недоступно в обраному регіоні.  " + country);
//                    map.put(link, NOT_AVAILABLE);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//        return map;
//    }


//    public static String selectCountry(String link){
//        List<String> list = countriesDB();
//        String country = null;
//        System.out.println("Select country");
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println((i+1) + " " + list.get(i));
//        }
//        Scanner scanner = new Scanner(System.in);
//        int response = scanner.nextInt();
//        if (response > list.size() || response < 0){
//            System.out.println("Incorrect country");
//        }
//        else{
//            country = list.get(response-1);
//        }
//
//        return replaceCountry(link, country);
//    }

    //    public void available(String link){
//        String languageCheck = interfaceLanguageCheck(link);
//        languageCheck = selectCountry(languageCheck);
//
//        try {
//            if (isAvailable(languageCheck)) {
//                System.out.println("Доступно в обраному регіоні.");
//            } else {
//                System.out.println("Недоступно в обраному регіоні.");
//            }
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

//    public void checkAvailableInAllCountries(String appId){
//        List<String> list = countriesDB();
//        String link = null;
//
//        for(String country : list){
//            link = getAppLink(appId, country);
//            try {
//                if (isAvailable(link)) {
////                    System.out.println("Доступно в обраному регіоні. " + country);
//                } else {
////                    System.out.println("Недоступно в обраному регіоні.  " + country);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        }
//    }


//    public static String getAppLink(String appId, String country){
//        String link = "https://play.google.com/store/apps/details";
//        String params = "id=" + appId + "&gl=" + country;
//        return link + "?" + params;
//    }


}
