/**
 * SearchTree- read text file and search for words
 * CMSY167 Spring 2023
 * @author Samuel Dolle
 * @version 1.0
 *
 */
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
public class Main {
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        Scanner path = new Scanner(System.in);
        List<String> Lines = new ArrayList<>(); //placing lines in arraylist instead of Line objects
        String refined1 = "";
        String refined2 = "";
        String refined3 = "";
        String search = "";
        Pattern expression = //regex pattern search parameters
                Pattern.compile("[$&+,:;=?@#|'<>.^*()%!]");
        SimpleMap<Integer, String> WordMap = new CustomBinaryTreeSimpleMap<>();

        System.out.println("Please enter file path:"); //prompt for file path
        String filepath = path.next();
        FileReader TextFile = new FileReader(filepath); //filereader format

        try (BufferedReader br = new BufferedReader(TextFile)) {
            int i = -1;
            String line;
            while ((line = br.readLine()) != null) {
                i++;
                Lines.add(i, line); //use bufferedreader to populate arraylist
            }
        }

        String text = Files.readString(Paths.get(filepath)); //read file as one string
        String[] words = text.split("[\\n|\\s]+"); //split string into smaller strings seperated by repeated linebreaks or whitespaces
        for (int i = 0; i <= words.length-1; i++) {
            WordMap.put(i,words[i].replaceAll("[\\.|\\'|\\,|\\-|\\:|\\/]","")); //populate SimpleMap with one word strings, removing all punctuation
        }
            do {
                System.out.println();
                System.out.println("(Type LOGOFF to exit)");
                System.out.println("Enter search term: ");
                search = input.next(); //prompt for search term
                Matcher matcher = expression.matcher(search);
                if (matcher.find()) { //user error handler for special characters
                    System.out.println("Do not enter special characters.");
                    System.out.println();
                } else {

                    for (int i = 0; i <= words.length - 1; i++) {
                        if (WordMap.containsKey(i)) { //check SimpleMap if index exists
                            if (WordMap.get(i).equalsIgnoreCase(search)) { //retrieve value of index and match to user search
                                i = words.length; //end loop
                            } else {
                                i = words.length;
                                System.out.println("All match results displayed."); //end text concluding search
                                System.out.println();
                            }
                        }
                    }
                    refined1 = " " + search + " "; //refined search results to include space or punctuation for successful searches
                    refined2 = " " + search + ",";
                    refined3 = " " + search + ".";

                    for (int n = 0; n <= Lines.size() - 1; n++) { //matches refined searches to Lines arraylist
                        if (Lines.get(n).toLowerCase().contains(refined1.toLowerCase()) || Lines.get(n).toLowerCase().contains(refined2.toLowerCase()) || Lines.get(n).toLowerCase().contains(refined3.toLowerCase())) {
                            System.out.println(n + ": " + Lines.get(n)); //prints line numbers and full lines of refined search
                        }
                    }
                }
            } while (!(search.matches("LOGOFF"))); //exit prompt
        input.close();
        WordMap.clear(); //clear SimpleMap
        if (WordMap.isEmpty()) { //check to see if map is empty
            System.out.println("Have a nice day.");
        }
    }
}