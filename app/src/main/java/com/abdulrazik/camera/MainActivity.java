package com.abdulrazik.camera;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.*;

public class MainActivity extends Activity {

    int blue = Color.rgb(25, 105, 220);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setGravity(Gravity.CENTER_HORIZONTAL);
        root.setPadding(24, 40, 24, 24);
        root.setBackgroundColor(Color.rgb(242,247,255));

        TextView title = new TextView(this);
        title.setText("ABDULRAZIK");
        title.setTextSize(30);
        title.setTextColor(blue);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("SMART CAMERA");
        subtitle.setTextSize(18);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        TextView status = new TextView(this);
        status.setText("وضعیت دوربین: متصل نیست");
        status.setTextSize(17);
        status.setTextColor(Color.DKGRAY);
        status.setGravity(Gravity.CENTER);
        status.setPadding(10, 35, 10, 35);
        root.addView(status);

        addButton(root, "📷 افزودن دوربین", v ->
            status.setText("برای افزودن دوربین، ابتدا روش اتصال را انتخاب کنید")
        );

        addButton(root, "📡 اتصال Wi-Fi", v ->
            status.setText("آماده تنظیم اتصال Wi-Fi دوربین")
        );

        addButton(root, "🌍 اتصال از راه دور", v ->
            status.setText("اتصال از راه دور پس از تنظیم Cloud دوربین فعال می‌شود")
        );

        addButton(root, "🎥 تصویر زنده", v ->
            status.setText("ابتدا باید دوربین سازگار متصل شود")
        );

        addButton(root, "🖼 عکس‌ها و فیلم‌ها", v ->
            status.setText("بخش مدیریت عکس و فیلم")
        );

        addButton(root, "⚙ تنظیمات", v ->
            status.setText("تنظیمات ABDULRAZIK CAMERA")
        );

        setContentView(root);
    }

    void addButton(LinearLayout root, String text,
                   android.view.View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(15);
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);
        button.setBackgroundTintList(
            android.content.res.ColorStateList.valueOf(blue)
        );
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params =
            new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, 62);
        params.setMargins(0, 7, 0, 7);
        root.addView(button, params);
    }
}
