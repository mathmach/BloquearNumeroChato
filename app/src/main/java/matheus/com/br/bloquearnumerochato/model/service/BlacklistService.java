package matheus.com.br.bloquearnumerochato.model.service;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import matheus.com.br.bloquearnumerochato.model.dao.BlacklistDAO;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;
import matheus.com.br.bloquearnumerochato.model.util.DatabaseHelper;

public class BlacklistService {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public BlacklistService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    private void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    private void close() {
        dbHelper.close();
    }

    public void create(final Blacklist blackList) {
        BlacklistDAO dao = new BlacklistDAO();
        try {
            open();
            dao.create(blackList, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public Blacklist getByNumber(final String number) {
        Blacklist blacklist = null;
        BlacklistDAO dao = new BlacklistDAO();
        try {
            open();
            blacklist = dao.getByNumber(number, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return blacklist;
    }

    public void delete(final Blacklist blackList) {
        BlacklistDAO dao = new BlacklistDAO();
        try {
            open();
            dao.delete(blackList, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public List<Blacklist> getAllBlacklist() {
        List<Blacklist> blacklistNumbers = new ArrayList<Blacklist>();
        BlacklistDAO dao = new BlacklistDAO();
        try {
            open();
            blacklistNumbers = dao.getAllBlacklist(database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close();
        }
        return blacklistNumbers;
    }
}
