package com.bravapro.settings.adapter.web.resource;

import com.bravapro.core.infrastructure.web.RestUtils;
import com.bravapro.settings.application.command.RemoveBusinessUnitLogoCommand;
import com.bravapro.settings.application.command.UpdateBusinessUnitCommand;
import com.bravapro.settings.application.command.UploadBusinessUnitLogoCommand;
import com.bravapro.settings.application.service.BusinessUnitApplicationService;
import com.bravapro.settings.domain.model.BusinessUnit;

import java.io.IOException;
import java.util.UUID;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/** Só comando — leitura em {@link BusinessUnitQueryResource}. */
@RestController
@RequestMapping("/api/settings/business-unit")
@RequiredArgsConstructor
public class BusinessUnitResource {

    private static final String ENTITY_NAME = "businessUnit";

    private final BusinessUnitApplicationService businessUnitApplicationService;

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @Valid @RequestBody UpdateBusinessUnitCommand command) {
        businessUnitApplicationService.when(command.id(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }

    @PostMapping(value = "/{id}/logo", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> uploadLogo(@PathVariable UUID id, @RequestParam("file") MultipartFile file)
            throws IOException {
        BusinessUnit unit = businessUnitApplicationService.when(new UploadBusinessUnitLogoCommand(
                id, file.getBytes(), file.getContentType(), file.getOriginalFilename()));
        return RestUtils.message().updated(ENTITY_NAME, unit);
    }

    @DeleteMapping("/{id}/logo")
    public ResponseEntity<Void> removeLogo(@PathVariable UUID id) {
        businessUnitApplicationService.when(new RemoveBusinessUnitLogoCommand(id));
        return RestUtils.message().updated(ENTITY_NAME, id);
    }
}
