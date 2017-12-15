package matheus.com.br.bloquearnumerochato.model.base;

import java.util.List;

public interface BaseCRUDService<E> {

    void create(final E blackList) throws Exception;

    E getByNumber(final String number) throws Exception;

    List<E> getAllBlacklist() throws Exception;

    void delete(final E blackList) throws Exception;

}
