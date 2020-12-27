package injections;

import annotations.AutoInjectable;
import annotations.Configuration;
import repository.ContractRepository;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 * injects into given object autoinjectable instances of fields.
 * Instances are found in packages which are provided by configuration annotation of Injector
 */
@Configuration(packages = {"repository", "validators"})
public class Injector {
    public static <T> T inject(T object) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {

        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(AutoInjectable.class)) {
                Class<?> fieldType = f.getType();
                Type suitableType;
                if (!Collection.class.isAssignableFrom(fieldType)) {
                    suitableType = f.getType();
                } else {
                    suitableType = f.getGenericType();
                    ParameterizedType aType = (ParameterizedType) suitableType;
                    Type[] fieldArgTypes = aType.getActualTypeArguments();
                    suitableType = fieldArgTypes[0];
                }
                String[] packages=Injector.class.getAnnotation(Configuration.class).packages();
                List<Class<?>> suitableClassesForInj = new ArrayList<>();
                for (String pack : packages) {
                    List<Class<?>> classesInPackage = ClassFinder.processDirectory(
                            new File("src/main/java/" + pack), pack);

                    for (Class<?> classInP : classesInPackage) {
                        if (classInP.getInterfaces().length == 1 &&
                                classInP.getInterfaces()[0].equals(suitableType)) {
                            suitableClassesForInj.add(classInP);
                        }
                    }
                }
                if (!Collection.class.isAssignableFrom(fieldType)) {
                    if (suitableClassesForInj.size() == 1) {
                        f.setAccessible(true);
                        f.set(object, suitableClassesForInj.get(0).getDeclaredConstructor().newInstance());

                    } else
                        throw new ClassNotFoundException("Class not found or found more than 1. Can not inject");

                } else {
                    List<Object> injection = new ArrayList<>();
                    for (Class<?> sc : suitableClassesForInj) {
                        injection.add(sc.getDeclaredConstructor().newInstance());
                    }
                    f.setAccessible(true);
                    f.set(object, injection);

                }

            }

        }



        return object;
    }


}
