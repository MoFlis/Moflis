package com.project.moflis.user.repository;


import com.project.moflis.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    User findByNameAndPhone(String name, String phone);

    default User findByEmailOrThrow(String email) {
        User user = findByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }
        return user;
    }

}
