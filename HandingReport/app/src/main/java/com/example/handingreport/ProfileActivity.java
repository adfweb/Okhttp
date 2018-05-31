package com.example.handingreport;

        import android.content.Intent;
        import android.nfc.Tag;
        import android.support.annotation.NonNull;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.util.Log;
        import android.view.View;
        import android.widget.Button;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.android.gms.tasks.OnCompleteListener;
        import com.google.android.gms.tasks.OnSuccessListener;
        import com.google.android.gms.tasks.Task;
        import com.google.firebase.auth.FirebaseAuth;
        import com.google.firebase.auth.FirebaseUser;
        import com.google.firebase.auth.GetTokenResult;

        import java.io.IOException;
        import okhttp3.OkHttpClient;
        import okhttp3.Request;
        import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    private  String TAG = "token";

    private TextView tvEmail;
    Button btnsend, btnref;

    TextView etStatus;

    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        etStatus =(TextView) findViewById(R.id.etStatus);

        btnsend =(Button) findViewById(R.id.btnsend);
        btnref =(Button) findViewById(R.id.btnref);
        tvEmail = (TextView) findViewById(R.id.tvEmail);
        tvEmail.setText(getIntent().getExtras().getString("Email"));

        setInfo();
        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnsend.setEnabled(false);

                FirebaseAuth.getInstance().getCurrentUser()
                        .sendEmailVerification()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                btnsend.setEnabled(true);

                                if(task.isSuccessful())
                                    Toast.makeText(ProfileActivity.this, "Verification email sent to : "+FirebaseAuth.getInstance().getCurrentUser().getEmail(), Toast.LENGTH_SHORT).show();
                                 else
                                    Toast.makeText(ProfileActivity.this, "Failed to sent verification email", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        btnref.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().getCurrentUser()
                        .reload()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent profi = new Intent(ProfileActivity.this, ReportActivity.class);
                                startActivity(profi);
                                setInfo();
                            }
                        });
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


    private  void setInfo(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        etStatus.setText(new StringBuilder("STATUS : ").append(String.valueOf(user.isEmailVerified())));
        /*user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
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
        */
    }

}




