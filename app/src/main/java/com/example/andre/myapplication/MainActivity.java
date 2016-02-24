package com.example.andre.myapplication;

import com.example.andre.InfoUtils;
import com.example.andre.androidshell.ShellExecuter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Pair;
import android.view.Display;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fillInformatoin();
    }

    public int getScreenWidth ()
    {
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int width = size.x;

        return width;
    }

    public int getScreenHeight ()
    {
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int height = size.y;

        return height;
    }

    public String getResolution ()
    {
        Display display = getWindowManager().getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);

        int width = size.x;
        int height = size.y;

        return String.format("%dx%d", height, width);
    }

    public void runApplication(String packageName, String activityName)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setComponent(new ComponentName(packageName,packageName + "." + activityName));
        startActivity(intent);
    }

    public void onOpenEngineerMode(View view)
    {
        runApplication("com.mediatek.engineermode", "EngineerMode");
    }

    Pair<String, String> createObj (String key, String value)
    {
        return new Pair<String, String>(key, value);
    }
    public void onMyButtonClick(View view)
    {
        fillInformatoin();
    }

    public void fillInformatoin()
    {
        ShellExecuter exec = new ShellExecuter();

        TableLayout tableLayout = (TableLayout)findViewById(R.id.tableLayout);

        tableLayout.removeAllViews();

        tableLayout.setStretchAllColumns(true);

        float horMargin = getResources().getDimension(R.dimen.activity_horizontal_margin);
        int screenWidthOffset = getScreenWidth()/2 - Math.round(horMargin);

        ArrayList< Pair<String, String> > objList = new ArrayList< Pair<String, String> >();

        objList.add (new Pair<String, String>("Manufacturer", InfoUtils.getManufacturer()));
        objList.add (new Pair<String, String>("Model",        InfoUtils.getModel()));
        objList.add (new Pair<String, String>("Brand",        InfoUtils.getBrand()));

        objList.add (new Pair<String, String>("Resolution", getResolution()));

        objList.add (new Pair<String, String>("Platform",   InfoUtils.getPlatform()));
        //objList.add (new Pair<String, String>("CPU freq", InfoUtils.getCpufreq(exec)));
        objList.add (new Pair<String, String>("RAM",        InfoUtils.getRamType(exec)));

        objList.add (new Pair<String, String>("Android Version", InfoUtils.getAndroidVersion()));
        objList.add (new Pair<String, String>("API",             InfoUtils.getAndroidAPI()));

        objList.add (new Pair<String, String>("Baseband", Build.getRadioVersion()));

        objList.add (new Pair<String, String>("Kernel", InfoUtils.getKernelVersion(exec)));
        objList.add (new Pair<String, String>("Flash",  InfoUtils.getFlashName(exec)));

        HashMap<String,String>  hash = InfoUtils.getDriversHash(exec);

        for (String key : hash.keySet())
        {
            String value = hash.get(key);

            objList.add(new Pair<String, String>(key, value));
        }

        for (int i = 0; i < objList.size(); i++)
        {
            Pair<String, String> obj = objList.get(i);

            TableRow row = new TableRow(this);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
            row.setLayoutParams(lp);

            Context context = getApplicationContext();
            int color = ContextCompat.getColor(context, R.color.colorBackground);
            if (i % 2 == 0) row.setBackgroundColor(color);

            TextView text1 = new TextView(this);
            text1.setText(obj.first);

            TextView text2 = new TextView(this);
            text2.setText(obj.second);
            text2.setMaxWidth(screenWidthOffset);

            row.addView(text1);
            row.addView(text2);

            tableLayout.addView(row,i);
        }
    }
}
