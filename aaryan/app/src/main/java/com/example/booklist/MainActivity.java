package com.example.booklist;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private TextView bookListTextView;
    private BroadcastReceiver bookReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        bookListTextView = findViewById(R.id.bookListTextView);
        
        // Register broadcast receiver
        bookReceiver = new BookBroadcastReceiver();
        IntentFilter filter = new IntentFilter("com.example.booklist.NEW_BOOK_2025");
        registerReceiver(bookReceiver, filter);
        
        // Start API call
        new Thread(this::fetchBooks).start();
    }

    private void fetchBooks() {
        try {
            URL url = new URL("https://designer.mocky.io/v2/your-api-endpoint");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            
            InputStream inputStream = connection.getInputStream();
            parseXML(inputStream);
            
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> bookListTextView.setText("Error loading books"));
        }
    }

    private void parseXML(InputStream inputStream) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            parser.setInput(inputStream, null);
            
            ArrayList<String> bookTitles = new ArrayList<>();
            String currentTitle = "";
            String currentYear = "";
            
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if (parser.getName().equals("title")) {
                        currentTitle = parser.nextText();
                    } else if (parser.getName().equals("year")) {
                        currentYear = parser.nextText();
                        if (currentYear.equals("2025")) {
                            sendBroadcast(new Intent("com.example.booklist.NEW_BOOK_2025")
                                    .putExtra("title", currentTitle));
                        }
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if (parser.getName().equals("book") && !currentTitle.isEmpty()) {
                        bookTitles.add(currentTitle);
                        currentTitle = "";
                    }
                }
                eventType = parser.next();
            }
            
            final StringBuilder displayText = new StringBuilder();
            for (String title : bookTitles) {
                displayText.append(title).append("\n");
            }
            
            runOnUiThread(() -> bookListTextView.setText(displayText.toString()));
            
        } catch (Exception e) {
            e.printStackTrace();
            runOnUiThread(() -> bookListTextView.setText("Error parsing books"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(bookReceiver);
    }

    private class BookBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String title = intent.getStringExtra("title");
            bookListTextView.append("\n\nNew 2025 Book Alert: " + title);
        }
    }
} 