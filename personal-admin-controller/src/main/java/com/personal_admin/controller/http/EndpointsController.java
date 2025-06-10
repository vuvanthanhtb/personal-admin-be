package com.personal_admin.controller.http;

import com.personal_admin.application.model.response.EndpointInfo;
import com.personal_admin.application.service.EndpointApplicationService;
import com.personal_admin.controller.model.vo.ResultMessage;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class EndpointsController {

    @Resource
    private EndpointApplicationService endpointApplicationService;


    @GetMapping("/endpoints")
    public ResultMessage<List<EndpointInfo>> listAllEndpoints() {
        List<EndpointInfo> endpoints = this.endpointApplicationService.listAllEndpoints();
        ResultMessage<List<EndpointInfo>> response = new ResultMessage<>();
        response.setResult(endpoints);
        response.setCode("API_LIST_SUCCESS");
        response.setMessage("Get list api success");
        return response;
    }
}
