package com.example.ip2c.playgrounds.cache;

import com.example.ip2c.playgrounds.api.model.IpInformationResponse;
import com.hazelcast.config.*;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CacheService {
    @Getter
    private IMap<String, IpInformationResponse> cache;

    @Value("${cache.name}")
    private String cacheName;

    @Value("${cache.ttl}")
    private int ttlInSeconds;

    @Value("${cache.max.idle}")
    private int maxIdleInSeconds;

    @Value("${cache.size}")
    private int cacheSize;

    @PostConstruct
    public void init() {
        HazelcastInstance hazelcastInstance = Hazelcast.newHazelcastInstance(fromConfig());
        cache = hazelcastInstance.getMap(cacheName);
    }

    public void put(String ip, IpInformationResponse ipInformationResponse) {
        cache.putIfAbsent(ip, ipInformationResponse);
    }

    public boolean invalidate(String ip) {
        return cache.evict(ip);
    }

    public boolean contains(String ip) {
        return cache.containsKey(ip);
    }

    public IpInformationResponse get(String ip) {
        return cache.get(ip);
    }

    private Config fromConfig() {
        Config config = new Config();
        MapConfig mapConfig = new MapConfig(cacheName);
        EvictionConfig evictionConfig = new EvictionConfig();

        evictionConfig.setEvictionPolicy(EvictionPolicy.LRU);
        evictionConfig.setMaxSizePolicy(MaxSizePolicy.PER_NODE);
        evictionConfig.setSize(cacheSize);
        mapConfig.setEvictionConfig(evictionConfig);

        mapConfig.setTimeToLiveSeconds(ttlInSeconds);
        mapConfig.setMaxIdleSeconds(maxIdleInSeconds);

        config.addMapConfig(mapConfig);

        return config;
    }
}
