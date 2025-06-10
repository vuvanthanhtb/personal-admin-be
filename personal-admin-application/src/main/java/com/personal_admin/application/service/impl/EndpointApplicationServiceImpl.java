package com.personal_admin.application.service.impl;

import com.personal_admin.application.model.response.EndpointInfo;
import com.personal_admin.application.service.EndpointApplicationService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PathPatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EndpointApplicationServiceImpl implements EndpointApplicationService {
    final String[] ignorePaths = {"/swagger-ui.html", "/v3/api-docs/swagger-config", "/v3/api-docs.yaml", "/v3/api-docs"};
    final String[] ignoreControllers = {"EndpointsController", "AuthController"};
    @Resource
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public List<EndpointInfo> listAllEndpoints() {
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

        return endpoints;
    }
}
