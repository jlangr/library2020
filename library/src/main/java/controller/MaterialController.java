package controller;

import com.loc.material.api.Material;
import com.loc.material.api.MaterialType;
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
