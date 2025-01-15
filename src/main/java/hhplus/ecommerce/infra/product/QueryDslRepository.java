package hhplus.ecommerce.infra.product;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hhplus.ecommerce.domain.product.Product;

import hhplus.ecommerce.domain.product.QProduct;
import hhplus.ecommerce.domain.product.QProductStock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static hhplus.ecommerce.domain.product.QProduct.product;


@Component
public class QueryDslRepository {

    private final JPAQueryFactory queryFactory;


    public QueryDslRepository(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    public Page<Product> findByProductWithStockByFilter(String name , Long minPrice , Long maxPrice , Pageable pageable) {
        QProduct product = QProduct.product;
        QProductStock productStock = QProductStock.productStock;

        JPQLQuery<Product> query = queryFactory
                .selectFrom(product)
                .leftJoin(product.productStock, productStock).fetchJoin()
                .where(
                        nameContains(name),
                        priceBetween(minPrice , maxPrice)
                )
                .orderBy(getOrderSpecifiers(pageable.getSort()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize());


        List<Product> content = query.fetch();
        long total = query.fetchCount();

        return new PageImpl<>(content,pageable,total);
    }

    private BooleanExpression nameContains(String productName) {
        return productName != null ? product.name.containsIgnoreCase(productName) : null;
    }

    private BooleanExpression priceBetween(Long minPrice , Long maxPrice) {
        if(minPrice == null && maxPrice == null) return null;
        if(minPrice == null) return product.price.loe(maxPrice);
        if(maxPrice == null) return product.price.goe(minPrice);
        return product.price.between(minPrice , maxPrice);
    }

    private OrderSpecifier<?>[] getOrderSpecifiers(Sort sort) {
        List<OrderSpecifier<?>> orders = new ArrayList<>();

        for(Sort.Order order : sort) {
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            PathBuilder<Product> pathBuilder = new PathBuilder<>(product.getType() , product.getMetadata());
            orders.add(new OrderSpecifier<>(direction , pathBuilder.getString(order.getProperty())));
        }
        return orders.toArray(new OrderSpecifier[0]);
    }
}
