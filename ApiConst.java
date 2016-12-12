package com.example.taras.firstlab;

import android.net.Uri;

/**
 * Created by Taras on 07/12/2016.
 */

public class ApiConst {
    public static final String AUTHORITY = "my_authority";
    public static final String CONTENT = "content://";
    public static final String ID_KEY = "ID";
    public static final String FIELD_KEY = "FIELD";
    public static final String DB_NAME = "MY_DB";
    public static final String PATH = "path";
    public static final int DATA_LIST = 1;
    public static final int DATA_ITEM = 2;
    public static final String ASCENDING_ORDER = "ASC";
    public static final Uri CONTENT_URI = Uri.parse(CONTENT + AUTHORITY + "/" + PATH);
    public static final String DATA_TYPE = "data_type";
    public static final String DATA_ITEM_TYPE = "data_item_type";
}
