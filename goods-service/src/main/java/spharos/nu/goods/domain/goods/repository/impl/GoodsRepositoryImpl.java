package spharos.nu.goods.domain.goods.repository.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;
import spharos.nu.goods.domain.goods.dto.response.GoodsCodeDto;
import spharos.nu.goods.domain.goods.dto.response.QGoodsCodeDto;
import spharos.nu.goods.domain.goods.entity.Goods;
import spharos.nu.goods.domain.goods.entity.QGoods;
import spharos.nu.goods.domain.goods.repository.GoodsRepositoryCustom;
import spharos.nu.goods.global.exception.CustomException;
import spharos.nu.goods.global.exception.errorcode.ErrorCode;

@RequiredArgsConstructor
public class GoodsRepositoryImpl implements GoodsRepositoryCustom {
	private final JPAQueryFactory queryFactory;

	@Override
	public Page<GoodsCodeDto> findAllGoods(Long categoryPk, boolean isTradingOnly, Pageable pageable) {
		QGoods goods = QGoods.goods;

		BooleanBuilder whereClause = new BooleanBuilder();
		whereClause
			.and(goods.categoryId.eq(categoryPk))
			.and(goods.isDisable.eq(false));

		if (isTradingOnly) {
			// true 일 때 tradingStatus 가 0
			whereClause.and(goods.tradingStatus.eq((byte)0));
		}

		Long totalCount = getTotalCount(goods, whereClause);
		List<GoodsCodeDto> content = getContent(goods, whereClause,
			pageable);

		return new PageImpl<>(content, pageable, totalCount);
	}

	@Override
	public Page<GoodsCodeDto> findAllGoods(String uuid, byte status, Pageable pageable) {

		QGoods goods = QGoods.goods;

		List<GoodsCodeDto> goodsList = queryFactory
			.select(new QGoodsCodeDto(goods.goodsCode))
			.from(goods)
			.where(goods.sellerUuid.eq(uuid), tradingStatusEq(status))
			.orderBy(goods.createdAt.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		Long total = queryFactory
			.select(goods.count())
			.from(goods)
			.where(goods.sellerUuid.eq(uuid), tradingStatusEq(status))
			.fetchOne();

		long totalCount = total != null ? total : 0;

		return new PageImpl<>(goodsList, pageable, totalCount);
	}

	private Long getTotalCount(QGoods goods, BooleanBuilder whereClause) {

		return queryFactory.select(goods.count()).from(goods).where(whereClause).fetchOne();
	}

	private <T> List<GoodsCodeDto> getContent(QGoods goods, BooleanBuilder whereClause, Pageable pageable) {

		JPAQuery<GoodsCodeDto> query = queryFactory.select(
				new QGoodsCodeDto(goods.goodsCode)).from(goods)
			.where(whereClause);

		if (pageable.getSort().isSorted()) {
			Sort.Order order = pageable.getSort().iterator().next();
			PathBuilder<Goods> goodsPath = new PathBuilder<>(goods.getType(), goods.getMetadata());
			Order direction = Sort.Direction.ASC.equals(order.getDirection()) ? Order.ASC : Order.DESC;

			OrderSpecifier<?> orderSpecifier = switch (order.getProperty()) {
				case "createdAt" -> new OrderSpecifier<>(Order.DESC, goodsPath.getDateTime(order.getProperty(),
					LocalDateTime.class));
				case "closedAt" -> new OrderSpecifier<>(Order.ASC, goodsPath.getDateTime(order.getProperty(),
					LocalDateTime.class));
				case "etc" -> new OrderSpecifier<>(direction, goodsPath.getDateTime(order.getProperty(),
					LocalDateTime.class));
				default -> throw new CustomException(ErrorCode.INVALID_REQUEST_PARAM);
			};

			query.orderBy(orderSpecifier);
		}

		query.offset(pageable.getOffset());
		query.limit(pageable.getPageSize());

		return query.fetch();
	}

	private BooleanExpression tradingStatusEq(Byte status) {
		QGoods goods = QGoods.goods;

		if (status == null) {
			return null; // status가 null인 경우 조건을 추가하지 않음
		}

		// 경매완료탭은 status가 2 또는 3인 상품을 보여줌
		if (status == 2 || status == 3) {
			return goods.tradingStatus.eq((byte)2).or(goods.tradingStatus.eq((byte)3));
		}

		return goods.tradingStatus.eq(status);
	}
}