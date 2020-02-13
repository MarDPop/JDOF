package main;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * Base Test class
 */
public class Test {

    /**
     * List of tests in
     */
    private final List<Method> testCases = new ArrayList<Method>();

    /**
     * List of passed tests
     */
    private final boolean[] pass;

    public Test() {
        for (Method m : this.getClass().getMethods()) {
            if (m.isAnnotationPresent(TestCase.class)) {
                testCases.add(m);
            }
        }
        
        pass = new boolean[testCases.size()];
    }

    /**
     * Static method that invokes all tests
     */
    public static void runAll() {
        System.out.println("****************************************");
        System.out.println("*      Marius Test Package v0.1        *");
        System.out.println("****************************************");
        try {
            for (Class c : getClasses("main.test")) { 
                if (Test.class.isAssignableFrom(c)) {
                    try {
                        ((Test) c.newInstance()).run();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (ClassNotFoundException | IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Runs all test cases within test and returns if test passed.
     * @return
     */
    public boolean run() {

        System.out.println("Now Testing: " + this.getClass());

        int i = 0;
        int passed = 0;

        for (Method m : testCases) {
            try {
                System.out.print("Running test case " + m.getName() + "... ");
                pass[i] = (Boolean) m.invoke(this);
                if(pass[i]) {
                    passed++;
                    System.out.println("pass");
                } else {
                    System.out.println("fail");
                }
                i++;
            } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }

        System.out.println(passed + "  test(s) out of " + i + " passed. " + (passed*100.0/i) + "%");

        return passed >= i;
    }

    /**
     * Checks two matrices of doubles to see if they match based on a tolerance
     * 
     * @param A matrix
     * @param B matrix
     * @param tol tolerance beneath which is zero 
     * @return
     */
    protected static boolean assertEquals(double[][] A, double[][] B, double tol) {
        boolean flag = true;
        if (A.length != B.length) {
            System.out.println("Matrix rows do not match.");
            flag = false;
        }
        if (A[0].length != B[0].length) {
            System.out.println("Matrix cols do not match.");
            flag = false;
        }

        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < B.length; j++) {
                if (!assertEquals(A[i][j], B[i][j], tol)) {
                    System.out.println("at index " + i + "," + j);
                    flag = false;
                }
            }
        }
        return flag;
    }

    /**
     * Checks two double arrays to see if they match based on tolerance
     * 
     * @param A
     * @param B
     * @param tol
     * @return
     */
    protected static boolean assertEquals(double[] A, double[] B, double tol) {
        boolean flag = true;
        if (A.length != B.length) {
            System.out.println("Matrix rows do not match.");
            flag = false;
        }

        for (int i = 0; i < A.length; i++) {
            if (!assertEquals(A[i], B[i], tol)) {
                System.out.println("at index " + i);
                flag = false;
            }
        }
        return flag;
    }

    /**
     * Checks to see if two doubles match based on tolerance
     * 
     * @param A
     * @param B
     * @param tol
     * @return
     */
    protected static boolean assertEquals(double A, double B, double tol) {
        boolean flag = true;

        if (Math.abs(A) < tol && Math.abs(B) < tol) {
        } else {
            double nonZeroTolerance = 0.5*(Math.abs(A) + Math.abs(B));
            if (tol < nonZeroTolerance) {
                if (Math.abs(A - B) > nonZeroTolerance) {
                    flag = false;
                }
            } else {
                if (Math.abs(A - B) > tol) {
                    flag = false;
                }
            }
        }
        if (!flag) {
            System.out.println("A = " + A + " , B = " + B);
        }
        return flag;
    }

    /**
     * Scans all classes accessible from the context class loader which belong to
     * the given package and subpackages.
     * 
     * @param packageName The base package
     * @return The classes
     * @throws ClassNotFoundException
     * @throws IOException
     * @throws URISyntaxException
     */
    public static Iterable<Class> getClasses(String packageName)
            throws ClassNotFoundException, IOException, URISyntaxException
    {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        String path = packageName.replace('.', '/');
        Enumeration<URL> resources = classLoader.getResources(path);
        List<File> dirs = new ArrayList<File>();
        while (resources.hasMoreElements())
        {
            URL resource = resources.nextElement();
            URI uri = new URI(resource.toString());
            dirs.add(new File(uri.getPath()));
        }
        List<Class> classes = new ArrayList<Class>();
        for (File directory : dirs)
        {
            classes.addAll(findClasses(directory, packageName));
        }

        return classes;
    }

    /**
     * Recursive method used to find all classes in a given directory and
     * subdirs.
     * 
     * @param directory
     *            The base directory
     * @param packageName
     *            The package name for classes found inside the base directory
     * @return The classes
     * @throws ClassNotFoundException
     */
    private static List<Class> findClasses(File directory, String packageName) throws ClassNotFoundException
    {
        List<Class> classes = new ArrayList<Class>();
        if (!directory.exists())
        {
            return classes;
        }
        File[] files = directory.listFiles();
        for (File file : files)
        {
            if (file.isDirectory())
            {
                classes.addAll(findClasses(file, packageName + "." + file.getName()));
            }
            else if (file.getName().endsWith(".class"))
            {
                classes.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
            }
        }
        return classes;
    }


}