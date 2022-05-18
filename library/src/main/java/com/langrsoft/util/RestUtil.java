package com.langrsoft.util;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RestUtil {
    private RestUtil() {}

    public static RestTemplate createRestTemplate() {
        var template = new RestTemplate();

        var messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setPrettyPrint(false);
        template.getMessageConverters()
                .removeIf(m -> m.getClass().isAssignableFrom(MappingJackson2HttpMessageConverter.class));
        template.getMessageConverters().add(messageConverter);
        return template;
    }
}
