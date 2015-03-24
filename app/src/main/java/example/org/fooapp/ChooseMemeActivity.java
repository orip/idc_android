package example.org.fooapp;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;


public class ChooseMemeActivity extends ActionBarActivity {

    private ImagesAdapter mImagesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_meme);

        mImagesAdapter = new ImagesAdapter(this, new int[]{
                R.drawable.success_kid,
                R.drawable.socially_awkward_penguin
        });

        GridView grid = (GridView) findViewById(R.id.grid);
        grid.setAdapter(mImagesAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_choose_meme, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static class ImagesAdapter extends BaseAdapter {
        private final Context mContext;
        private final int[] mDrawableIds;

        private ImagesAdapter(Context context, int[] drawableIds) {
            mContext = context;
            this.mDrawableIds = drawableIds;
        }

        @Override
        public int getCount() {
            return mDrawableIds.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            // TODO: can optimize this with convertView
            ImageView result = new ImageView(mContext);
            result.setScaleType(ImageView.ScaleType.CENTER_CROP);
            result.setPadding(12, 12, 12, 12);
            result.setImageResource(mDrawableIds[i]);
            return result;
        }
    }
}
