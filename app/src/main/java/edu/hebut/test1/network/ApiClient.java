package edu.hebut.test1.network;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class ApiClient {
    private static final String BASE_URL = "http://192.168.3.8:5000"; // 请确保此IP在你的局域网内可达
    private static final String TAG = "ApiClient";

    public interface ApiCallback {
        void onSuccess(String response);
        void onError(String error);
    }

    /**
     * 发送 POST 请求到后端
     * @param endpoint 接口路径，如 "/test"
     * @param jsonData JSON 格式的请求体数据
     * @param callback 回调接口
     */
    public static void sendPostRequest(String endpoint, String jsonData, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                try {
                    URL url = new URL(BASE_URL + endpoint);
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/json");

                    dos = new DataOutputStream(conn.getOutputStream());
                    dos.writeBytes(jsonData);

                    // 读取响应
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
                        return "Error: HTTP Code " + responseCode;
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Send Request Error: " + e.getMessage());
                    return "Error: " + e.getMessage();
                } finally {
                    try { if (dos != null) dos.close(); } catch (Exception e) {}
                    if (conn != null) conn.disconnect();
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

    /**
     * 上传图片到后端
     * @param imageFile 图片文件
     * @param callback 回调
     */
    public static void uploadImage(File imageFile, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                HttpURLConnection conn = null;
                DataOutputStream dos = null;
                FileInputStream fis = null;
                try {
                    URL url = new URL(BASE_URL + "/analyze_image"); // 假设后端接口路径
                    conn = (HttpURLConnection) url.openConnection();
                    conn.setDoInput(true);
                    conn.setDoOutput(true);
                    conn.setUseCaches(false);
                    conn.setRequestMethod("POST");

                    String boundary = "*****" + UUID.randomUUID().toString() + "*****";
                    conn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
                    conn.setRequestProperty("Connection", "Keep-Alive");

                    dos = new DataOutputStream(conn.getOutputStream());
                    fis = new FileInputStream(imageFile);

                    // 写入文件头
                    dos.writeBytes("--" + boundary + "\r\n");
                    dos.writeBytes("Content-Disposition: form-data; name=\"image\"; filename=\"" + imageFile.getName() + "\"\r\n");
                    dos.writeBytes("Content-Type: image/jpeg\r\n\r\n");

                    // 写入文件内容
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = fis.read(buffer)) != -1) {
                        dos.write(buffer, 0, bytesRead);
                    }
                    dos.writeBytes("\r\n");
                    dos.writeBytes("--" + boundary + "--\r\n");

                    dos.flush();

                    // 读取响应
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
                        return "Error: HTTP Code " + responseCode;
                    }

                } catch (Exception e) {
                    Log.e(TAG, "Upload Error: " + e.getMessage());
                    return "Error: " + e.getMessage();
                } finally {
                    try { if (fis != null) fis.close(); } catch (Exception e) {}
                    try { if (dos != null) dos.close(); } catch (Exception e) {}
                    if (conn != null) conn.disconnect();
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
}