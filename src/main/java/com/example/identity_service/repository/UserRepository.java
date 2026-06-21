package com.example.identity_service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.identity_service.entity.User;

// giúp tương tác với DBMS để thực hiện các thao tác CRUD (Create, Read, Update, Delete) trên bảng User, String ở dây là kiểu dữ liệu của khóa chính (id) trong bảng User
@Repository
public interface UserRepository extends JpaRepository<User, String> {
	boolean existsByUsername(String username);

	/*
	 * spring JPA sẽ tự động tạo ra câu truy vấn SQL để tìm kiếm người dùng dựa trên
	 * tên người dùng (username) và trả về một Optional chứa đối tượng User nếu tìm
	 * thấy, hoặc một Optional rỗng nếu không tìm thấy.
	 *
	 * Optional là một lớp trong Java được sử dụng để đại diện cho một giá trị có
	 * thể tồn tại hoặc không tồn tại. Nó giúp tránh việc trả về null và cung cấp
	 * các phương thức tiện ích để xử lý giá trị một cách an toàn.
	 */
	Optional<User> findByUsername(String username);
}
