package matheus.com.br.bloquearnumerochato.model.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import matheus.com.br.bloquearnumerochato.model.base.dao.BaseBlacklistDAO;
import matheus.com.br.bloquearnumerochato.model.util.DatabaseHelper;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;

public class BlacklistDAO implements BaseBlacklistDAO {

    public void create(final Blacklist blackList, final SQLiteDatabase database) {
        final ContentValues values = new ContentValues();

        values.put("phone_number", blackList.getPhoneNumber());

        final long id = database.insert(DatabaseHelper.TABLE_BLACKLIST, null, values);

        blackList.setId(id);
    }

    public Blacklist getByNumber(final String number, final SQLiteDatabase database) {
        Blacklist blacklist = null;

        final Cursor cursor = database.query(DatabaseHelper.TABLE_BLACKLIST, new String[]{"id", "phone_number"}, "phone_number = ?", new String[]{number}, null, null, null);

        if (cursor.moveToFirst()) {
            blacklist = new Blacklist();

            blacklist.setId(cursor.getLong(0));
            blacklist.setPhoneNumber(cursor.getString(1));
        }
        return blacklist;
    }

    public void delete(final Blacklist blackList, final SQLiteDatabase database) {
        database.delete(DatabaseHelper.TABLE_BLACKLIST, "phone_number = ?", new String[]{blackList.getPhoneNumber()});
    }

    public List<Blacklist> getAllBlacklist(final SQLiteDatabase database) {
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
