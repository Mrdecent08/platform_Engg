import java.util.List;

public class AlertGroup {
    private List<Alert> alerts;
    private Labels labels;
    private Receiver receiver;

    // Getters and setters
}

public class Alert {
    private Annotations annotations;
    private String endsAt;
    private String fingerprint;
    private List<Receiver> receivers;
    private String startsAt;
    private Status status;
    private String updatedAt;
    private String generatorURL;
    private Labels labels;

    // Getters and setters
}

public class Annotations {
    private String summary;
    private String description;

    // Getters and setters
}

public class Receiver {
    private String name;

    // Getters and setters
}

public class Status {
    private List<String> inhibitedBy;
    private List<String> silencedBy;
    private String state;

    // Getters and setters
}

public class Labels {
    private String alertname;
    private String instance; // Add other label properties as needed

    // Getters and setters
}
