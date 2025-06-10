package com.personal_admin.application.service;

import com.personal_admin.application.model.response.EndpointInfo;

import java.util.List;

public interface EndpointApplicationService {
    List<EndpointInfo> listAllEndpoints();
}
