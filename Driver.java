import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Driver {
    public static void main(String args[]) throws ClassNotFoundException, SecurityException, NoSuchMethodException,
            IllegalArgumentException, IllegalAccessException, InvocationTargetException {
        System.out.println("Programs avaliable:");
        File dir = new File(System.getProperty("user.dir"));
        ArrayList<String> files = new ArrayList<>();
        files.addAll(Arrays.asList(dir.list()));
        for (String i : files) {
            if (!i.equals("Driver.java") && i.contains(".java")) {
                System.out.println(i.substring(0, i.length() - 5));
            }
        }
        String input = "";
        Scanner in = new Scanner(System.in);
        Class<?> cls;
        Method meth;
        boolean repeat = true;
        String[] params = null;
        while (!input.equals("quit")) {
            System.out.print("What option would you like to choose? (enter quit to quit): ");
            input = in.nextLine();
            try {
                input = files.stream().filter((input + ".java")::equalsIgnoreCase).findFirst().get();
                input = input.substring(0, input.length() - 5);
                cls = Class.forName(input);

                meth = cls.getMethod("main", String[].class);
                while (repeat) {
                    try {
                        System.out.print("Enter the input separated by spaces (if input is required): ");
                        params = in.nextLine().split(" ");
                        meth.invoke(null, (Object) params);
                        repeat = false;
                    } catch (Exception e) {
                        System.out.println(e + " has occured, try again");
                        repeat = true;
                    }
                }
                repeat = true;
            } catch (Exception e) {
                System.out.println("No such value present");
            }
        }
        in.close();
    }
}
