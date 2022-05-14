package domain.core;

import com.loc.material.api.Material;

public class HoldingBuilder {
    private Material material = new Material("1", "", "1", "", "");

    public HoldingBuilder with(Material material) {
        this.material = material;
        return this;
    }

    public HoldingBuilder withClassification(String classification) {
        this.material = new Material(classification, "", classification, "", "");
        return this;
    }

    public Holding create() {
        return new Holding(material, Branch.CHECKED_OUT, 1);
    }
}