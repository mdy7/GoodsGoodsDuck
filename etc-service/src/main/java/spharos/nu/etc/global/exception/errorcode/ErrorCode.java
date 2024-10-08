package spharos.nu.etc.global.exception.errorcode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

	// 400 : 잘못된 요청
	REQUEST_PARAMETER(400, "요청 파라미터 값이 올바르지 않습니다."),
	WRONG_NUMBER(400, "인증번호가 일치하지 않습니다."),
	BIDDING_NOT_FOUND(400, "입찰자가 없습니다."),

	// 401 : 접근 권한이 없음
	NO_AUTHORITY(401, "접근 권한이 없습니다."),

	TOKEN_EXPIRED(401, "토큰이 만료되었습니다."),

	// 403: Forbidden
	PASSWORD_ERROR(403, "비밀번호가 일치하지 않습니다."),

	// 404: 잘못된 리소스 접근
	NOT_FOUND_ENTITY(404, "해당 객체를 찾지 못했습니다."),
	NOT_FOUND_USER(404, "존재하지 않는 사용자입니다."),

	//409 : 중복된 리소스
	ALREADY_EXIST_USER(409, "이미 존재하는 사용자입니다."),
	WINNING_BID_ALREADY_EXIST(409, "이미 낙찰된 상품입니다."),
	ALREADY_REVIEW_CREATE(409, "이미 작성한 후기입니다."),

	//500 : INTERNAL SERVER ERROR
	INTERNAL_SERVER_ERROR(500, "서버 내부 에러입니다.");

	private final Integer status;
	private final String message;

}
