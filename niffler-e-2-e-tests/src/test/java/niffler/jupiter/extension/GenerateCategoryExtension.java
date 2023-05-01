package niffler.jupiter.extension;

import niffler.api.HttpHandler;
import niffler.jupiter.annotation.Category;
import niffler.jupiter.annotation.GenerateSpend;
import niffler.model.CategoryJson;
import niffler.model.ISpend;
import niffler.utils.PropertyHandler;
import org.junit.jupiter.api.extension.*;

import java.lang.reflect.AnnotatedElement;
import java.util.Arrays;

public class GenerateCategoryExtension implements ParameterResolver, BeforeEachCallback {

    private final PropertyHandler props = new PropertyHandler("./src/test/resources/property.properties");

    public static final ExtensionContext.Namespace CATEGORY_NAMESPACE = ExtensionContext.Namespace
            .create(GenerateCategoryExtension.class);

    private final HttpHandler httpHandler = new HttpHandler(props.get("spend.category.baseurl"));

    @Override
    public void beforeEach(ExtensionContext context) {

        Category categoryAnnotation = getCategoryAnnotation(context);

        if (categoryAnnotation != null) {
            CategoryJson category = new CategoryJson();
            category.setCategory(categoryAnnotation.category());
            category.setUsername(categoryAnnotation.username());

            ISpend response = httpHandler.executePost(props.get("request.category.path"), category);

            context.getStore(CATEGORY_NAMESPACE).put(props.get("category.objname"), response);
        }
    }

    private Category getCategoryAnnotation(ExtensionContext context) {

        Category categoryAnnotation = null;
        AnnotatedElement annotatedElement = context.getElement().get();

        if(Arrays.stream(annotatedElement.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType() == Category.class)) {
            categoryAnnotation = annotatedElement.getAnnotation(Category.class);
        }
        if(Arrays.stream(annotatedElement.getDeclaredAnnotations())
                .anyMatch(annotation -> annotation.annotationType() == GenerateSpend.class)) {
            categoryAnnotation = annotatedElement.getAnnotation(GenerateSpend.class).category();
        }
        return categoryAnnotation;
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
