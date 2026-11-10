package com.cesur.dam.data;

import java.util.List;

public interface DAO<T> {
    List<T> listarTodos();
    T insertar(T entidad);
}