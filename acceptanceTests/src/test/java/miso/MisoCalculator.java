package miso;

import com.google.inject.Guice;
import com.google.inject.Injector;
import io.cucumber.guice.ScenarioScoped;

@ScenarioScoped
public class MisoCalculator {
    @com.google.inject.Inject
    private MisoMathUtil mathUtil;

    public MisoCalculator() {
        Injector injector = Guice.createInjector(new BasicModule());
        mathUtil = injector.getInstance(MisoMathUtil.class);
    }

    public void setOperands(int a, int b) {
        mathUtil.setOperand_1(a);
        mathUtil.setOperand_2(b);
    }

    public void add() {
        mathUtil.add();
    }

    public int getResult() {
        return mathUtil.getAccumulator();
    }
}
