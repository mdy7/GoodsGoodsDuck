package spharos.nu.chat.domain.chat.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import spharos.nu.chat.domain.chat.dto.request.ChatRequestDto;
import spharos.nu.chat.domain.chat.dto.response.ChatResposeDto;
import spharos.nu.chat.domain.chat.entity.ChatMessage;
import spharos.nu.chat.domain.chat.service.ChatService;
import spharos.nu.chat.global.apiresponse.ApiResponse;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/v1/chat-n")
@Tag(name = "ChatN", description = "토큰 검증이 필요없는 채팅 컨트롤러")
public class ChatNController {

	private final ChatService chatService;

	@Operation(summary = "채팅 내역 조회", description = "채팅 내역을 조회합니다.")
	@GetMapping(value = "/{chatRoomId}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatResposeDto> getChatMessages(
		@PathVariable(value = "chatRoomId") String chatRoomId
	) {
		log.info("chatN 컨트롤러 채팅 내역 조회");
		return chatService.getChatMessages(chatRoomId).subscribeOn(Schedulers.boundedElastic());
	}

	@Operation(summary = "마지막 메시지 조회", description = "마지막 메시지를 조회합니다.")
	@GetMapping("/{chatRoomId}/last")
	public ResponseEntity<ApiResponse<ChatResposeDto>> getLastMessage(
		@PathVariable(value = "chatRoomId") String chatRoomId
	) {
		return ApiResponse.success(chatService.getLastMessage(chatRoomId),"마지막 메시지 조회 성공");
	}

	@Operation(summary = "새로운 메시지 조회", description = "실시간으로 도착하는 새로운 메시지를 조회합니다")
	@GetMapping(value="/{chatRoomId}/new", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ChatResposeDto> getNewMessage(
		@PathVariable(value = "chatRoomId") String chatRoomId
	) {
		return chatService.getNewMessage(chatRoomId);
	}

}
