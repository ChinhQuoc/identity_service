package com.example.identity_service.service;

import java.util.HashSet;
import java.util.List;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.identity_service.dto.request.UserCreationRequest;
import com.example.identity_service.dto.request.UserUpdateRequest;
import com.example.identity_service.dto.response.UserResponse;
import com.example.identity_service.entity.User;
import com.example.identity_service.enums.Role;
import com.example.identity_service.exception.AppException;
import com.example.identity_service.exception.ErrorCode;
import com.example.identity_service.mapper.UserMapper;
import com.example.identity_service.repository.RoleRepository;
import com.example.identity_service.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
	UserRepository userRepository;
	UserMapper userMapper;
	PasswordEncoder passwordEncoder;
	RoleRepository roleRepository;

	public UserResponse createUser(UserCreationRequest request) {
		if (userRepository.existsByUsername(request.getUsername())) {
			throw new AppException(ErrorCode.USER_EXISTS);
		}

		User user = userMapper.toUser(request);
		/*
		 * PasswordEncoder là một interface trong Spring Security được sử dụng để mã hóa
		 * mật khẩu người dùng trước khi lưu vào cơ sở dữ liệu.
		 * BCryptPasswordEncoder là một trong những triển khai phổ biến của
		 * PasswordEncoder, sử dụng thuật toán bcrypt để mã hóa mật khẩu,
		 * cung cấp tính bảo mật cao và khả năng chống lại các cuộc tấn công
		 * brute-force.
		 *
		 * 10 là độ mạnh của thuật toán bcrypt, càng cao thì mật khẩu càng khó bị bẻ
		 * khóa, nhưng cũng sẽ tốn nhiều thời gian hơn để mã hóa và xác thực mật khẩu.
		 */
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		HashSet<String> roles = new HashSet<>();
		roles.add(Role.USER.name());
		// user.setRoles(roles);

		return userMapper.toUserResponse(userRepository.save(user));
	}

	// Trước lúc gọi fun thì user phải có role là ADMIN
	@PreAuthorize("hasRole('ADMIN')")
	public List<UserResponse> getAllUsers() {
		log.info("Getting all users");
		return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
	}

	// PostAuthorize sẽ kiểm tra sau khi fun đã được thực thi
	// Chỉ cho phép người dùng truy cập thông tin của chính họ
	@PostAuthorize("returnObject.username == authentication.name")
	public UserResponse getUserById(String id) {
		log.info("In method getuserByID");
		return userMapper
				.toUserResponse(userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found")));
	}

	public UserResponse updateUser(UserUpdateRequest request, String userId) {
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found"));

		userMapper.updateUser(user, request);
		user.setPassword(passwordEncoder.encode(request.getPassword()));

		var roles = roleRepository.findAllById(request.getRoles());
		user.setRoles(new HashSet<>(roles));

		return userMapper.toUserResponse(userRepository.save(user));
	}

	public void deleteUser(String userId) {
		userRepository.deleteById(userId);
	}

	public UserResponse getMyInfo() {
		var context = SecurityContextHolder.getContext();
		String name = context.getAuthentication().getName();
		User user = userRepository.findByUsername(name).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTS));

		return userMapper.toUserResponse(user);
	}
}
