package com.innovator.innovator.controllers;

import com.innovator.innovator.services.ExecService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/excel")
@AllArgsConstructor
public class ExcelController {
    private ExecService execService;

    @GetMapping("/users")
    public ResponseEntity<Resource> getUsersFile() {
        String filename = "Users innovator app.xlsx";
        InputStreamResource file = new InputStreamResource(execService.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
