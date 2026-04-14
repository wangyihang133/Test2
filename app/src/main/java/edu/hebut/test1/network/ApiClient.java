package edu.hebut.test1.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.3.8:5000";

    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    public static void sendPostRequest(String endpoint, String jsonData, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(BASE_URL + endpoint);
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");
                    conn.setDoOutput(true);

                    OutputStream os = conn.getOutputStream();
                    os.write(jsonData.getBytes());
                    os.flush();
                    os.close();

                    int responseCode = conn.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        StringBuilder response = new StringBuilder();
                        String inputLine;
                        while ((inputLine = in.readLine()) != null) {
                            response.append(inputLine);
                        }
                        in.close();
                        return response.toString();
                    } else {
                        return "Error: " + responseCode;
                    }
                } catch (Exception e) {
                    Log.e("ApiClient", "Error: " + e.getMessage());
                    return "Error: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                if (result.startsWith("Error")) {
                    callback.onError(result);
                } else {
                    callback.onSuccess(result);
                }
            }
        }.execute();
    }

    public static void analyzeImage(String imagePath, ApiCallback callback) {
        // 实现图片分析功能
    }

    public static void analyzeVideo(String videoPath, ApiCallback callback) {
        // 实现视频分析功能
    }

    public static void getReports(ApiCallback callback) {
        // 实现获取报告功能
    }
}