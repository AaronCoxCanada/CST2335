package com.example.deathforce.androidlabs;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ChatWindow extends Activity {

    private ListView listView;
    private EditText editText;
    private Button button;
    private ArrayList<String> chatMessages;
    private ChatAdapter messageAdapter;

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

        button.setOnClickListener(((View v) -> {
            chatMessages.add(editText.getText().toString());
            messageAdapter.notifyDataSetChanged();
            editText.setText("");
        }));
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
            if (position%2 == 0) result = inflator.inflate(R.layout.chat_row_incoming, null);
            else result = inflator.inflate(R.layout.chat_row_outgoing, null);
            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));

            return result;
        }
    }
}
