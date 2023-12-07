import java.io.*;
import java.util.Arrays;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class ShowFile {
    public static void main(String[] args) throws IOException {
        int bite;

        try (FileInputStream fis = new FileInputStream("studentsEN.txt"); FileOutputStream fos = new FileOutputStream("copy_file.txt")) {
            do {
                bite = fis.read();
                if (bite == 9) bite = 45;
                if (bite != -1) fos.write(bite);
            }
            while (bite != -1);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}