package matheus.com.br.bloquearnumerochato.model.service;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import matheus.com.br.bloquearnumerochato.model.base.service.BaseBlacklistService;
import matheus.com.br.bloquearnumerochato.model.dao.BlacklistDAO;
import matheus.com.br.bloquearnumerochato.model.entity.Blacklist;
import matheus.com.br.bloquearnumerochato.model.util.DatabaseHelper;

public class BlacklistService implements BaseBlacklistService {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public BlacklistService(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void create(final Blacklist blackList) {
        BlacklistDAO dao = new BlacklistDAO();
        try {
            database = dbHelper.getWritableDatabase();
            dao.create(blackList, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    public Blacklist getByNumber(final String number) {
        Blacklist blacklist = null;
        BlacklistDAO dao = new BlacklistDAO();
        try {
            database = dbHelper.getReadableDatabase();
            blacklist = dao.getByNumber(number, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
        return blacklist;
    }

    public void delete(final Blacklist blackList) {
        BlacklistDAO dao = new BlacklistDAO();
        try {
            database = dbHelper.getWritableDatabase();
            dao.delete(blackList, database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
    }

    public List<Blacklist> getAllBlacklist() {
        List<Blacklist> blacklistNumbers = new ArrayList<>();
        BlacklistDAO dao = new BlacklistDAO();
        try {
            database = dbHelper.getReadableDatabase();
            blacklistNumbers = dao.getAllBlacklist(database);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            dbHelper.close();
        }
        return blacklistNumbers;
    }
}
