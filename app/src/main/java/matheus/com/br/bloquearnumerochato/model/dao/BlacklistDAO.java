package matheus.com.br.bloquearnumerochato.model.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import matheus.com.br.bloquearnumerochato.model.util.DatabaseHelper;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;

public class BlacklistDAO {

    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public BlacklistDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        open();
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Blacklist create(final Blacklist blackList) {
        final ContentValues values = new ContentValues();

        values.put("phone_number", blackList.getPhoneNumber());

        final long id = database.insert(DatabaseHelper.TABLE_BLACKLIST, null, values);

        blackList.setId(id);
        return blackList;
    }

    public Blacklist getByNumber(String number) {
        Blacklist blacklist = null;

        final Cursor cursor = database.query(DatabaseHelper.TABLE_BLACKLIST, new String[]{"id", "phone_number"}, "phone_number = ?", new String[]{number}, null, null, null);

        if (cursor.moveToFirst()) {
            blacklist = new Blacklist();

            blacklist.setId(cursor.getLong(0));
            blacklist.setPhoneNumber(cursor.getString(1));
        }
        return blacklist;
    }

    public void delete(final Blacklist blackList) {
        database.delete(DatabaseHelper.TABLE_BLACKLIST, "phone_number = '" + blackList.getPhoneNumber() + "'", null);
    }

    public List<Blacklist> getAllBlacklist() {
        final List<Blacklist> blacklistNumbers = new ArrayList<Blacklist>();

        final Cursor cursor = database.query(DatabaseHelper.TABLE_BLACKLIST, new String[]{"id", "phone_number"}, null, null, null, null, null);

        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            final Blacklist number = new Blacklist();

            number.setId(cursor.getLong(0));
            number.setPhoneNumber(cursor.getString(1));

            blacklistNumbers.add(number);

            cursor.moveToNext();
        }
        return blacklistNumbers;
    }
}