package models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ResultModel {

    private int lineNumber;
    private String abr;
    private String name;
    private String team;
    private String timeStart;
    private String timeEnd;
    private String dateStart;
    private String dateEnd;
    private LocalDateTime timeStartResult;
    private LocalDateTime timeEndResult;
    private long durationTimeMillis;
    private String durationTimeStr;

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int i) {
        this.lineNumber = i + 1;
    }

    public String getAbr() {
        return abr;
    }

    public void setAbr(String abr) {
        this.abr = abr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getDateStart() {
        return dateStart;
    }

    public void setDateStart(String dateStart) {
        this.dateStart = dateStart;
    }

    public String getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(String dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getTimeStartResult() {
        return timeStartResult;
    }

    public void setTimeStartResult(String dateStart, String timeStart) {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String text = dateStart + " " + timeStart;
        this.timeStartResult = LocalDateTime.parse(text, formatter);
    }

    public LocalDateTime getTimeEndResult() {
        return timeEndResult;
    }

    public void setTimeEndResult(String dateEnd, String timeEnd) {
        DateTimeFormatter formatter
                = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        String text = dateEnd + " " + timeEnd;
        this.timeEndResult = LocalDateTime.parse(text, formatter);
    }

    public String getDurationTimeStr() {
        return durationTimeStr;
    }

    public void setDurationTimeStr(LocalDateTime timeStartResult, LocalDateTime timeEndResult) {
        long ms = Duration.between(timeStartResult, timeEndResult).toMillis();
        long millis = ms % 1000;
        long x = ms / 1000;
        long seconds = x % 60;
        x /= 60;
        long minutes = x % 60;
        this.durationTimeStr = String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }

    public long getDurationTimeMillis() {
        return durationTimeMillis;
    }

    public void setDurationTimeMillis(LocalDateTime timeStartResult, LocalDateTime timeEndResult) {
        this.durationTimeMillis = Duration.between(timeStartResult, timeEndResult).toMillis();
    }

    public void printRaceResult() {
        System.out.printf("%5s %3s %20s %5s %30s %5s %10s%n", this.getLineNumber(), "|", this.getName(), "|", this.getTeam(), "|", this.getDurationTimeStr());
    }

    @Override
    public String toString() {
        return "ResultModel{" +
                "line='" + lineNumber + '\'' +
                ", abr='" + abr + '\'' +
                ", name='" + name + '\'' +
                ", team='" + team + '\'' +
                ", dateStart='" + dateStart + '\'' +
                ", timeStart='" + timeStart + '\'' +
                ", dateEnd='" + dateEnd + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", time='" + durationTimeStr + '\'' +
                ", timeMillis='" + durationTimeMillis + '\'' +
                '}';
    }
}