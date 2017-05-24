import java.io.File;

public class Find {
    private static boolean isRecursive;
    private static File dir;
    private static String regexp;
    public static void main(String[] args) {
        parseArgs(args);
        findFile(dir);
    }

    //функция, которую вызовем рекурсивно в том случае, если попадется директория, а не файл
    private static void findFile(File directory) {
        for(File file : directory.listFiles()) {
            if (file.isDirectory()) {
                //если был -r, то вызываем эту же функцию с найденной директорией
                if (isRecursive) {
                    findFile(file);
                }
            } else {
                /*
                Функция matches() проверяет, удовлетворяет ли заданная строка регулярному выражению,
                переданному в скобках.
                 */
                if (file.getName().matches(regexp)) {
                    System.out.println(file.getAbsolutePath());
                }
            }
        }
    }

    private static void parseArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Incorrect input.");
            System.exit(0);
        }

        /*
        обычно все ключи указываются сразу. Поэтому возможны варианты:
            либо без ключа;
            либо ключ -r;
            либо ключ -d;
            либо ключ -rd;
            либо -dr;
         */
        if (args[0].matches("-.*")) {
            if (args[0].contains("r")) {
                isRecursive = true;
            }
            if (args[0].contains("d")) {
                if (args.length != 3) {
                    System.out.println("Incorrect input.");
                    System.exit(0);
                } else {
                    dir = new File(args[1]);
                    if (!dir.isDirectory()) {
                        System.out.println(args[1] + " is not directory");
                        System.exit(0);
                    }
                    regexp = args[2];
                }
            } else {
                if (args.length != 2) {
                    System.out.println("Incorrect input.");
                    System.exit(0);
                } else {
                    //точка - текущая директория
                    dir = new File(".");
                    regexp = args[1];
                }
            }
        } else {
            if (args.length != 1) {
                System.out.println("Incorrect input.");
                System.exit(0);
            } else {
                dir = new File(".");
                regexp = args[0];
            }
        }
    }
}
