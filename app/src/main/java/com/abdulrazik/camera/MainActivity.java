package com.abdulrazik.camera;

import android.app.Activity;
import android.os.Bundle;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.*;
import android.text.InputType;

import androidx.media3.common.MediaItem;
import androidx.media3.common.Player;
import androidx.media3.common.PlaybackException;
import androidx.media3.exoplayer.ExoPlayer;
import androidx.media3.ui.PlayerView;

public class MainActivity extends Activity {

    int blue = Color.rgb(25, 105, 220);
    ExoPlayer player;
    PlayerView playerView;
    EditText rtspInput;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(20, 25, 20, 20);
        root.setBackgroundColor(Color.rgb(242,247,255));

        TextView title = new TextView(this);
        title.setText("ABDULRAZIK");
        title.setTextSize(29);
        title.setTextColor(blue);
        title.setGravity(Gravity.CENTER);
        title.setTypeface(null, 1);
        root.addView(title);

        TextView subtitle = new TextView(this);
        subtitle.setText("SMART CAMERA");
        subtitle.setTextSize(17);
        subtitle.setTextColor(Color.DKGRAY);
        subtitle.setGravity(Gravity.CENTER);
        root.addView(subtitle);

        status = new TextView(this);
        status.setText("وضعیت دوربین: آماده اتصال");
        status.setTextSize(16);
        status.setTextColor(Color.DKGRAY);
        status.setGravity(Gravity.CENTER);
        status.setPadding(5, 20, 5, 15);
        root.addView(status);

        TextView label = new TextView(this);
        label.setText("آدرس RTSP دوربین:");
        label.setTextSize(16);
        label.setTextColor(Color.BLACK);
        root.addView(label);

        rtspInput = new EditText(this);
        rtspInput.setSingleLine(true);
        rtspInput.setTextSize(14);
        rtspInput.setHint("rtsp://آدرس-دوربین:554/...");
        rtspInput.setInputType(InputType.TYPE_CLASS_TEXT |
                InputType.TYPE_TEXT_VARIATION_URI);
        root.addView(rtspInput);

        addButton(root, "▶ پخش تصویر زنده", v -> startCamera());

        addButton(root, "■ قطع تصویر", v -> stopCamera());

        playerView = new PlayerView(this);
        playerView.setUseController(true);
        LinearLayout.LayoutParams videoParams =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 230);
        videoParams.setMargins(0, 15, 0, 15);
        root.addView(playerView, videoParams);

        addButton(root, "📷 افزودن دوربین", v ->
                status.setText("آدرس RTSP دوربین را در کادر بالا وارد کنید"));

        addButton(root, "📡 اتصال Wi-Fi", v ->
                status.setText("گوشی و دوربین باید به شبکه مناسب متصل باشند"));

        addButton(root, "🌍 اتصال از راه دور", v ->
                status.setText("اتصال از راه دور نیازمند پشتیبانی دوربین و تنظیمات آن است"));

        ScrollView scroll = new ScrollView(this);
        scroll.addView(root);
        setContentView(scroll);
    }

    void startCamera() {
        String url = rtspInput.getText().toString().trim();

        if (!url.startsWith("rtsp://") && !url.startsWith("rtsps://")) {
            status.setText("آدرس معتبر RTSP وارد کنید");
            return;
        }

        stopCamera();

        try {
            status.setText("در حال اتصال به دوربین...");

            player = new ExoPlayer.Builder(this).build();
            playerView.setPlayer(player);

            player.addListener(new Player.Listener() {
                @Override
                public void onPlaybackStateChanged(int playbackState) {
                    if (playbackState == Player.STATE_READY) {
                        status.setText("تصویر دوربین آماده پخش است");
                    } else if (playbackState == Player.STATE_BUFFERING) {
                        status.setText("در حال دریافت تصویر...");
                    }
                }

                @Override
                public void onPlayerError(PlaybackException error) {
                    status.setText("پخش ناموفق بود؛ آدرس و پشتیبانی RTSP را بررسی کنید");
                }
            });

            MediaItem mediaItem = MediaItem.fromUri(url);
            player.setMediaItem(mediaItem);
            player.prepare();
            player.play();

        } catch (Exception e) {
            status.setText("خطا در اتصال به دوربین");
        }
    }

    void stopCamera() {
        if (player != null) {
            playerView.setPlayer(null);
            player.release();
            player = null;
        }
        if (status != null) {
            status.setText("تصویر دوربین متوقف شد");
        }
    }

    void addButton(LinearLayout root, String text,
                   View.OnClickListener listener) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(15);
        button.setAllCaps(false);
        button.setTextColor(Color.WHITE);
        button.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(blue));
        button.setOnClickListener(listener);

        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, 58);
        params.setMargins(0, 6, 0, 6);
        root.addView(button, params);
    }

    @Override
    protected void onStop() {
        super.onStop();
        stopCamera();
    }
}
