
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Correction {

    public static void correction(String oldFileName,String newFileName,Set<String> ids){
        try(FileReader fileReader=new FileReader(oldFileName);
            FileWriter fileWriter=new FileWriter(newFileName)) {

            Scanner scanner=new Scanner(fileReader);
            String line=scanner.nextLine();// header->people
            fileWriter.write(line);
            fileWriter.write(System.lineSeparator());
            List<String> listIds=new ArrayList<>(ids);
            int index=0;
            line= scanner.nextLine().trim();
            Pattern pattern=Pattern.compile("[^\\s+<>id=\"”\\d+]+");
            Matcher matcher= pattern.matcher(line);

            String result="";
            if(matcher.find()){
                result= matcher.group();//person
            }

            while (scanner.hasNext()){
                if(!line.contains("/") && line.contains(result)){
                    String toReplace="<"+result+" id=\""+listIds.get(index)+"\" >";
                    index++;
                    fileWriter.write(toReplace);
                    fileWriter.write(System.lineSeparator());
                }
                else{
                    fileWriter.write(line);
                    fileWriter.write(System.lineSeparator());
                }
                line= scanner.nextLine();
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
