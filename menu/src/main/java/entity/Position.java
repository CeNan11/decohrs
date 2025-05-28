package entity;

public class Position {
    private Integer positionId;
    private String positionTitle;

    public Position() {}

    public Position(Integer positionId, String positionTitle) {
        this.positionId = positionId;
        this.positionTitle = positionTitle;
    }

    // Getters
    public Integer getPositionId() { return positionId; }
    public String getPositionTitle() { return positionTitle; }

    // Setters 
    public void setPositionId(Integer positionId) { this.positionId = positionId; }
    public void setPositionTitle(String positionTitle) { this.positionTitle = positionTitle; }

    @Override
    public String toString() {
        return positionTitle;
    }
}