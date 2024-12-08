package dev.ankis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.ankis.entities.User;
import dev.ankis.models.CacheData;
import dev.ankis.repositories.CacheDataRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CacheDataService {
    private final CacheDataRepository cacheDataRepository;
    private final ObjectMapper mapper;

    public boolean saveData(String key, Object data) {
        try {
            CacheData cacheData = new CacheData(key, mapper.writeValueAsString(data));
            cacheDataRepository.save(cacheData);
            return true;
        } catch (JsonProcessingException exp) {
            log.error("Error occurred while saving to cache", exp);
            return false;
        }
    }

    public UserDetails getUserDetails(String email) {
        String user = cacheDataRepository.findById(email).map(x -> x.getValue()).orElse("");
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(user, User.class);
        } catch (JsonProcessingException exp) {
            log.error("Error occurred while parsing the cached data: {}", exp);
        }
        return null;
    }

    public <T> T getData(String key, Class<T> clazz) {
        String data = cacheDataRepository.findById(key).map(x -> x.getValue()).orElse("");
        try {
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            return mapper.readValue(data, clazz);
        } catch (JsonProcessingException exp) {
            log.error("Error occurred while parsing the cached data: {}", exp);
        }
        return null;
    }
}
