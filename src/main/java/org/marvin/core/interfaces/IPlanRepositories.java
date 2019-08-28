package org.marvin.core.interfaces;

import org.marvin.core.models.Todo;

import java.util.List;

public interface IPlanRepositories<T extends Todo> {

        List<T> findDonePlans();
        T findById(long id);
        List<T> findByUserId(long id);


        long create(T plan, long account);
        T update(T plan);
        void delete(long id);
        void deleteComplete();


}
