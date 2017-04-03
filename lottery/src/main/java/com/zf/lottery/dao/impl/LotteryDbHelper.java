package com.zf.lottery.dao.impl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zf.lottery.data.Lottery;

import java.util.ArrayList;
import java.util.Date;
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
        db.execSQL("CREATE TABLE IF NOT EXISTS ssc (term INTEGER primary key, openTime INTEGER, n1 smallint, "
                + "n2 smallint, n3 smallint, n4 smallint, n5 smallint,sum smallint)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void saveSscResult(List<Lottery> lotteries) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            for (Lottery lottery : lotteries) {
                int[] numbers = lottery.getNumbers();
                Object[] bindArgs = {lottery.getTerm(), lottery.getTime().getTime(), numbers[0], numbers[1],
                        numbers[2], numbers[3], numbers[4], lottery.getSum()};
                db.execSQL("insert into ssc(term,openTime,n1,n2,n3,n4,n5,sum) values(?,?,?,?,?,?,?,?)", bindArgs);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }

    public List<Lottery> readSscResult() {
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from ssc order by term desc", null);
        List<Lottery> lotteries = new ArrayList<>();
        while (cursor.moveToNext()) {
            Lottery lottery = new Lottery();
            lottery.setTerm(cursor.getLong(0));
            lottery.setTime(new Date(cursor.getLong(1)));
            int[] numbers = {cursor.getInt(2), cursor.getInt(3), cursor.getInt(4), cursor.getInt(5), cursor.getInt(6)};
            lottery.setNumbers(numbers);
            lottery.setSum(cursor.getInt(7));
            lotteries.add(lottery);
        }
        cursor.close();
        db.close();
        return lotteries;
    }

    public void deleteOldAndSaveNewResult(List<Lottery> lotteries) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.beginTransaction();
            for (Lottery lottery : lotteries) {
                int[] numbers = lottery.getNumbers();
                Object[] bindArgs = {lottery.getTerm(), lottery.getTime().getTime(), numbers[0], numbers[1],
                        numbers[2], numbers[3], numbers[4], lottery.getSum()};
                db.execSQL("insert into ssc(term,openTime,n1,n2,n3,n4,n5,sum) values(?,?,?,?,?,?,?,?)", bindArgs);
            }
            db.execSQL("delete from ssc where term in(select term from ssc order by term asc limit " + lotteries.size() + ')');
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }
}
