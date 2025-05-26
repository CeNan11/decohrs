package entity;

public class Education {
    private String primarySchool;
    private int primaryYearGraduated;
    private String tertiarySchool;
    private int tertiaryYearGraduated;
    
    // Setters and getters
    public void setPrimarySchool(String primarySchool) {
        this.primarySchool = primarySchool;
    }

    public void setPrimaryYearGraduated(int primaryYearGraduated) {
        this.primaryYearGraduated = primaryYearGraduated;
    }

    public void setTertiarySchool(String tertiarySchool) {
        this.tertiarySchool = tertiarySchool;
    }

    public void setTertiaryYearGraduated(int tertiaryYearGraduated) {
        this.tertiaryYearGraduated = tertiaryYearGraduated;
    }

    public String getPrimarySchool() {
        return primarySchool;
    }

    public int getPrimaryYearGraduated() {
        return primaryYearGraduated;
    }

    public String getTertiarySchool() {
        return tertiarySchool;
    }

    public int getTertiaryYearGraduated() {
        return tertiaryYearGraduated;
    }
}