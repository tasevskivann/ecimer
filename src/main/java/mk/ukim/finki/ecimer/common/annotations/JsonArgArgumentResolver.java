package mk.ukim.finki.ecimer.common.annotations;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.MissingNode;
import com.google.common.base.Strings;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class JsonArgArgumentResolver implements HandlerMethodArgumentResolver {
    private static final String JSONBODYATTRIBUTE = "JSON_REQUEST_BODY";

    @Autowired
    private ObjectMapper om;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(JsonArg.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

        String jsonBody = getRequestBody(webRequest);

        JsonNode rootNode;
        try {
            rootNode = om.readTree(jsonBody);
        } catch (JsonParseException e) {
            throw new HttpMessageNotReadableException(e.getMessage(), e);
        }

        String jsonArgName = parameter.getParameterAnnotation(JsonArg.class).name();
        jsonArgName = Strings.isNullOrEmpty(jsonArgName) ? parameter.getParameterName() : jsonArgName;
        JsonNode node = rootNode.path(jsonArgName);
        if (node instanceof MissingNode) {
            return null;
        }

        return om.readValue(node.toString(), om.constructType(parameter.getGenericParameterType()));
    }

    private String getRequestBody(NativeWebRequest webRequest) {
        HttpServletRequest servletRequest = webRequest.getNativeRequest(HttpServletRequest.class);

        String jsonBody = (String) webRequest.getAttribute(JSONBODYATTRIBUTE, NativeWebRequest.SCOPE_REQUEST);
        if (jsonBody == null) {
            try {
                jsonBody = IOUtils.toString(servletRequest.getInputStream());
                webRequest.setAttribute(JSONBODYATTRIBUTE, jsonBody, NativeWebRequest.SCOPE_REQUEST);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return jsonBody;
    }
}
