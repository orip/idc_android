package example.org.fooapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;


public class ChooseMemeActivity extends ActionBarActivity {

    private int[] mDrawableIds = new int[]{
            R.drawable.success_kid,
            R.drawable.socially_awkward_penguin,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meme);

        GridView grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(new ImagesAdapter());
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d("ChooseMemeActivity", "Image clicked, index=" + i + ", id=" + mDrawableIds[i]);
                startActivity(CustomizeMemeActivity.createIntentForDrawable(ChooseMemeActivity.this, mDrawableIds[i]));
            }
        });
    }

    /**
     * Very simple adapter, can be optimized
     */
    private class ImagesAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mDrawableIds.length;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            // TODO: can optimize this with convertView
            ImageView result = new ImageView(ChooseMemeActivity.this);
            result.setScaleType(ImageView.ScaleType.CENTER_CROP);
            result.setPadding(12, 12, 12, 12);
            result.setImageResource(mDrawableIds[i]);
            return result;
        }

        // Unused right now
        @Override
        public Object getItem(int i) {
            return null;
        }

        // Unused right now
        @Override
        public long getItemId(int i) {
            return 0;
        }
    }
}
