package domain.core;

import com.loc.material.api.Material;

public class HoldingBuilder {
    private Material material = new Material("1", "", "1", "", "");
    private Branch branch = Branch.CHECKED_OUT;
    private int copyNumber = 1;

    public HoldingBuilder with(Material material) {
        this.material = material;
        return this;
    }

    public HoldingBuilder withClassification(String classification) {
        this.material = new Material(classification, "", classification, "", "");
        return this;
    }

    public HoldingBuilder branch(Branch branch) {
        this.branch = branch;
        return this;
    }

    public HoldingBuilder copyNumber(int copyNumber) {
        this.copyNumber = copyNumber;
        return this;
    }

    public Holding build() {
        return new Holding(material, branch, copyNumber);
    }
}