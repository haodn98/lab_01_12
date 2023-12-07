import java.io.*;

public class Task4 {
    public static void main(String[] args){
        String[] str ;
        try (BufferedReader br = new BufferedReader(new FileReader("studentsEN.txt"));
             BufferedWriter fw = new BufferedWriter(new FileWriter("5grades.txt"))) {
            while (br.readLine() != null) {
                str = br.readLine().split("[\t]");
                if (str[2].equals("5")) {
                    fw.write(str[0] + " " + str[1] + " " + str[2] + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
