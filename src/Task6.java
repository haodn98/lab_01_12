
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

class ThreeNumsException extends Exception {
    @Override
    public String toString() {
        return "You entered not 3-digits number";
    }

}

public class Task6 {
    public static void main(String[] args) {
        myBank();
    }

    public static void myBank() {
        URL url;
        try {
            url = new URL("https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()))) {
            System.out.println("Please enter your number(3-digits integer): ");
            Scanner sc = new Scanner(System.in);
            int number = sc.nextInt();
            if (Integer.toString(number).length() != 3) {
                throw new ThreeNumsException();
            }
            boolean is_found = false;
            String line;
            while ((line = br.readLine()) != null) {
                String[] str = line.split("\\s{2,}");
                if (Objects.equals(str[0], Integer.toString(number))) {
                    System.out.println("Your bank is " + str[1]);
                    is_found = true;
                    break;
                }
            }
            if (!is_found) {
                System.out.println("No such number in the list");
            }
        } catch (IOException | ThreeNumsException e) {
            System.out.println(e);
        }


    }
}