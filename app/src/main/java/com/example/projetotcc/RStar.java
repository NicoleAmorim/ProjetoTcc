package com.example.projetotcc;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class RStar {
    private Activity activity;
    private AlertDialog dialog;

    public RStar(Activity myActivity){
        activity = myActivity;
    }

    public void StartRat(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.item_rating, null));

        builder.setCancelable(false);

        dialog = builder.create();
        dialog.show();
    }

    public void DismissDialog(){
        dialog.dismiss();
    }
}
