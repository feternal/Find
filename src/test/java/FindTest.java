import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;

public class FindTest {


    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();


    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }



    @After
    public void cleanUpStream() {
        System.setOut(null);
    }

    @Test
    public void findCreatedFile() {
        String fileName = "Asfdnjv.jsd";
        File file = new File(fileName);
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Find.main(fileName.split(" "));
        file.delete();
        String[] allFiles = outContent.toString().split("\r\n");
        for (String s : allFiles) {
            // '.' - любой символ
            // '*' - повторение 0 или более раз
            // $ - конец строки
            // таким образом, данное регулярное выражение означает: сторока, начинающаяся с чего угодно, но
            // заканчивающаяся на fileName
            if (s.matches(".*" + fileName + "$")) {
                assert true;
                return;
            }
        }
        assert false;
    }

    @Test
    public void recursiveFind() {
        String fileName = "KKLAJsdjh.awej.dfs";
        /*
        Использовал File.separator вместо "/", т.к. под разными операционными системами
        используются разные разделители при указании путей.
        */

        File file = new File("a" + File.separator + fileName);
        File dir = new File("." + File.separator + "a");
        dir.mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //включаем рекурсию
        Find.main(("-r " + fileName).split(" "));
        file.delete();
        dir.delete();
        String[] allFiles = outContent.toString().split("\r\n");
        for (String s : allFiles) {
            if (s.matches(".*" + fileName + "$")) {
                assert true;
                return;
            }
        }
        assert false;
    }

    @Test
    public void findManyFiles() {
        String fileName1 = "Asfdnjv.jsd";
        String fileName2 = "Llasdhui.asdj";
        String fileName3 = "NkdslkLHGHKGF";
        File file1 = new File(fileName1);
        File file2 = new File(fileName2);
        File file3 = new File(fileName3);
        try {
            file1.createNewFile();
            file2.createNewFile();
            file3.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //найти все файлы
        Find.main(".*".split(" "));
        file1.delete();
        file2.delete();
        file3.delete();
        String[] allFiles = outContent.toString().split("\r\n");
        boolean first = false;
        boolean second = false;
        boolean third = false;
        for (String s : allFiles) {
            if (s.matches(".*" + fileName1 + "$")) {
                first = true;
            }
            if (s.matches(".*" + fileName2 + "$")) {
                second = true;
            }
            if (s.matches(".*" + fileName3 + "$")) {
                third = true;
            }
        }
        assert first && second && third;
    }

    @Test
    public void findInDirectory() {
        String fileName = "asdlkjiluh";
        File file = new File("a" + File.separator + fileName);
        File dir = new File("." + File.separator + "a");
        dir.mkdir();
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Find.main(("-d ." + File.separator + "a " + fileName).split(" "));
        file.delete();
        dir.delete();
        String[] allFiles = outContent.toString().split("\r\n");
        for (String s : allFiles) {
            if (s.matches(".*" + fileName + "$")) {
                assert true;
                return;
            }
        }
        assert false;
    }
}
