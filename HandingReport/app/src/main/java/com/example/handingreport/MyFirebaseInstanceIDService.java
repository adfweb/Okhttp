package com.example.handingreport;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    private static  final String TAG ="MyFirebaseInsIDService";

    @Override
    public void onTokenRefresh() {
        //Get updete token
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG , "New token: " + refreshedToken);

        //You can save the token into third party server to do anything you want
    }
}