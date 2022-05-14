package util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.Assert.*;

public class ProxyTest {
    private ActionProxyFactory actionProxyFactory = new ActionProxyFactory();

    @Test
    public void testMessageKey() {
        HashMap<String, Object> params = new HashMap<String, Object>();
        params.put("foo", "200");
        HashMap<String, Object> extraContext = new HashMap<String, Object>();
        extraContext.put(ActionContext.PARAMETERS, params);
        try {
            ActionProxy proxy = actionProxyFactory.createActionProxy("",
                    MockConfigurationProvider.VALIDATION_ACTION_NAME, extraContext);
            ValueStack stack = ActionContext.getContext().getValueStack();
            ActionContext.setContext(new ActionContext(stack.getContext()));
            ActionContext.getContext().setLocale(Locale.US);
            proxy.execute();
            assertTrue(((ValidationAware) proxy.getAction()).hasFieldErrors());
            Map<String, List<String>> errors =
                    ((ValidationAware) proxy.getAction()).getFieldErrors();
            System.out.println("field errors: " + errors);
            List<String> fooErrors = errors.get("foo");
            assertEquals(1, fooErrors.size());
            String errorMessage = fooErrors.get(0);
            assertNotNull(errorMessage);
            assertEquals("Foo Range Message", errorMessage);
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
