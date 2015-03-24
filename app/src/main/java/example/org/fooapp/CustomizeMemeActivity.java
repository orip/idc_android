package example.org.fooapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;


public class CustomizeMemeActivity extends ActionBarActivity {

    private static final String DRAWABLE_ID_EXTRA_NAME = "drawable.id";
    private int mDrawableId;

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

        mDrawableId = intent.getIntExtra(DRAWABLE_ID_EXTRA_NAME, 0);
        ((ImageView) findViewById(R.id.meme_image)).setImageResource(mDrawableId);

        final EditText captionInput = (EditText) findViewById(R.id.caption_input);

        findViewById(R.id.share_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("CustomizeMemeActivity", "share button tapped");
                Bitmap immutableBitmap = BitmapFactory.decodeResource(getResources(), mDrawableId);
                Bitmap mutableBitmap = immutableBitmap.copy(immutableBitmap.getConfig(), true);
                addText(mutableBitmap, captionInput.getText().toString());
                shareBitmap(mutableBitmap);
            }
        });
    }

    /**
     * Super-simple implementation for adding text to image.
     */
    private void addText(Bitmap bitmap, String text) {
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setTextSize(24 * bitmap.getDensity());
        paint.setColor(Color.WHITE);

        Rect bounds = new Rect();
        paint.getTextBounds(text, 0, text.length(), bounds);

        // center horizontally
        int x = (bitmap.getWidth() - bounds.width()) / 2;

        // 12 dps above bottom
        int y = (bitmap.getHeight() - bounds.height() - 12 * bitmap.getDensity());

        canvas.drawText(text, x, y, paint);
    }

    /**
     * Saves the bitmap to disk and launches a share intent pointing to it
     */
    private void shareBitmap(Bitmap bitmap) {
        try {
            File path = uniquePublicallyAccessibleFile(".jpg");
            saveBitmapToFile(bitmap, Bitmap.CompressFormat.JPEG, path);
            Intent shareIntent = new Intent()
                    .setAction(Intent.ACTION_SEND)
                    .putExtra(Intent.EXTRA_STREAM, Uri.fromFile(path))
                    .setType("image/*");
            startActivity(Intent.createChooser(shareIntent, "Share meme"));
        } catch (IOException e) {
            Log.e("CustomizeMemeActivity", "Error saving image", e);
            Toast.makeText(this, "Error saving image", Toast.LENGTH_SHORT).show();
        }
    }

    private File uniquePublicallyAccessibleFile(String extension) {
        File externalDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        // Unique filename
        return new File(externalDir, UUID.randomUUID().toString() + extension);
    }

    private void saveBitmapToFile(Bitmap bitmap, Bitmap.CompressFormat format, File file) throws IOException {
        FileOutputStream fos = new FileOutputStream(file);

        boolean exceptionThrown = true;
        try {
            bitmap.compress(format, 90, fos);
            exceptionThrown = false;
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                // Swallow exception in finally block if we're already throwing an exception
                if (!exceptionThrown) {
                    throw e;
                }
            }
        }
    }
}
