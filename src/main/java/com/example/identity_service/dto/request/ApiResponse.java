package com.example.identity_service.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
// những field nào có giá trị null sẽ không được serialize ra JSON, giúp
// response gọn hơn
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
	int code = 201;
	String message;
	T result;
}
