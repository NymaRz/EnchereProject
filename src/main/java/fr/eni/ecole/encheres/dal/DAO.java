package fr.eni.ecole.encheres.dal;

import java.util.List;

public interface DAO<T> {

	default void save(T t) {}
	default T findOne(int noArticle){ return null;}
	default List<T> findAll(){return null;}
	default void delete(int noArticle){}
	default void update(T t){}
}
