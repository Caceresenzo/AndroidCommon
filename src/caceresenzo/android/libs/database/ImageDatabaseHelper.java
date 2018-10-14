package caceresenzo.android.libs.database;

import java.io.ByteArrayOutputStream;

import android.content.ContentValues;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

/**
 * @source https://github.com/hiteshbpatel/Android_Blog_Projects/tree/master/Android-SQLite-master/src/com/expr/sample/db
 */

public class ImageDatabaseHelper extends SQLiteOpenHelper {
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "imageCache";
	private static final String TABLE_IMAGE = "images";
	
	// Image Table Columns names
	private static final String COLUMN_ID = "column_id";
	private static final String IMAGE_ID = "image_id";
	private static final String IMAGE_BITMAP = "image_bitmap";
	
	private SQLiteDatabase database;
	
	public ImageDatabaseHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase sqLiteDatabase) {
		String CREATE_IMAGE_TABLE = "CREATE TABLE " + TABLE_IMAGE + "(" + COLUMN_ID + " INTEGER PRIMARY KEY ," + IMAGE_ID + " TEXT," + IMAGE_BITMAP + " TEXT )";
		sqLiteDatabase.execSQL(CREATE_IMAGE_TABLE);
		this.database = sqLiteDatabase;
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) { // Drop older table if existed
		if (newVersion > oldVersion) {
			sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_IMAGE);
			onCreate(sqLiteDatabase);
		}
	}
	
	public void empty() {
		onUpgrade(database, 0, 1);
	}
	
	public void insetImage(Resources resources, Bitmap bitmap, String imageId) { // resources = getResources()
		insetImage(new BitmapDrawable(resources, bitmap), imageId);
	}
	
	public boolean insetImage(Drawable drawable, String imageId) {
		database = this.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(IMAGE_ID, imageId);
		Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
		values.put(IMAGE_BITMAP, stream.toByteArray());
		
		long result = database.insert(TABLE_IMAGE, null, values);
		Log.d("caceresenzo.apps.boxplay", "> " + (result));
		if (result == -1) {
			return false;
		} else {
			return true;
		}
	}
	
	public ImageHelper getImage(String imageId) {
		database = this.getReadableDatabase();
		Cursor cursor = database.query(TABLE_IMAGE, new String[] { COLUMN_ID, IMAGE_ID, IMAGE_BITMAP }, IMAGE_ID + " LIKE '" + imageId + "%'", null, null, null, null);
		ImageHelper imageHelper = new ImageHelper();
		
		if (cursor.moveToFirst()) {
			do {
				imageHelper.setImageId(cursor.getString(1));
				imageHelper.setImageByteArray(cursor.getBlob(2));
			} while (cursor.moveToNext());
		}
		
		cursor.close();
		return imageHelper;
	}
	
	public class ImageHelper {
		
		private String imageId;
		private byte[] imageByteArray;
		
		public String getImageId() {
			return imageId;
		}
		
		public void setImageId(String imageId) {
			this.imageId = imageId;
		}
		
		public byte[] getImageByteArray() {
			return imageByteArray;
		}
		
		public void setImageByteArray(byte[] imageByteArray) {
			this.imageByteArray = imageByteArray;
		}
		
	}
}