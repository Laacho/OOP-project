
import java.util.Scanner;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);

       // String XML= scanner.nextLine();
     Set<String> ids= Verification.verify("XML input.txt");
        Correction.correction("XML input.txt","NewFile.txt",ids);

    }
}
