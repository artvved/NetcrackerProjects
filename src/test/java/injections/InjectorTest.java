package injections;

import annotations.AutoInjectable;
import domain.contracts.Contract;
import fileHandlers.CSVInfoLoader;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;
import repository.BubbleRepositorySorter;
import repository.ContractRepository;
import repository.IRepositorySorter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.logging.FileHandler;

public class InjectorTest extends TestCase {
    @Test
    public void testInject() throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ContractRepository contractRepository=new ContractRepository();
        Injector.inject(contractRepository);
        Assert.assertNotNull(contractRepository.getSorter());

        CSVInfoLoader csvInfoLoader=new CSVInfoLoader();
        Injector.inject(csvInfoLoader);
        Assert.assertNotNull(csvInfoLoader.getValidators());

    }

}