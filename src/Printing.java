public class Printing {


    public void print(XMLParser xmlParser){
        System.out.println(xmlParser.getHeader()+": ");
        for (int i = 0; i < xmlParser.getEntity().size(); i++) {
            System.out.println(xmlParser.getEntity().get(i)); //xml file
        }

    }



}
