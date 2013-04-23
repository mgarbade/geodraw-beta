package de.Geo.geodraw;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class Draw extends Activity {
	protected static final int RESULT_LOAD_IMAGE = 1;
	private DrawView mDrawView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		mDrawView = new DrawView(this);
		setContentView(mDrawView);
		mDrawView.setBackgroundColor(Color.BLACK);
		mDrawView.requestFocus();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.paint_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {
		case R.id.undo: {
			// hier sollte nur der letzte Pfad gelöscht werden
			mDrawView.undoLastLine();
			return true;
		}
		case R.id.p_white_id: {
			mDrawView.changeColour(0);
			return true;
		}
		case R.id.p_blue_id: {
			mDrawView.changeColour(1);
			return true;
		}
		case R.id.p_lblue_id: {
			mDrawView.changeColour(2);
			return true;
		}
		case R.id.p_green_id: {
			mDrawView.changeColour(3);
			return true;
		}
		case R.id.p_pink_id: {
			mDrawView.changeColour(4);
			return true;
		}
		case R.id.p_red_id: {
			mDrawView.changeColour(5);
			return true;
		}
		case R.id.p_yellow_id: {
			mDrawView.changeColour(6);
			return true;
		}
		case R.id.p_black_id: {
			mDrawView.changeColour(7);
			return true;
		}
		case R.id.b_white_id: {
			mDrawView.setBackgroundColor(Color.WHITE);
			return true;
		}
		case R.id.b_blue_id: {
			mDrawView.setBackgroundColor(Color.BLUE);
			return true;
		}
		case R.id.b_lblue_id: {
			mDrawView.setBackgroundColor(Color.CYAN);
			return true;
		}
		case R.id.b_green_id: {
			mDrawView.setBackgroundColor(Color.GREEN);
			return true;
		}
		case R.id.b_pink_id: {
			mDrawView.setBackgroundColor(Color.MAGENTA);
			return true;
		}
		case R.id.b_red_id: {
			mDrawView.setBackgroundColor(Color.RED);
			return true;
		}
		case R.id.b_yellow_id: {
			mDrawView.setBackgroundColor(Color.YELLOW);
			return true;
		}
		case R.id.b_black_id: {
			mDrawView.setBackgroundColor(Color.BLACK);
			return true;
		}
		case R.id.b_custom_id: {
			setCustomBackground(mDrawView);
			return true;
		}
		case R.id.save_drawing: {
			// Hier sollte der Befehl zum Speichern aufgerufen werden
			saveDrawing();
			return true;
		}
		case R.id.clear_all: {
			// hier sollten alle Pfade gelöscht werden
			mDrawView.clearPoints();
			return true;
		}
		case R.id.verysmall: {
			// Befehl zum Ändern der Breite
			mDrawView.changeWidth(0);
			return true;
		}
		case R.id.small: {
			// Befehl zum Ändern der Breite
			mDrawView.changeWidth(1);
			return true;
		}
		case R.id.medium: {
			// Befehl zum Ändern der Breite
			mDrawView.changeWidth(2);
			return true;
		}
		case R.id.large: {
			// Befehl zum Ändern der Breite
			mDrawView.changeWidth(3);
			return true;
		}
		case R.id.verylarge: {
			// Befehl zum Ändern der Breite
			mDrawView.changeWidth(4);
			return true;
		}
		default: {
			return true;
		}
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri resultUri = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(resultUri,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

			int inSample = 2;
			BitmapFactory.Options opts = new BitmapFactory.Options();
			opts.inSampleSize = inSample;

			GetterSetter.mBitmap = BitmapFactory.decodeFile(picturePath, opts);
		}

	}

	void setCustomBackground(DrawView v) {
		Intent i = new Intent(Intent.ACTION_PICK,
				android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
		startActivityForResult(i, RESULT_LOAD_IMAGE);
	}

	void saveDrawing() {
		// Build the Drawing Cache
		mDrawView.setDrawingCacheEnabled(true);
		mDrawView.buildDrawingCache();
		File imageFile = new File(Environment.getExternalStorageDirectory(),
				"Pictures/testimage.jpg");

		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(imageFile);
			mDrawView.getDrawingCache(true).compress(CompressFormat.JPEG,
					100, fileOutputStream);
			fileOutputStream.close();
			Context context = getApplicationContext();
			CharSequence text = "Success!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		} catch (IOException ex) {
			Log.d("geodraw", ex.getMessage());
			Context context = getApplicationContext();
			CharSequence text = "Fail!";
			int duration = Toast.LENGTH_SHORT;
			Toast toast = Toast.makeText(context, text, duration);
			toast.show();
		}
		mDrawView.setDrawingCacheEnabled(false);
	}
}
