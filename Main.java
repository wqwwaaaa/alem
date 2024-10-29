package org.example;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class Main {
    public static void main(String[] args) {
        String Base_URL = "https://evilinsult.com/generate_insult.php?lang=en&type=json";
        try {
            FileWriter fileWriter = new FileWriter("/home/prog/Desktop/aaa.txt", false);
            URL url = new URL(Base_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder responce = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                responce.append(inputLine);
            }
            in.close();
            conn.disconnect();

            JSONObject jsonResponce = new JSONObject(responce.toString());
            String insult = jsonResponce.getString("insult");
            String insult1 = translate(insult);
            System.out.println(insult1);
            fileWriter.write(insult1);
            fileWriter.close();
        }
        catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
    }
    private static String translate(String text) throws Exception{
        String TRANSLATE_API_URL = "https://translate.googleapis.com/translate_a/single?client=gtx&sl=en&tl=ru&dt=t&q=";

        String urlString = TRANSLATE_API_URL + URLEncoder.encode(text,"UTF-8");
        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        conn.disconnect();
        return new JSONArray(response.toString()).getJSONArray(0).getJSONArray(0).getString(0);
    }
}