package io.github.grano22.feedbrico.shared.infrastructure.readstore.repository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.ListPagingAndSortingRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface ReadRepository<T, ID> extends Repository<T, ID>, ListPagingAndSortingRepository<T, ID>, QueryByExampleExecutor<T> {
    T getReferenceById(ID id);
    boolean existsById(ID id);

    Optional<T> findById(ID id);

    List<T> findAll();
    List<T> findAllById(Iterable<ID> ids);

    <S extends T> @NonNull List<S> findAll(@NonNull Example<S> example);
    <S extends T> @NonNull List<S> findAll(@NonNull Example<S> example, @NonNull Sort sort);

    long count();
}
