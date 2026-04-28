package com.example.identity_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identity_service.entity.User;

// giúp tương tác với DBMS để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên bảng User, String ở dây là kiểu dữ liệu của khóa chính (id) trong bảng User
@Repository
public interface UserRepository extends JpaRepository<User, String> {
    boolean existsByUsername(String username);
}
