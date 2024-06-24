package com.example.emoney.utils;


import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;

public class CurrenciesNbu {

    private final static String URL_NBU = "https://bank.gov.ua/NBUStatService/v1/statdirectory/exchange?json";

    public static String getCurrenciesJson() throws RuntimeException{
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL_NBU))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();


        HttpResponse<String> response = null;

        try{
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }

    public static List<CurrencyParseObj> parseCurrencies(String data){

        Gson gson = new Gson();

        CurrencyParseObj[] currencies = gson.fromJson(data, CurrencyParseObj[].class);

        return Arrays.asList(currencies);
    }

    public static List<CurrencyParseObj> getListRequiredCurrByCode(List<CurrencyParseObj> currencies,
                                                                String... codes){

        Map<String, CurrencyParseObj> mapCurr= new HashMap<>();

        for(CurrencyParseObj currency : currencies){
            mapCurr.put(currency.getCurrencyCode(), currency);
        }
        mapCurr.put("UAH", new CurrencyParseObj( "Українська гривня", 1.00, "UAH"));

        List<CurrencyParseObj> result = new ArrayList<>();
        for(String code : codes){
            var curr = mapCurr.get(code.toUpperCase().trim());
            if(curr!=null){
                result.add(curr);
            }
        }

        return result;
    }

    public static Map<String, Double> getMapRequiredCurrByCode(List<CurrencyParseObj> currencies,
                                                                   String... codes){

        Map<String, CurrencyParseObj> mapCurr= new HashMap<>();

        Map<String, Double> result= new HashMap<>();

        for(CurrencyParseObj currency : currencies){
            mapCurr.put(currency.getCurrencyCode(), currency);
        }

        for(String code: codes){
            var i = mapCurr.get(code.trim().toUpperCase());
            if(i != null){
                result.put(code.toUpperCase().trim(), i.getRate());
            }
        }
        result.put("UAH", 1.00);

        return result;
    }



    public static void main(String[] args){
        List<CurrencyParseObj> res = parseCurrencies(getCurrenciesJson());
        List<CurrencyParseObj> resultList = getListRequiredCurrByCode(res, "USD", "Eur", "cad","XPT");

        Map<String, Double> resultMap = getMapRequiredCurrByCode(res, "USD", "Eur", "cad","XPT");


        System.out.println("List ");

        for(CurrencyParseObj curr : resultList){
            System.out.println(curr.toString());
        }

        System.out.println("Map ");
        for(String key : resultMap.keySet()){
            System.out.println(key + ": " + resultMap.get(key));
        }
    }

}
