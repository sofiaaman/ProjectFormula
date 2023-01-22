package models;

public class RacerModel {
    private String racerAbbreviation;
    private String racerName;
    private String racerTeam;

    public String getRacerAbbreviation() {
        return racerAbbreviation;
    }

    public void setRacerAbbreviation(String racerAbbreviation) {
        this.racerAbbreviation = racerAbbreviation;
    }

    public String getRacerName() {
        return racerName;
    }

    public void setRacerName(String racerName) {
        this.racerName = racerName;
    }

    public String getRacerTeam() {
        return racerTeam;
    }

    public void setRacerTeam(String racerTeam) {
        this.racerTeam = racerTeam;
    }

    @Override
    public String toString() {
        return "Racer{" +
                "racerAbbreviation='" + racerAbbreviation + '\'' +
                ", racerName='" + racerName + '\'' +
                ", racerTeam='" + racerTeam + '\'' +
                '}';
    }
}
