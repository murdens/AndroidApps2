package com.example.android.cookies;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * Called when the cookie should be eaten.
     */
    public void eatCookie(View view) {
        // TODO: Find a reference to the ImageView in the layout. Change the image.
        ImageView afterCookieImageView = findViewById(R.id.android_cookie_image_view);
        afterCookieImageView.setImageResource(R.drawable.after_cookie);

        // TODO: Find a reference to the TextView in the layout. Change the text.
        TextView statusTextView = findViewById(R.id.status_text_view);
        statusTextView.setText("I'm pogged!");
    }

    /**
     * Called when reset pressed.
     */
    public void reset(View view) {

        ImageView resetCookieImageView = findViewById(R.id.android_cookie_image_view);
        resetCookieImageView.setImageResource(R.drawable.before_cookie);

        TextView resetstatusTextView = findViewById(R.id.status_text_view);
        resetstatusTextView.setText("I'm so hungry");
    }
}