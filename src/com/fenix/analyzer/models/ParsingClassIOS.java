package com.fenix.analyzer.models;

import com.fenix.analyzer.exceptions.NoOneCountryException;
import com.fenix.analyzer.services.AvailableCountriesGeneratorRPC;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.jws.WebService;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@WebService(endpointInterface = "com.fenix.analyzer.services.AvailableCountriesGeneratorRPC")
public class ParsingClassIOS implements AvailableCountriesGeneratorRPC {
//    static String appStoreURL = "https://apps.apple.com/us/app/cash-tornado-slots-casino/id1480805172";

//    private String link;
    static String[] availableCountries = {
            "ae", "ag", "ai", "al", "am", "ao", "ar", "at", "au", "az", "bb", "be", "bf", "bg", "bh", "bj", "bm", "bn", "bo", "br",
            "bs", "bt", "bw", "by", "bz", "ca", "ch", "cl", "cn", "co", "cr", "cv", "cy", "cz", "de", "dk", "dm", "do",
            "dz", "ec", "ee", "eg", "es", "fi", "fj", "fm", "fr", "ga", "gb", "gd", "gh", "gm", "gr", "gt", "gw", "gy",
            "hk", "hn", "hr", "hu", "id", "ie", "il", "in", "is", "it", "jm", "jo", "jp", "ke", "kg", "kh", "kn", "kw",
            "kz", "la", "lb", "lc", "lk", "lt", "lu", "lv", "md", "mg", "ml", "mn", "mo", "mr", "mt", "mu", "mv",
            "mw", "mx", "my", "mz", "na", "ne", "ng", "ni", "nl", "no", "np", "nr", "nz", "om", "pa", "pe", "pg", "ph", "pk", "pl",
            "pt", "pw", "py", "qa", "ro", "ru", "sa", "sb", "sc", "se", "sg", "si", "sk", "sl", "sn", "sr", "st", "sv", "sz", "tc",
            "td", "th", "tn", "to", "tr", "tt", "tw", "tz", "ua", "ug", "us", "uy", "uz", "vc", "ve", "vn", "vu", "za", "zm", "zw"};

    private boolean checkAppAvailability(String appID, String countryCode) {
        String appStoreURLForCountry = "https://apps.apple.com/" + countryCode + "/app/" + appID;

        try {
            Jsoup.connect(appStoreURLForCountry).get();
//            System.out.println("Додаток доступний для встановлення в " + countryCode);
            return true;
        } catch (HttpStatusException e) {
            if (e.getStatusCode() == 404) {
//                System.out.println("Додаток не доступний для встановлення в " + countryCode);
                return false;
            } else {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public Map<String, Boolean> checkAvailableInAllCountries(String appId){
        Map<String, Boolean> map = new HashMap<>();
        for(String country : availableCountries){
            boolean available = checkAppAvailability(appId, country);
            map.put(country, available);
        }
        return map;
    }
    @Override
    public Map<String, Boolean> getInfoAboutAvailable(String link){
        String appId = extractAppIDFromURL(link);
        Map<String, Boolean> map = checkAvailableInAllCountries(appId);
        if(!map.containsValue(true)){
            try {
                throw new NoOneCountryException("NOT_AVAILABLE");
            } catch (NoOneCountryException e) {
                throw new RuntimeException(e);
            }
        }
        return map;
    }

    private static String extractAppIDFromURL(String appStoreURL) {
        Pattern pattern = Pattern.compile("/id(\\d+)");
        Matcher matcher = pattern.matcher(appStoreURL);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    //    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        String appID = extractAppIDFromURL(appStoreURL);
//
//        if (appID != null) {
//
//            selectAndCheckCountry(appID, availableCountries, scanner);
//        } else {
//            System.out.println("Не вдалося витягнути appID з URL.");
//        }
//    }

//    public void available(String link){
//        Scanner scanner = new Scanner(System.in);
//        String appID = extractAppIDFromURL(link);
//
//        if (appID != null) {
//
//            selectAndCheckCountry(appID, availableCountries, scanner);
//        } else {
//            System.out.println("Не вдалося витягнути appID з URL.");
//        }
//    }
//
//    private static void selectAndCheckCountry(String appID, String[] availableCountries, Scanner scanner) {
//        System.out.println("Доступні країни в App Store:");
//        for (int i = 0; i < availableCountries.length; i++) {
//            System.out.println(i + 1 + ". " + availableCountries[i]);
//        }
//
//        System.out.print("Виберіть номер країни: ");
//        int selectedCountryIndex = scanner.nextInt();
//
//        if (selectedCountryIndex >= 1 && selectedCountryIndex <= availableCountries.length) {
//            String selectedCountry = availableCountries[selectedCountryIndex - 1];
//            checkAppAvailability(appID, selectedCountry);
//        } else {
//            System.out.println("Некоректний вибір країни.");
//        }
//    }
}
