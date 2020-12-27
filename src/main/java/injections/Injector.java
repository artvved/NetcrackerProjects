package injections;

import annotations.AutoInjectable;
import annotations.Configuration;
import repository.ContractRepository;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Configuration(packages = {"repository", "validators"})
public class Injector {
    public static <T> T inject(T object) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        // ContractRepository contractRepository = new ContractRepository();
        Class c = object.getClass();
        Field[] fields = c.getDeclaredFields();

        for (Field f : fields) {
            if (f.isAnnotationPresent(AutoInjectable.class)) {
                System.out.println("da" + f.getName());
                Class<?> fieldType = f.getType();


                for (String pack : new String[]{"repository", "validators"}) {
                    List<Class<?>> classesInPackage = ClassFinder.processDirectory(
                            new File("src/main/java/" + pack), pack);
                    List<Class<?>> suitableForInj = new ArrayList<>();
                    for (Class<?> classInP : classesInPackage) {
                        if (classInP.getInterfaces().length == 1 &&
                                classInP.getInterfaces()[0].equals(fieldType)) {
                            suitableForInj.add(classInP);
                        }
                    }
                    if (!Collection.class.isAssignableFrom(fieldType)) {
                        if (suitableForInj.size() == 1) {
                            f.setAccessible(true);
                            f.set(object, suitableForInj.get(0).getDeclaredConstructor().newInstance());

                        } else
                            throw new ClassNotFoundException("Class not found or found more than 1. Can not inject");

                    }else {
                        List<Object> injection=new ArrayList<>();
                        for(Class<?> sc:suitableForInj){
                            injection.add(sc.getDeclaredConstructor().newInstance());
                        }
                        f.setAccessible(true);
                        f.set(object, injection);
                    }
                }
            }

        }

        System.out.println();

        return object;
    }


}
