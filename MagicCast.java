package com.example.taras.firstlab;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * Created by Taras on 12/12/2016.
 */

public final class MagicCast {
    public static ArrayList<String> toStringList(Cursor cursor) {
        ArrayList<String> res = new ArrayList<>();

        if (cursor.moveToFirst()) {
            int fieldRow, idRow;
            fieldRow =  cursor.getColumnIndex(ApiConst.FIELD_KEY);
            idRow =     cursor.getColumnIndex(ApiConst.ID_KEY);

            do {
                int id;
                String field;
                field = cursor.getString(fieldRow);
                id =    cursor.getInt(idRow);
                res.add(field + "$" + toString(id));
            } while (cursor.moveToNext());
        }

        return res;
    }

    public static ContentValues toContentValues(String tag, String val) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(tag, val);
        return contentValues;
    }

    public static String getValue(String s) {
        String res = "";

        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '$') {
                break;
            }

            res += s.charAt(i);
        }

        return res;
    }

    public static String toString(Integer i) {
        return Integer.toString(i);
    }

    public static int getId(String s) {
        int res = 0, i = 0;

        while (s.charAt(i) != '$') {
            i++;
        }

        i++;

        for (; i < s.length(); i++) {
            res = res * 10 + s.charAt(i) - '0';
        }

        return res;
    }

    public static ArrayList<String> getSearchResults(ArrayList<String> list, String request) {
        ArrayList<String> res = new ArrayList<>();
        int m = request.length();

        for (String s : list) {
            int n = s.length();

            for (int i = 0; i + m - 1 < n; i++) {
                boolean ok = true;

                for (int j = 0; j < m; j++)
                    if (s.charAt(i + j) != request.charAt(j)) {
                        ok = false;
                    }

                if (ok) {
                    res.add(s);
                    break;
                }
            }
        }

        return res;
    }
}
