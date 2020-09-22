package miso;


//@io.cucumber.guice.ScenarioScoped
public class MisoMathUtil {
    private int operand_1;
    private int operand_2;
    private int accumulator;

    public void add() {
        this.accumulator = this.operand_1 + this.operand_2;
    }

    public void setOperand_1(int operand_1) {
        this.operand_1 = operand_1;
    }

    public void setOperand_2(int operand_2) {
        this.operand_2 = operand_2;
    }

    public int getAccumulator() {
        return accumulator;
    }
}
