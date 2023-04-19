package niffler.jupiter;

import niffler.api.HttpHandler;
import niffler.model.CategoryJson;
import niffler.model.ISpend;
import niffler.utils.PropertyHandler;
import org.junit.jupiter.api.extension.*;

public class GenerateCategoryExtension implements ParameterResolver, BeforeEachCallback {

    private final PropertyHandler props = new PropertyHandler("./src/test/resources/property.properties");

    public static final ExtensionContext.Namespace CATEGORY_NAMESPACE = ExtensionContext.Namespace
            .create(GenerateCategoryExtension.class);

    private final HttpHandler httpHandler = new HttpHandler(props.get("spend.category.baseurl"));

    @Override
    public void beforeEach(ExtensionContext context) {

        Category categoryAnnotation = context.getRequiredTestMethod().getAnnotation(Category.class);

        if (categoryAnnotation != null) {
            CategoryJson category = new CategoryJson();
            category.setCategory(categoryAnnotation.category());
            category.setUsername(categoryAnnotation.username());

            ISpend response = httpHandler.executePost(props.get("request.category.path"), category);

            context.getStore(CATEGORY_NAMESPACE).put(props.get("category.objname"), response);
        }
    }

    @Override
    public boolean supportsParameter(ParameterContext parameterContext,
                                     ExtensionContext extensionContext) throws ParameterResolutionException {
        return parameterContext.getParameter().getType().isAssignableFrom(CategoryJson.class);
    }

    @Override
    public CategoryJson resolveParameter(ParameterContext parameterContext,
                                      ExtensionContext extensionContext) throws ParameterResolutionException {
        return extensionContext.getStore(CATEGORY_NAMESPACE).get(props.get("category.objname"), CategoryJson.class);
    }

}
