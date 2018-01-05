package com.example.kureichyk.phone;

import android.app.ListActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

public class MainActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<String> list = new ArrayList<String>();

        try {
            XmlPullParser parser = getResources().getXml(R.xml.contacts);
String S1="",S2="";
            while (parser.getEventType()!= XmlPullParser.END_DOCUMENT) {
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("table"))
                    list.add("  ");
                if (parser.getEventType() == XmlPullParser.START_TAG
                        && parser.getName().equals("column")) {
                        S1=parser.getAttributeName(0);
                        S2=parser.getAttributeValue(0);
                        parser.next();
                        list.add(S2+"  "+parser.getText());
                }
                parser.next();
            }
        }
        catch (Throwable t) {
            Toast.makeText(this,
                    "Ошибка при загрузке XML-документа: " + t.toString(), 9000)
                    .show();
        }

        setListAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, list));
    }


}











