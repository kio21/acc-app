package com.example.test;

import org.jetbrains.annotations.NotNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public final class Helper {

    private Helper() {
    }

    public static <T> void verifyRequestMapping(Class<T> controller, String callPath) {
        RequestMapping annotation = controller.getAnnotation(RequestMapping.class);
        assertNotNull(annotation);
        assertTrue(Arrays.asList(annotation.value()).contains(callPath));
    }

    @NotNull
    public static <K, V> MultiValueMap<K, V> toMultiValueMap(Map<K, V> map) {
        final MultiValueMap<K, V> multiMap = new LinkedMultiValueMap<>(map.size());
        map.forEach(multiMap::add);
        return multiMap;
    }
}
