package com.ashthebest.thaisoup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;
    private EditText editText;
    private String s = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        result = (TextView) findViewById(R.id.result);
        getBtn = (Button) findViewById(R.id.getBtn);
        editText = (EditText) findViewById(R.id.edittext);

        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s = editText.getText().toString();
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    String url = "https://www.codechef.com/users/" + s;
                    Document doc = Jsoup.connect(url).get();
                    //String title = doc.title();
                    //Elements links = doc.select("a[href]");
                    Elements elements = doc.getElementsByClass("rating-number");
                    builder.append(elements.text()).append("\n");

                    //builder.append(title).append("\n");

                    //for (Element link : links) {
                        //builder.append("\n").append("Link : ").append(link.attr("href"))
                                //.append("\n").append("Text : ").append(link.text());
                    //}
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}
