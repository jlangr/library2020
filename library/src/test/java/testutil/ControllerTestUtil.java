package testutil;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

public class ControllerTestUtil {
    public static <T> T resultContent(MvcResult resultActions, Class<T> valueType)
            throws UnsupportedEncodingException, JsonProcessingException {
        return new ObjectMapper().readValue(contentAsString(resultActions), valueType);
    }

    public static String contentAsString(MvcResult resultActions) throws UnsupportedEncodingException {
        return resultActions.getResponse().getContentAsString();
    }

    public static MockHttpServletRequestBuilder postAsJson(String url, Object content) {
        try {
            return post(url)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(new ObjectMapper().writeValueAsString(content));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
