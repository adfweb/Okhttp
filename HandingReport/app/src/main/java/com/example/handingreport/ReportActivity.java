package com.example.handingreport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.io.IOException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ReportActivity extends AppCompatActivity {

    private  String TAG = "token";

    Button addrep;

    TextView mText;

    OkHttpClient client = new OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        mText = (TextView) findViewById(R.id.textViewres);

        addrep = (Button) findViewById(R.id.addrep);
        addrep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadJson();
            }
        });
    }

    String doGetRequest(String url, String token) throws IOException {
        Request request = new Request.Builder()
                .header("Authorization", token)
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }


    public void loadJson(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            public void onSuccess(GetTokenResult result) {
                String idToken = result.getToken();
                Log.d(TAG,"FB token: " + idToken);
                OkHttpClient client = new OkHttpClient();
                try {
                    String getResponse = doGetRequest("http://webexpertltd.com/api/flights/group?date=2018-05-28", String.format("Bearer %s", idToken));
                    //etStatus.setText(getResponse);
                    Log.d(TAG,"JSON " + getResponse);
                }
                catch(IOException e) {
                }
            }
        });

    }
}
