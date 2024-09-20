package spharos.nu.auth.global.exception.erroresponse;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class ErrorResponse<T> {
	private final Integer status;
	private final T result;
	private final String message;
}
