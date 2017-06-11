/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package notifications;

/**
 *
 * @author omari
 */
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

/**
 * Created by omari on 5/10/2017.
 */
public class FCMNotification {

    public static int EVENT = 1;
    public static int SCHEDULE_CHANGE = 2;
    public static int PERMISSION = 3;
    public static int JOB_POST = 4;

    public final static String AUTH_KEY_FCM = "AAAA4G8XWWA:APA91bHhXOHsj0a9XaKRlnn0dVIQCBdX2Z9DlkQzzOwb3SWZ2cBGMd1EI3NR66GgveM0UetWopKdnabubk34ZhNdE68T4iM_nnh1ROrvoXOOyImAU6hyWHwt47222CkkREzkeZmu13P9";
    public final static String API_URL_FCM = "https://fcm.googleapis.com/fcm/send";

    public static boolean sendNotification(int type, String title, String msg, String topic) throws Exception {

        boolean ret = false;
        String authKey = AUTH_KEY_FCM; // You FCM AUTH key
        String FMCurl = API_URL_FCM;

        URL url = new URL(FMCurl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setUseCaches(false);
        conn.setDoInput(true);
        conn.setDoOutput(true);

        conn.setRequestMethod("POST");
        conn.setRequestProperty("Authorization", "key=" + authKey);
        conn.setRequestProperty("Content-Type", "application/json");

        JSONObject all = new JSONObject();
        all.put("condition", "'" + topic + "' in topics");

        JSONObject data = new JSONObject();
        data.put("notificationType", type);
        data.put("notificationTitle", title);
        data.put("notificationBody", msg);

        all.put("data", data);

        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
        wr.write(all.toString());
        wr.flush();
        wr.close();

        int responseCode = conn.getResponseCode();
        if(responseCode ==200) ret = true;

        System.out.print(responseCode);
        return ret;
    }

}
