package fr.efrei.database_service.service;

public interface CRUD<T, U> {

    public T save(T object);

    public T findById(U id);

    public T update(U id, T object);

    public void deleteById(U id);
}