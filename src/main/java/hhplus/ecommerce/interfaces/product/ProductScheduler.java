package hhplus.ecommerce.interfaces.product;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductScheduler {
    private final CacheManager cacheManager;

    @Scheduled(cron = "0 0 0 * * *") // 매일 자정에 evict  이용한 캐시 초기화
    public void evictPopularProductCache() {
        cacheManager.getCache("popular_products").clear();
    }
}
