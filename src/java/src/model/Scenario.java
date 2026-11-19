package model;

public class Scenario {
    private final String scenarioId;
    private final String title;
    private final int durationMin;
    private final int maxParticipants;

    public Scenario(String scenarioId, String title, int durationMin, int maxParticipants) {
        this.scenarioId = scenarioId;
        this.title = title;
        this.durationMin = durationMin;
        this.maxParticipants = maxParticipants;
    }

    public String getScenarioId() {
        return scenarioId;
    }

    public String getTitle() {
        return title;
    }

    public int getDurationMin() {
        return durationMin;
    }

    public int getMaxParticipants() {
        return maxParticipants;
    }
}