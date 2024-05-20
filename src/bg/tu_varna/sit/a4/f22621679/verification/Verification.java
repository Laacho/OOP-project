package bg.tu_varna.sit.a4.f22621679.verification;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Verification {
    private Set<String> ids;

    /***
     * Verifies whether an XML file contains duplicate element IDs.
     * @param fileName The path to the XML file to be verified.
     * @return  True if the file contains duplicate IDs, false otherwise.
     */
    public boolean verify(String fileName) {
        this.ids = new LinkedHashSet<>();
        boolean found = false;
        try (FileReader fileReader = new FileReader(fileName)) {
            Scanner scanner = new Scanner(fileReader);

            String line = scanner.nextLine();
            line = scanner.nextLine().trim();
            Pattern pattern = Pattern.compile("[^\\s+<>id=\"\\d+]+");
            Matcher matcher = pattern.matcher(line);

            String result = "";
            if (matcher.find()) {
                result = matcher.group();
            }

            while (scanner.hasNext()) {
                if (line.contains(result)) {
                    if (line.contains("id")) {
                        //proveri dali e validno
                        pattern = Pattern.compile("\\d+");
                        matcher = pattern.matcher(line);
                        String currID = "";
                        if (matcher.find()) {
                            currID = matcher.group();
                        }

                        if (!ids.add(currID)) {
                            found = true;
                            String original = currID;
                            currID += "_1";
                            int counter = 2;
                            while (!ids.add(currID)) {
                                currID = original + "_" + String.valueOf(counter);
                                counter++;
                            }
                        }
                    } else {
                        found = true;
                        if (line.contains("/")) {
                            line = scanner.nextLine().trim();
                            continue;
                        }
                        String lastId = ids.toArray(String[]::new)[ids.size() - 1];//1_3
                        int counter = 2;
                        if (lastId.contains("_")) {
                            counter = Integer.parseInt(lastId.substring(lastId.indexOf("_") + 1));
                        }
                        String currID = lastId.substring(0, lastId.indexOf("_")) + "_1";
                        while (!ids.add(currID)) {
                            currID = lastId.substring(0, lastId.indexOf("_")) + "_" + String.valueOf(counter);
                            counter++;
                        }
                    }
                }
                line = scanner.nextLine().trim();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return found;
    }

    /***
     * Corrects an XML file containing duplicate element IDs by appending unique suffixes to duplicate IDs.
     * @param oldFileName The path to the original XML file with potential duplicate IDs.
     * @return The path to the corrected XML file with unique IDs (prefixed with "N").
     */
    public String correction(String oldFileName) {
        if (verify(oldFileName)) {
            try (FileReader fileReader = new FileReader(oldFileName);
                 FileWriter fileWriter = new FileWriter("N"+oldFileName)) {
                String header = "";
                Scanner scanner = new Scanner(fileReader);
                String line = scanner.nextLine();

                header="</"+line.split("\\s+")[0].replace("<", "")+">";
                fileWriter.write(line);
                fileWriter.write(System.lineSeparator());
                List<String> listIds = new ArrayList<>(this.ids);
                int index = 0;
                line = scanner.nextLine().trim();
                Pattern pattern = Pattern.compile("[^\\s+<>id=\"\\d+]+");
                Matcher matcher = pattern.matcher(line);
                String result = "";
                if (matcher.find()) {
                    result = matcher.group();
                }
                while (scanner.hasNext()) {
                    if (!line.contains("/") && line.contains(result)) {
                        String toReplace = "<" + result + " id=\"" + listIds.get(index) + "\">";
                        index++;
                        fileWriter.write(toReplace);
                        fileWriter.write(System.lineSeparator());
                    } else {
                        fileWriter.write(line);
                        fileWriter.write(System.lineSeparator());
                    }
                    line = scanner.nextLine();
                }
                fileWriter.write(header);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
        return "N"+oldFileName;
    }

}
