package com.personal_admin.controller.http;

import com.personal_admin.application.model.response.EndpointInfo;
import com.personal_admin.controller.model.vo.ResultMessage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class EndpointsController {
    final String[] ignorePaths = {"/swagger-ui.html", "/v3/api-docs/swagger-config", "/v3/api-docs.yaml", "/v3/api-docs"};
    final String[] ignoreControllers = {"EndpointsController", "AuthController"};
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @GetMapping("/endpoints")
    public ResultMessage<List<EndpointInfo>> listAllEndpoints() {
        List<EndpointInfo> endpoints = new ArrayList<>();
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();

        for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
            RequestMappingInfo mappingInfo = entry.getKey();
            HandlerMethod handlerMethod = entry.getValue();

            if (Arrays.asList(ignoreControllers).contains(handlerMethod.getBeanType().getSimpleName())) {
                continue;
            }

            PathPatternsRequestCondition patternsCondition = mappingInfo.getPathPatternsCondition();
            if (patternsCondition == null) {
                continue;
            }

            Set<String> patterns = patternsCondition.getPatternValues();
            if (patterns.isEmpty()) {
                continue;
            }

            Set<String> methods = mappingInfo.getMethodsCondition().getMethods()
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.toSet());

            if (patterns.stream().anyMatch(pattern -> pattern.equals("/error"))) {
                continue;
            }

            for (String pattern : patterns) {
                if (!Arrays.asList(ignorePaths).contains(pattern)) {
                    for (String method : methods) {
                        endpoints.add(new EndpointInfo(
                                pattern,
                                method,
                                // handlerMethod.getMethod().getName(),
                                handlerMethod.getBeanType().getSimpleName()
                        ));
                    }
                }

            }
        }

        ResultMessage<List<EndpointInfo>> response = new ResultMessage<>();
        response.setResult(endpoints);
        response.setCode("API_LIST_SUCCESS");
        response.setMessage("Get list api success");
        return response;
    }
}
