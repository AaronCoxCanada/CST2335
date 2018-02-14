package com.example.deathforce.androidlabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.PorterDuff;
import android.graphics.Shader;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindow";

    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> chatMessages;
    private ChatAdapter messageAdapter;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);
        listView = findViewById(R.id.chatListView);
        editText = findViewById(R.id.chat_text_message);
        button = findViewById(R.id.chatSendButton);
        chatMessages = new ArrayList<>();

        messageAdapter = new ChatAdapter(this);
        listView.setAdapter(messageAdapter);

        db = new ChatDatabaseHelper(this).getWritableDatabase();

        cursor = db.rawQuery("SELECT * FROM " + ChatDatabaseHelper.TABLE_NAME, null, null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Log.i(ACTIVITY_NAME, "SQL_MESSAGE: " + cursor.getString(
                    cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)) );
            chatMessages.add(cursor.getString(
                    cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE)));
            cursor.moveToNext();
        }
        messageAdapter.notifyDataSetChanged();

        Log.i(ACTIVITY_NAME, "Cursor's column count = " + cursor.getColumnCount());
        for (int i = 0; i < cursor.getColumnCount(); i++){
            Log.i(ACTIVITY_NAME, "Cursor Column name #" + i + " = " + cursor.getColumnName(i));
        }

        button.setOnClickListener(((View v) -> {
            chatMessages.add(editText.getText().toString());

            ContentValues cs = new ContentValues();
            cs.put(ChatDatabaseHelper.KEY_MESSAGE, editText.getText().toString());
            db.insert(ChatDatabaseHelper.TABLE_NAME, null, cs);

            messageAdapter.notifyDataSetChanged();
            editText.setText("");

            InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            try{
                mgr.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            } catch (NullPointerException e){
                //empty
            }

        }));

    }

    protected void onDestroy(){
        super.onDestroy();
        cursor.close();
        db.close();
    }

    private class ChatAdapter extends ArrayAdapter<String> {

        ChatAdapter(Context ctx){
            super(ctx, 0);


        }

        @Override
        public int getCount() {
            return chatMessages.size();
        }

        @Override
        public String getItem(int position) {
            return chatMessages.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflator = ChatWindow.this.getLayoutInflater();
            View result;
            if (position%2 == 0) {
                result = inflator.inflate(R.layout.chat_row_incoming, null);
                result.setBackgroundTintMode(PorterDuff.Mode.ADD);
            }
            else result = inflator.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }
    }
}
