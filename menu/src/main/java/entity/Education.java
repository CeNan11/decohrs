package entity;

public class Education {
    private String primarySchool;
    private int primaryYearGraduated;
    private String tertiarySchool;
    private int tertiaryYearGraduated;
    
    // Getters and setters
    public String getPrimarySchool() {
        return primarySchool;
    }
    
    public void setPrimarySchool(String primarySchool) {
        this.primarySchool = primarySchool;
    }
    
    public int getPrimaryYearGraduated() {
        return primaryYearGraduated;
    }
    
    public void setPrimaryYearGraduated(int primaryYearGraduated) {
        this.primaryYearGraduated = primaryYearGraduated;
    }
    
    public String getTertiarySchool() {
        return tertiarySchool;
    }
    
    public void setTertiarySchool(String tertiarySchool) {
        this.tertiarySchool = tertiarySchool;
    }
    
    public int getTertiaryYearGraduated() {
        return tertiaryYearGraduated;
    }
    
    public void setTertiaryYearGraduated(int tertiaryYearGraduated) {
        this.tertiaryYearGraduated = tertiaryYearGraduated;
    }
}