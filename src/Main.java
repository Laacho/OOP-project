import java.io.FileNotFoundException;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

      //  Set<String> verify = Verification.verify(fileName);
     //   Correction.correction(fileName,"Temp.txt",verify);
   // String fileName="XML.txt";
        //reading and verifying file
      //  Verification verification=new Verification();
       // Set<String> verify = verification.verify(fileName);
     ///   String root=verification.getRootElem();
       // Correction correction = new Correction();
       //  correction.correction(fileName,"Temp.txt",verify);
      //   String attributeName= correction.getAttributeName();
        //parsing xmlfile to classes
    //   XMLParser xmlParser=new XMLParser(root);



        XMLMaker xmlMaker=new XMLMaker();


     xmlMaker.creteXML();
     //   xmlMaker.creteXML();






    }
}
