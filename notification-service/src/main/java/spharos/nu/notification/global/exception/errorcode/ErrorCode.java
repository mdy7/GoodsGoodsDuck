package spharos.nu.notification.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400 : 잘못된 요청
	MISSING_PATH_VARIALBE(400,"pathVariable이 누락되었습니다"),
	MISSING_QUERY_PARAM(400,"QueryParameter이 누락되었습니다"),
	MISSING_BODY_REQUEST(400,"RequestBody가 누락되었습니다"),
	INVALID_REQUEST_BODY(400, "RequestBody가 유효하지 않습니다"),
	INVALID_REQUEST_PARAM(400, "RequestParameter가 유효하지 않습니다"),
	INVALID_REQUEST_METHOD(400, "요청 메서드가 유효하지 않습니다"),
	TYPE_MISMATCH(400,"요청 데이터 중 유효하지 않은 타입이 있습니다."),
	WRONG_NUMBER(400, "인증번호가 일치하지 않습니다."),
	FCM_SEND_FAIL(400, "FCM 전송에 실패하였습니다."),

	// 401 : 접근 권한이 없음
	NO_AUTHORITY(401, "접근 권한이 없습니다."),
	TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),

	// 403: Forbidden
	PASSWORD_ERROR(403, "비밀번호가 일치하지 않습니다."),

	// 404: 잘못된 리소스 접근
	NOT_FOUND_GATEWAY(404, "존재하지 않는 경로입니다."),
	NOT_FOUND_ENTITY(404, "해당 객체를 찾지 못했습니다."),
	NOT_FOUND_USER(404, "존재하지 않는 사용자입니다."),
	NOT_FOUND_GOODS(404, "존재하지 않는 상품입니다."),
	NOT_FOUND_LIKE(404,  "해당 상품에 대한 좋아요를 찾을 수 없습니다."),
	NOT_FOUND_NOTIFICATION(404, "존재하지 않는 알림입니다."),
	NOT_FOUND_USER_NOTIFICATION_INFO(404, "유저 알림정보가 존재하지 않습니다."),

	//409 : 중복된 리소스
	ALREADY_EXIST_USER(409, "이미 존재하는 사용자입니다."),
	ALREADY_EXIST_LIKE(409, "이미 좋아요를 누른 상품입니다."),

	//500 : INTERNAL SERVER ERROR
	INTERNAL_SERVER_ERROR(500, "서버 내부 에러입니다.");

	private final Integer status;
	private final String message;

}
