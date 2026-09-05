package com.lashmanager.core.infrastructure.web;

import org.springframework.http.HttpHeaders;

class HeaderMessageBuilder {

    HttpHeaders createAlert(String message) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(WebHeaderConstants.X_LASH_ALERT, message);
        return headers;
    }

    HttpHeaders createAlert(String message, String param) {
        HttpHeaders headers = createAlert(message);
        headers.add(WebHeaderConstants.X_LASH_PARAMS, param);
        return headers;
    }
}
