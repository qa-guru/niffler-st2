package niffler.jupiter.extension;

import java.util.Date;

import niffler.api.HttpHandler;
import niffler.jupiter.annotation.Category;
import niffler.model.CategoryJson;
import niffler.model.ISpend;
import niffler.jupiter.annotation.GenerateSpend;
import niffler.model.SpendJson;
import niffler.utils.PropertyHandler;
import org.junit.jupiter.api.extension.*;

import static niffler.jupiter.extension.GenerateCategoryExtension.CATEGORY_NAMESPACE;

public class GenerateSpendExtension implements ParameterResolver, BeforeEachCallback {

    private final PropertyHandler props = new PropertyHandler("./src/test/resources/property.properties");

    public static final ExtensionContext.Namespace SPEND_SPACE = ExtensionContext.Namespace
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
        return extensionContext.getStore(SPEND_SPACE).get(props.get("spend.objname"), SpendJson.class);
    }

    @Override
    public void beforeEach(ExtensionContext context) {
        GenerateSpend annotation = context.getRequiredTestMethod()
                .getAnnotation(GenerateSpend.class);

        CategoryJson categoryJson = context.getStore(CATEGORY_NAMESPACE).get(props.get("category.objname"), CategoryJson.class);

        if (annotation != null) {
            SpendJson spend = new SpendJson();
            if(categoryJson != null) {
                spend.setUsername(categoryJson.getUsername());
                spend.setCategory(categoryJson.getCategory());
            } else {
                Category category = context.getElement().get().getAnnotation(GenerateSpend.class).category();
                spend.setUsername(category.username());
                spend.setCategory(category.category());
            }
            spend.setAmount(annotation.amount());
            spend.setDescription(annotation.description());
            spend.setSpendDate(new Date());
            spend.setCurrency(annotation.currency());

            ISpend response = httpHandler.executePost(props.get("request.spend.path"), spend);

            context.getStore(SPEND_SPACE).put(props.get("spend.objname"), response);
        }
    }

}
