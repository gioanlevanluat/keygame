package com.keygame.repository;

import com.keygame.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
    @Modifying
    @Query(value = "update User u set u.isDeleted = true where id in :userIds ")
    void delete(List<Long> userIds);
}
