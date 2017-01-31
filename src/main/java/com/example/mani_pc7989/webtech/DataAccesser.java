package com.example.mani_pc7989.webtech;

import android.os.AsyncTask;
import android.provider.ContactsContract;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by MANI-PC on 11/22/2016.
 */

public class DataAccesser extends AsyncTask<String,Void,Void> {

    String json = "";
    private TaskCompleted listener;

    public DataAccesser(TaskCompleted listener){
        this.listener=listener;
    }


        @Override
        protected Void doInBackground(String... params) {

            try {
                OkHttpClient client = new OkHttpClient();
                Request request = new Request.Builder()
                        .url(params[0])
                        .build();

                Response response = client.newCall(request).execute();
                json = response.body().string();
            }catch (Exception exp) {
                int x = 10;
            }

            return null;
        }


    protected void onPostExecute(Void param) {
       listener.OnTaskCompleted(json);
    }
}
