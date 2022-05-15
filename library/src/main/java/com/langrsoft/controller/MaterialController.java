package com.langrsoft.controller;

import com.langrsoft.external.MaterialType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/material")
public class MaterialController {
    @GetMapping(value = "/{materialType}")
    public MaterialType get(@PathVariable("materialType") String materialType) {
        return MaterialType.valueOf(materialType);
    }
}
