package com.example.taras.firstlab;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.text.TextUtils;

public class DataHandler extends ContentProvider {

    private static final UriMatcher uriMatcher;
    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(ApiConst.AUTHORITY, ApiConst.PATH, ApiConst.DATA_LIST);
        uriMatcher.addURI(ApiConst.AUTHORITY, ApiConst.PATH + "/#", ApiConst.DATA_ITEM);
    }

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHelper = new DBHelper(getContext(), ApiConst.DB_NAME);
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case ApiConst.DATA_LIST : {
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = ApiConst.FIELD_KEY + " " + ApiConst.ASCENDING_ORDER;
                }

                break;
            }

            case ApiConst.DATA_ITEM : {
                String id = uri.getLastPathSegment();

                if (TextUtils.isEmpty(selection)) {
                    selection = ApiConst.ID_KEY + " = " + id;
                } else {
                    selection += " AND " + ApiConst.ID_KEY + " = " + id;
                }

                break;
            }
        }

        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(ApiConst.DB_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        cursor.setNotificationUri(getContext().getContentResolver(), ApiConst.CONTENT_URI);
        return cursor;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        long rowId = db.insert(ApiConst.DB_NAME, null, values);
        Uri resultUri = ContentUris.withAppendedId(ApiConst.CONTENT_URI, rowId);
        getContext().getContentResolver().notifyChange(resultUri, null);
        return resultUri;
    }

    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {
        String id = uri.getLastPathSegment();

        if (TextUtils.isEmpty(selection)) {
            selection = ApiConst.ID_KEY + " = " + id;
        } else {
            selection += " AND " + ApiConst.ID_KEY + " = " + id;
        }

        db = dbHelper.getWritableDatabase();
        int cnt = db.delete(ApiConst.DB_NAME, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        String id = uri.getLastPathSegment();

        if (TextUtils.isEmpty(selection)) {
            selection = ApiConst.ID_KEY + " = " + id;
        } else {
            selection += " AND " + ApiConst.ID_KEY + " = " + id;
        }

        db = dbHelper.getWritableDatabase();
        int cnt = db.update(ApiConst.DB_NAME, values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return cnt;
    }

    @Override
    public String getType(@NonNull Uri uri) {
        if (uriMatcher.match(uri) == ApiConst.DATA_LIST) {
            return ApiConst.DATA_TYPE;
        } else {
            return ApiConst.DATA_ITEM_TYPE;
        }
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name) {
            super(context, name, null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase sqLiteDatabase) {
            sqLiteDatabase.execSQL("create table " + ApiConst.DB_NAME + " (" +
                    ApiConst.ID_KEY + " integer primary key autoincrement, " +
                    ApiConst.FIELD_KEY + " text);");
            ContentValues contentValues = new ContentValues();
            contentValues.put(ApiConst.FIELD_KEY, "Default string #1");
            sqLiteDatabase.insert(ApiConst.DB_NAME, null, contentValues);
        }

        @Override
        public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        }
    }
}
