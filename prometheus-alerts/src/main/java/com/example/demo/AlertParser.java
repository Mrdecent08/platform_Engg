import com.fasterxml.jackson.databind.ObjectMapper;

public class AlertParser {

    public List<AlertGroup> parseAlerts(String json) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(json, new TypeReference<List<AlertGroup>>(){});
    }
}
