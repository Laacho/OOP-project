import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {


    public  Set<String> verify(String fileName){
        Set<String> ids=new LinkedHashSet<>();
        try (FileReader fileReader=new FileReader(fileName)){
            Scanner scanner=new Scanner(fileReader);

            String line=scanner.nextLine();// header->people
            line= scanner.nextLine().trim();
            Pattern pattern=Pattern.compile("[^\\s+<>id=\"”\\d+]+");
            Matcher matcher= pattern.matcher(line);

            String result="";
            if(matcher.find()){
                result= matcher.group();//person
            }

            while (scanner.hasNext()){
//                if(counter<attributes){
//                  //bi trqbvalo da e red kudeto trqbva da proverim za id
//                    if(line.contains("id")){
//                        //ima id
//                        // <person id=”0”>
//
//                        Pattern pattern=Pattern.compile("\\d+");
//                        Matcher matcher=pattern.matcher(line);
//                        int currID=0;
//                        if(matcher.find()){
//                            currID= Integer.parseInt(matcher.group());
//                        }
//
//                        if(!ids.add(currID)){
//                           //znachi go ima i trqbva da se napravi ako e 1 da stane 1_1 i 1_2
//                            String idString= String.valueOf(currID);
//                            //what to concatenate
//                            idString+=String.valueOf(additionCounter.get(currID)+1);
//
//                        }
//                        else{
//
//                        }
//                    }
//                    else {
//                        //nqma id
//                    }
//                }
//                else{
//                    counter++;
//                }


                    //<people> ->here is scanner
                    // <person id=”0”> // <person id="1"> // <person>
                    // <name>John Smith</name>
                    // <address>USA</address> ->N
                    // </person>
                    // <person id=”1”>
                    // <name>Ivan Petrov</name>
                    // <address>Bulgaria</address>
                    // </person>
                    // </people>
                if(line.contains(result)){
                    if(line.contains("id")){
                        //proveri dali e validno
                         pattern=Pattern.compile("\\d+");
                         matcher=pattern.matcher(line);
                       String currID="";
                        if(matcher.find()){
                            currID= matcher.group();
                        }
                        if(!ids.add(currID)){
                            //ako ne moje da go dobavi trqbva da se zameni s 1_1
                            String original=currID;
                            currID+="_1";
                            int counter=2;
                            while (!ids.add(currID)){
                                currID=original+"_"+String.valueOf(counter);
                                counter++;
                            }
                        }
                    }
                    else{
                        // da mu dobavi podhodqshto
                        if(line.contains("/")){
                            line= scanner.nextLine().trim();
                            continue;
                        }
                        String lastId= ids.toArray(String[]::new)[ids.size()-1];//1_3
                        int counter=2;
                        if(lastId.contains("_")){
                            counter= Integer.parseInt(lastId.substring(lastId.indexOf("_")+1));
                        }
                       String currID=lastId.substring(0,lastId.indexOf("_"))+"_1";

                        while (!ids.add(currID)){
                            currID=lastId.substring(0,lastId.indexOf("_"))+"_"+String.valueOf(counter);
                            counter++;
                        }
                    }

                }
                    line= scanner.nextLine().trim();

            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return ids;
    }

}
