package com.example.deathforce.androidlabs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

public class ListItemsActivity extends Activity {

    static final int REQUEST_IMAGE_CAPTURE = 1;

    protected static final String ACTIVITY_NAME = "ListItemsActivity";
    ImageButton imageButton;
    CheckBox checkBox;
    Switch switchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);
        Log.i(ACTIVITY_NAME, "In onCreate()");

        imageButton = findViewById(R.id.imageButton);
        switchButton = findViewById(R.id.switch1);
        checkBox = findViewById(R.id.checkBox);

        imageButton.setOnClickListener((View e) ->{
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        });

        switchButton.setOnCheckedChangeListener((e, j)->{
            if (j){
                Toast.makeText(this, R.string.switch_on, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, R.string.switch_off, Toast.LENGTH_LONG).show();
            }
        });

        checkBox.setOnCheckedChangeListener((e, j)->{
            if(j){
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.dialog_message)
                        .setTitle(R.string.dialog_title)
                        .setPositiveButton(R.string.ok, (dialog, which) -> {
                            Intent resultIntent = new Intent();
                            resultIntent.putExtra("Response",getResources().getString(R.string.dialog_toast_message));
                            setResult(Activity.RESULT_OK, resultIntent);
                            finish();
                        })
                        .setNegativeButton(R.string.cancel, (dialog, which) -> {

                        })
                        .show();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = null;
            if (extras != null) {
                imageBitmap = (Bitmap) extras.get("data");
            }
            imageButton.setImageBitmap(imageBitmap);

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i(ACTIVITY_NAME, "In onResume()");
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();

        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }
}
