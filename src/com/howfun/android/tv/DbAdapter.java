package com.howfun.android.tv;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.howfun.android.tv.entity.Area;
import com.howfun.android.tv.entity.TVchannel;

public class DbAdapter {

	private static final String TAG = "DbAdapter";

	private static final String DATABASE_NAME = "tv_guide";
	private static final int DATABASE_VERSION = 1;

	private static final String TABLE_AREAS = "areas";
	private static final String TABLE_CHANNELS_PREF = "channels_pref";

	private static final String KEY_ID = "_id";

	private static final String KEY_AREA_ID = "areaId";
	private static final String KEY_AREA_NAME = "areaName";
	private static final String KEY_AREA_ZONE = "areaZone";

	private static final String KEY_CHANNEL_ID = "channelId";
	private static final String KEY_CHANNEL_NAME = "channelName";

	private static final String CREATE_TABLE_AREAS = "CREATE TABLE "
			+ TABLE_AREAS + "(" + KEY_AREA_ID
			+ " INTEGER PRIMARY KEY," + KEY_AREA_NAME
			+ " TEXT NOT NULL," + KEY_AREA_ZONE + " TEXT" + ");";

	private static final String CREATE_TABLE_CHANNELS_PREF = "CREATE TABLE "
			+ TABLE_CHANNELS_PREF + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
			+ KEY_CHANNEL_ID
			+ " INTEGER NOT NULL," + KEY_CHANNEL_NAME
			+ " TEXT NOT NULL" + ");";

	private static final String DROP_TABLE_AREAS = "DROP TABLE IF EXISTS "
			+ TABLE_AREAS;

	private static final String DROP_TABLE_CHANNELS_PREF = "DROP TABLE IF EXISTS "
			+ TABLE_CHANNELS_PREF;

	private final Context mCtx;

	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;

	public DbAdapter(Context context) {
		mCtx = context;
	}

	public DbAdapter open() {
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	public void close() {
		mDbHelper.close();
	}

	public void addArea(Area area) {
		ContentValues values = new ContentValues();
		values.put(KEY_AREA_ID, area.getAreaId());
		values.put(KEY_AREA_NAME, area.getAreaName());
		values.put(KEY_AREA_ZONE, area.getAreaZone());

		mDb.insert(TABLE_AREAS, null, values);
	}

	public void addChannel(TVchannel channel) {
		if(channel == null){
			return ;
		}
		int channelId = channel.getChannelId();
		//del channel with the same id
		delPrefChannel(channelId);
		ContentValues values = new ContentValues();
		values.put(KEY_CHANNEL_ID, channel.getChannelId());
		values.put(KEY_CHANNEL_NAME, channel.getChannelName());
		
		mDb.insert(TABLE_CHANNELS_PREF, null, values);
	}
	
	private boolean prefChannelExists(int channelId){
		boolean flag = false;
		List<TVchannel> prefChannelList = getPrefChannelList();
		if(prefChannelList != null){
			Iterator<TVchannel> it = prefChannelList.iterator();
			while(it.hasNext()){
				TVchannel channel = it.next();
				int id = channel.getChannelId();
				if(id == channelId){
					flag = true;
					return flag;
				}
			}
		}
		return true;
	}

	public Cursor getAllAreas() {
		Cursor cur = null;
		cur = mDb.query(TABLE_AREAS, null, null, null, null, null, null);

		if (cur != null) {
			cur.moveToFirst();
		}
		return cur;
	}
	
	public List<Area> getAreaList(){
		List<Area> areaList = new ArrayList<Area>();
		Cursor c = getAllAreas();
		if(c != null && c.getCount() > 0){
			do{
				int id = c.getInt(c.getColumnIndex(KEY_AREA_ID));
				String areaName = c.getString(c.getColumnIndex(KEY_AREA_NAME));
				String areaZone = c.getString(c.getColumnIndex(KEY_AREA_ZONE));
				Area area = new Area(id, areaName, areaZone);
				areaList.add(area);
			}while(c.moveToNext());
		}
		if(c != null){
			c.close();
		}
		return areaList;
	}

	public Cursor getAllPrefChannels() {
		Cursor cur = null;
		cur = mDb
				.query(TABLE_CHANNELS_PREF, null, null, null, null, null, KEY_ID + " DESC");
		if (cur != null) {
			cur.moveToFirst();
		}
		return cur;
	}
	
	public List<TVchannel> getPrefChannelList(){
		List<TVchannel> prefChannelList = new ArrayList<TVchannel>();
		Cursor c = getAllPrefChannels();
		if(c != null && c.getCount() > 0){
			do{
				int id = c.getInt(c.getColumnIndex(KEY_CHANNEL_ID));
				String channelName = c.getString(c.getColumnIndex(KEY_CHANNEL_NAME));
				TVchannel channel = new TVchannel(id, channelName);
				prefChannelList.add(channel);
			}while(c.moveToNext());
		}
		if(c != null){
			c.close();
		}
		return prefChannelList;
	}
	

	public boolean delPrefChannel(int channelId) {
		boolean flag = false;
		flag = mDb.delete(TABLE_CHANNELS_PREF, KEY_CHANNEL_ID + "="
				+ channelId, null) > 0;

		return flag;
	}
	
	public boolean delPrefChannels(){
		boolean flag = false;
		flag = mDb.delete(TABLE_CHANNELS_PREF, null, null) > 0;

		return flag;
	}

	private static class DatabaseHelper extends SQLiteOpenHelper {
		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);

		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			Utils.log(TAG, "create table " + TABLE_AREAS);
			db.execSQL(CREATE_TABLE_AREAS);

			Utils.log(TAG, "create table " + TABLE_AREAS);
			db.execSQL(CREATE_TABLE_CHANNELS_PREF);

		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL(DROP_TABLE_AREAS);
			db.execSQL(DROP_TABLE_CHANNELS_PREF);

			onCreate(db);
		}

	}

}
