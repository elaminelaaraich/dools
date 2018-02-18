import com.dools.Condition;
import com.dools.Data;
import com.dools.DoolsReader;
import com.dools.OutputData;

import javax.script.ScriptException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {

        List<Data> sourceData = DoolsReader.readSourceData(args[0]);
        List<Condition> conditions = DoolsReader.readDoolsDecisionTable(args[1]);


        List<String> outputDataList = sourceData.stream()
                .map(data -> {
                    OutputData outputData = new OutputData(data);
                    outputData.calculateFodepBasedOnConditions(conditions);
                    return outputData;
                })
                .map(OutputData::toCsv)
                .collect(Collectors.toList());
        Files.write(Paths.get(args[2]), outputDataList);
    }


}
