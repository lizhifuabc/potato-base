package com.potato.base.business.demo.repository;

import com.potato.base.business.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 用户
 *
 * @author lizhifu
 * @date 2021/12/14
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
