package example.org.fooapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


public class CustomizeMemeActivity extends ActionBarActivity {

    private static final String DRAWABLE_ID_EXTRA_NAME = "drawable.id";

    public static Intent createIntentForDrawable(Context context, int drawableId) {
        return new Intent(context, CustomizeMemeActivity.class)
                .putExtra(DRAWABLE_ID_EXTRA_NAME, drawableId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize_meme);
        Intent intent = getIntent();

        if (!intent.hasExtra(DRAWABLE_ID_EXTRA_NAME)) {
            Toast.makeText(this, "No image to share :-(", Toast.LENGTH_SHORT).show();
            finish(); // close this activity
        }

        int drawableId = intent.getIntExtra(DRAWABLE_ID_EXTRA_NAME, 0);
        ((ImageView) findViewById(R.id.meme_image)).setImageResource(drawableId);

        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CustomizeMemeActivity", "share button tapped");
            }
        });
    }
}
