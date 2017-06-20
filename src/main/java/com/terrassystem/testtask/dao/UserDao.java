package com.terrassystem.testtask.dao;

import com.terrassystem.testtask.entity.User;
import org.springframework.stereotype.Repository;

/**
 * Created by Павло on 19.06.2017.
 */
@Repository
public interface UserDao extends GenericDao<User, Long> {
}
