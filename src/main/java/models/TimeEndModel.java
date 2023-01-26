package models;

public class TimeEndModel {
    private String abr;
    private String date;
    private String time;

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "TimeEnd{" +
                "abr='" + abr + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}