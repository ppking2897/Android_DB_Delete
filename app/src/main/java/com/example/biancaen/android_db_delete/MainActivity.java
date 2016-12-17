package com.example.biancaen.android_db_delete;

import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView textView;
    private EditText editText;
    private UIHandler uiHandler;
    private Editable text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.main_textView);
        editText = (EditText) findViewById(R.id.main_editText);

        uiHandler = new UIHandler();
    }

    public void button(View v) {
        text = editText.getText();
        new Thread() {
            @Override
            public void run() {
                try {
                    MultipartUtility mu = new MultipartUtility("https://android-test-db-ppking2897.c9users.io/DataBase/Delete02.php?accountId="+text, "UTF-8");

                    List<String> ret = mu.finish();


                    Message mesg = new Message();
                    Bundle data = new Bundle();
                    data.putCharSequence("data",ret.toString());
                    mesg.setData(data);
                    mesg.what=0;
                    uiHandler.sendMessage(mesg);

                } catch (Exception e) {
                    Log.v("ppking", "DB Error:" + e.toString());
                }
            }
        }.start();

    }





    private class UIHandler extends android.os.Handler{

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0 :
                    textView.setText(msg.getData().getCharSequence("data")+"\n");
                    break;
            }
        }
    }


}