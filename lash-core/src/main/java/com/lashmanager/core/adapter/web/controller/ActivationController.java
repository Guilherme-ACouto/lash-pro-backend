package com.lashmanager.core.adapter.web.controller;

import com.lashmanager.core.adapter.web.dto.ActivationResponse;
import com.lashmanager.core.application.command.ActivateAccountCommand;
import com.lashmanager.core.application.service.ActivateAccountApplicationService;
import com.lashmanager.core.domain.port.in.ActivateAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/activation")
@RequiredArgsConstructor
public class ActivationController {

  private final ActivateAccountApplicationService activateAccountApplicationService;

  @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
  public ResponseEntity<ActivationResponse> activate(@RequestParam("key") String activationKey) {
    ActivateAccountUseCase.ActivationResult result =
        activateAccountApplicationService.when(new ActivateAccountCommand(activationKey));
    return ResponseEntity.ok(new ActivationResponse(result.email(), result.tenantId()));
  }
}
