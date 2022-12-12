package app.helpers;

import app.io.FileReader;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserInputUtils {

    public static String getFileContentsOrInput(String input) {
        if(!isFile(input)){
            return input;
        } else{
            if(!hasCorrectFileExtension(input)){
                throw new IllegalArgumentException("Please provide a file with .txt extension!");
            }

            FileReader reader = new FileReader();

            return reader.getFileContents(input);
        }
    }

    public static boolean isFile(String input) {
        checkIfInputIsNull(input);

        Pattern pattern = Pattern.compile("\\.\\S");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static boolean hasCorrectFileExtension(String input) {
        checkIfInputIsNull(input);

        Pattern pattern = Pattern.compile("\\.txt$");
        Matcher matcher = pattern.matcher(input);

        return matcher.find();
    }

    public static void checkIfInputIsNull(String input){
        if(input == null){
            throw new IllegalArgumentException("File cannot be null");
        }
    }
}
