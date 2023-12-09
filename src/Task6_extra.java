import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class ThreeDigitsException extends Exception {
    @Override
    public String toString() {
        return "You entered not 3-digits number";
    }

}

public class Task6_extra {
    public static void main(String[] args) throws IOException {
        System.out.println("Please enter your ");
        myBank();
    }

    public static void myBank() throws IOException {
        if (!Files.exists(Path.of("banks.json"))) {
            createFile();
        }
        System.out.println("Please enter your number(3-digits integer): ");
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        try {
            if (Integer.toString(number).length() != 3) {
                throw new ThreeDigitsException();
            }
        }catch (ThreeDigitsException e){
            System.out.println(e);
        }
        boolean is_found = false;
        String[] file = Files.readString(Path.of("banks.json")).replace("[", "").split("]");
        for (int i = 0; i < file.length; i++) {
            JSONObject tempJson = new JSONObject(file[i]);
            if (tempJson.get("bank id").equals(Integer.toString(number))) {
                System.out.println(tempJson.get("bank name"));
                is_found = true;
                System.out.println("Do you wand to find your department?(Y/n)");
                String answer = sc.next();
                switch (answer) {
                    case "Y":
                        System.out.println("Enter your department number(ex. 10101010)");
                        String depNumber = sc.next();
                        try {
                            System.out.println(tempJson.get(depNumber).toString().replace("{","").replace("}","").replace("\""," "));
                            break;
                        }catch (JSONException e) {
                            System.out.println("Department "+depNumber +" is not found");
                        }
                        break;
                    case "n":
                        System.out.println("Have a nice day");
                        break;
                    default:
                        System.out.println("Wrong answer, have a nice day");
                        break;
                }
            }
        }
        if (!is_found){
            System.out.println("Bank is not in a list");
        }
    }
    public static void createFile() {
        URL url;
        try {
            url = new URL("https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        try (
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                BufferedWriter bw = new BufferedWriter(new FileWriter("banks.json"))) {
            String line, bank_id, bank_name, temp_bank_id = " ";
            String regex = "([0-9]{8}\\t[a-zA-Z]*.*\\S)";
            Pattern pattern = Pattern.compile(regex);
            JSONObject tempResult = new JSONObject();
            JSONArray mainResult = new JSONArray();
            int j = 0;
            while ((line = br.readLine()) != null) {
                JSONObject depResult = new JSONObject();
                String[] str = line.split("\\s{2,}");
                if (str[0].length() == 3) {
                    bank_id = str[0];
                    bank_name = str[1];
                    if (j == 0) {
                        temp_bank_id = bank_id;
                        tempResult.put("bank id", bank_id);
                        tempResult.put("bank name", bank_name);
                    }
                    j++;
                    for (int i = 1; i < str.length; i++) {
                        Matcher matcher = pattern.matcher(str[i]);
                        if (matcher.matches()) {
                            depResult.put("Department code", str[i].split("[\t]")[0]);
                            depResult.put("Department Name", str[i].split("[\t]")[1]);
                            depResult.put("Department Address", str[i + 2] + " " + str[i + 3]);
                        }

                    }
                    if (!temp_bank_id.equals(bank_id)) {
                        mainResult.put(tempResult);
                        bw.write(mainResult.toString(4).replace("[]", ""));
                        tempResult.clear();
                        tempResult.put((String) depResult.get("Department code"), depResult);
                        j = 0;
                    } else {
                        tempResult.put((String) depResult.get("Department code"), depResult);
                    }

                }
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

