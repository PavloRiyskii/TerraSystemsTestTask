package com.terrassystem.testtask.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Павло on 19.06.2017.
 */
public interface GenericDao<Entity, Id extends Serializable> {
    Entity findById(Id id);
    List<Entity> findAll();
    void save(Entity entity);
    void update(Entity entity);
    void delete(Entity entity);
}
