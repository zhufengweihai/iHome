package com.zf.lottery.dao.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zf.lottery.data.Lottery;

import java.util.List;

/**
 * Created by zhufeng7 on 2017-3-20.
 */

public class LotteryDbHelper extends SQLiteOpenHelper {
    private static final String NAME = "count"; //数据库名称

    private static final int VERSION = 1; //数据库版本

    public LotteryDbHelper(Context context) {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS ssc (term INTEGER primary key, openTime INTEGER, n1 smallint, n2 " +
                "smallint" + ", n3 smallint, n4 smallint, n5 smallint,sum smallint, maxAbsence smallint)");
        db.execSQL("CREATE TABLE IF NOT EXISTS absence (number smallint, absence smallint)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addSscResult(List<Lottery> lotteries) {
        SQLiteDatabase db = getWritableDatabase();
        db.beginTransaction();
        for (Lottery lottery : lotteries) {
            ContentValues cv = new ContentValues();
            cv.put("term", lottery.getTerm());
            cv.put("openTime", lottery.getTime().getTime());
            int[] numbers = lottery.getNumbers();
            cv.put("n1", numbers[0]);
            cv.put("n2", numbers[1]);
            cv.put("n3", numbers[2]);
            cv.put("n4", numbers[3]);
            cv.put("n5", numbers[4]);
            cv.put("sum", lottery.getSum());
            cv.put("maxAbsence", lottery.getMaxAbence());
            db.insert("ssc", null, cv);
        }
        db.endTransaction();
        db.close();
    }

    public void readSscResult() {
        SQLiteDatabase db = getReadableDatabase();

        db.close();
    }
}
