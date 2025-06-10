package com.personal_admin.application.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndpointInfo {
    String url;
    String methods;
    //    String handlerMethod;
    String controller;
}
