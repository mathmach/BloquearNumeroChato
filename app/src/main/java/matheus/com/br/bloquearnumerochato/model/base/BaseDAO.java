package matheus.com.br.bloquearnumerochato.model.base;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

public interface BaseDAO<E> {

    void create(final E blackList, final SQLiteDatabase database) throws Exception;

    E getByNumber(final String number, final SQLiteDatabase database) throws Exception;

    List<E> getAllBlacklist(final SQLiteDatabase database) throws Exception;

    void delete(final E blackList, final SQLiteDatabase database) throws Exception;

}
