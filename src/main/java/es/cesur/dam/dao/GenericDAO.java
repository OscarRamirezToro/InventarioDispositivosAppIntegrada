package es.cesur.dam.dao;

import java.util.List;

public interface GenericDAO<T> {
    List<T> listarTodos();
    T insertar(T entidad);
}