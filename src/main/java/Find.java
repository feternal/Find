import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.ListIterator;

public class Find {
    private boolean isRecursive;
    private File dir;
    private String toFind;

    public void find(String[] args) {
        parseArgs(args);
        findFile(dir);
    }

    //функция, которую вызовем рекурсивно в том случае, если попадется директория, а не файл
    private void findFile(File directory) {
        for (File file : directory.listFiles()) {
            if (file.isDirectory()) {
                //если был -r, то вызываем эту же функцию с найденной директорией
                if (isRecursive) {
                    findFile(file);
                }
            }
            if (file.getName().equals(toFind)) {
                System.out.println(file.getAbsolutePath());
            }
        }
    }

    private void parseArgs(String[] args) {
        if (args.length == 0) {
            System.out.println("Incorrect input.");
            System.exit(0);
        }

        /*
         возможны варианты:
            либо без ключа;
            либо ключ -r;
            либо ключ -d;
            либо ключ -rd;
            либо -dr;
         */
        LinkedList<String> list = new LinkedList<String>(Arrays.asList(args));
        StringBuilder keys = new StringBuilder("-");
        ListIterator<String> iterator = list.listIterator();
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.matches("-.*")) {
                keys.append(s.subSequence(1, s.length()));
                iterator.remove();
            }
        }
        if (!keys.toString().equals("-")) {
            list.addFirst(keys.toString());
        }
        //Чтобы метод toArray вернул массив каких-то объектов, надо ему в параметрах передать инстанс массива
        //этих же объектов. В противном случае вернет Object[].
        args = list.toArray(new String[0]);

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
                    toFind = args[2];
                }
            } else {
                if (args.length != 2) {
                    System.out.println("Incorrect input.");
                    System.exit(0);
                } else {
                    //точка - текущая директория
                    dir = new File(".");
                    toFind = args[1];
                }
            }
        } else {
            if (args.length != 1) {
                System.out.println("Incorrect input.");
                System.exit(0);
            } else {
                dir = new File(".");
                toFind = args[0];
            }
        }
    }
}
