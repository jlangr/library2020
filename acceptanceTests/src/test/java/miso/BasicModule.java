package miso;

import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(MisoMathUtil.class).toInstance(new MisoMathUtil());
    }
}
