package dao;

import models.RacerModel;
import models.TimeEndModel;
import models.TimeStartModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

public class Dao {

    private InputStream convertTxtToInputStream(String fileName) {
        ClassLoader classLoader = Dao.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        assert inputStream != null;
        return inputStream;
    }

    public List<RacerModel> getListOfRacers() {
        List<RacerModel> racerModels;
        try
                (InputStream inputStream = convertTxtToInputStream("abbreviations.txt")) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            racerModels =
                    bufferedReader.lines()
                            .map(line -> line.split("_"))
                            .map(str -> {
                                RacerModel racerModel = new RacerModel();
                                racerModel.setRacerAbbreviation(str[0]);
                                racerModel.setRacerName(str[1]);
                                racerModel.setRacerTeam(str[2]);
                                return racerModel;
                            }).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return racerModels;
    }

    private String changeString(String s) {
        StringBuilder sb = new StringBuilder(s);
        sb.insert(3, "_");
        return sb.toString();
    }

    public List<TimeEndModel> getListOfEndTimes() {
        try
                (InputStream inputStream = convertTxtToInputStream("end.txt")) {
            assert inputStream != null;
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader);

            List<TimeEndModel> timeEndModelList;
            timeEndModelList =
                    bufferedReader.lines()
                            .map(this::changeString)
                            .map(e -> e.split("_"))
                            .map(str -> {
                                TimeEndModel timeEndModel = new TimeEndModel();
                                timeEndModel.setAbr(str[0]);
                                timeEndModel.setDate(str[1]);
                                timeEndModel.setTime(str[2]);
                                return timeEndModel;
                            })
                            .collect(Collectors.toList());
            return timeEndModelList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TimeStartModel> getListOfStartTimes() {
        try
                (InputStream inputStream = convertTxtToInputStream("start.txt")) {

            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader);

            List<TimeStartModel> timeStartModelList;
            timeStartModelList =
                    bufferedReader.lines()
                            .map(e -> e.split("_"))
                            .map(str -> {
                                TimeStartModel timeStartModel = new TimeStartModel();
                                timeStartModel.setAbr(str[0].substring(0, 3));
                                timeStartModel.setDate(str[0].substring(3));
                                timeStartModel.setTime(str[1]);
                                return timeStartModel;
                            })
                            .collect(Collectors.toList());
            return timeStartModelList;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}