package com.bravapro.core.infrastructure.web;

import org.springframework.http.HttpHeaders;
import org.springframework.util.MultiValueMap;

class HeaderMessageBuilder {

    MultiValueMap<String, String> createAlert(String message) {
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.add(WebHeaderConstants.X_BRAVAPRO_ALERT, message);
        return headers;
    }

    MultiValueMap<String, String> createAlert(String message, String param) {
        MultiValueMap<String, String> headers = createAlert(message);
        headers.add(WebHeaderConstants.X_BRAVAPRO_PARAMS, param);
        return headers;
    }
}
