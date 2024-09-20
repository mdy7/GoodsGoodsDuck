package spharos.nu.goods.domain.goods.dto.request;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class GoodsCreateDto {
	@Schema(description = "상품명")
	private String goodsName;
	@Schema(description = "상품설명")
	private String description;
	@Schema(description = "카테고리id")
	private Long categoryId;
	@Schema(description = "시작가격")
	private Long minPrice;
	@Schema(description = "경매시작일시")
	private LocalDateTime openedAt;
	@Schema(description = "경매마감일시")
	private LocalDateTime closedAt;
	@Schema(description = "선호거래방법")
	private byte wishTradeType;
	@Schema(description = "태그리스트")
	private List<TagDto> tags;
	@Schema(description = "이미지리스트")
	private List<ImageDto> images;

}
