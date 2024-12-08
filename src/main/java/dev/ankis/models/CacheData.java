package dev.ankis.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RequiredArgsConstructor
@Getter
@Accessors(chain = true)
@RedisHash(value = "cacheData", timeToLive = 3600l)
public class CacheData {
    @Id
    private final String key;
    @Indexed
    private final String value;

}
