

import java.util.*;

public class XMLReader {
    public void validateIDS(String XML){
        XML=XML.trim();
            Scanner scanner=new Scanner(XML);
           String start=scanner.nextLine();//<people>
            String[] obj=start.split("[^<>]+");

           String shouldEnd="</"+obj[0]+">";
           Set<Integer> ids=new TreeSet<>();
           String string="";
            while (scanner.hasNext()){
                String line= scanner.nextLine();
                if(shouldEnd.equals(line)){
                    break;
                }
                //person id="0"
                //this regex:[^<>\s+"=]+  will return : person id 0
                //String[] containing=line.split("[^<>\\s+\"=]+");
                line= scanner.nextLine();
                if(!line.contains("id")){//<person >
                    int index=line.indexOf('>');
                    string=line.substring(0,index-1);
                    string+= "id=\" "+"0"+"\">";//CHANGE 0
                    //stava <person id="0">
                }
                else {
                    //<person id="0">
                    int[] idS= Arrays.stream(line.split("\\d+")).mapToInt(Integer::parseInt).toArray();
                    //ako ne moje sledovatelno sushtestvuva i mislim novo
                    if(!ids.add(idS[0])){
                       int id=1;
                       for (int i:ids){
                           if(!ids.contains(id)){
                               break;
                           }
                           id++;
                       }
                    }
                    //ako moje da go dobavi kum seta znachi e unique i nqma problem
                    else{
                        ids.add(idS[0]);
                    }
                }

            }
    }
}
