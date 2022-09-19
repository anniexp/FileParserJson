import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static ObjectMapper readJsonFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return objectMapper;
    }

    public static void writeInFiles(ObjectMapper objectMapper, File file) throws IOException {
        FileWriter write = new FileWriter("LowRankStudents.json");
        FileWriter write2 = new FileWriter("OtherStudents.json");
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        sb1.append("[");
        sb2.append("[");
        JsonNode jsonFileTree = objectMapper.readTree(file);
        for (int i = 0; i < jsonFileTree.size(); i++) {
            JsonNode jsonNode = jsonFileTree.get(i);
            StringBuilder currSb = jsonNode.get("rank").asInt() < 3 ? sb1 : sb2;
            currSb.append(",\n").append(jsonNode);
        }
        sb1.append("\n]");
        sb2.append("\n]");

        sb1.deleteCharAt(1);
        sb2.deleteCharAt(1);

        write.write(sb1.toString());
        write2.write(sb2.toString());

        write.close();
        write2.close();
    }

    public static void main(String[] args) throws IOException {
        File file = new File("Students.json");
        ObjectMapper objectMapper = readJsonFile();
        writeInFiles(objectMapper, file);
    }
}
