package niffler.jupiter;

import java.util.Date;

import niffler.api.HttpHandler;
import niffler.model.ISpend;
import niffler.model.SpendJson;
import niffler.utils.PropertyHandler;
import org.junit.jupiter.api.extension.*;

public class GenerateSpendExtension implements ParameterResolver, BeforeTestExecutionCallback {

    private final PropertyHandler props = new PropertyHandler("./src/test/resources/property.properties");

    public static ExtensionContext.Namespace NAMESPACE = ExtensionContext.Namespace
            .create(GenerateSpendExtension.class);

    private final HttpHandler httpHandler = new HttpHandler(props.get("spend.category.baseurl"));

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
        ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(SpendJson.class);
    }

    @Override
    public SpendJson resolveParameter(ParameterContext parameterContext,
        ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(NAMESPACE).get(props.get("spend.objname"), SpendJson.class);
    }

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        GenerateSpend annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateSpend.class);

        if (annotation != null) {
            SpendJson spend = new SpendJson();
            spend.setUsername(annotation.username());
            spend.setAmount(annotation.amount());
            spend.setDescription(annotation.description());
            spend.setCategory(annotation.category());
            spend.setSpendDate(new Date());
            spend.setCurrency(annotation.currency());

            ISpend response = httpHandler.executePost(props.get("request.spend.path"), spend);

            context.getStore(NAMESPACE).put(props.get("spend.objname"), response);
        }
    }
}
