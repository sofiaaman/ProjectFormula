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
    //метод для чтения файла abbreviations.txt

    //Задание 1. Преобразуем файл abbreviations.txt
    //Нужно распарсить данные из файла abbreviations.txt в модель гонщика
    // разуем каждую строку файла в модель гонщика

    public List<RacerModel> getListOfRacers() {
        ClassLoader classLoader = Dao.class.getClassLoader();  // загрузчик класса
        List<RacerModel> racerModels; //коллекция из гонщиков

        try
                (InputStream inputStream = classLoader.getResourceAsStream("abbreviations.txt")) {
            assert inputStream != null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);//читает посимвольно
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader); //буфер, в который записываются символы
            // bufferedReader.readLine();
            //у BufferedReader есть метод, который может читать все строки из файла как поток.
            racerModels =
                    bufferedReader.lines() //строка из txt файла
                            .map(line -> line.split("_")) //разделение каждой строки по _,массив строк с подстроками
                            .map(str -> {   //если нужно произвести несколько действий над str то это пишем в {}
                                RacerModel racerModel = new RacerModel();
                                //[ [DRR] [Daniel Ricciardo] [RED BULL RACING TAG HEUER]
                                //[SVF] [Sebastian Vettel] [FERRARI] ]
                                racerModel.setRacerAbbreviation(str[0]);
                                racerModel.setRacerName(str[1]);
                                racerModel.setRacerTeam(str[2]);
                                //System.out.println(racerModel.toString());
                                return racerModel;
                            }).collect(Collectors.toList());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return racerModels;
    }

    public String changeString(String s) {
        StringBuffer sb = new StringBuffer(s);
        sb.insert(3, "_");
        return sb.toString();
    }

    //метод для чтения файла end.txt
    public List<TimeEndModel> getListOfEndTimes() {
        ClassLoader classLoader = Dao.class.getClassLoader();  // загрузчик класса

        try
                (InputStream inputStream = classLoader.getResourceAsStream("end.txt")) {
            assert inputStream != null;
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8);//читает посимвольно
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader); //буфер, в который записываются символы

//            Stream<String> list1 = bufferedReader.lines();
//            List<String> Test2 = list1.map(line -> changeString(line)).collect(Collectors.toList());

            //cначала добавляем в line "_" с помощью changeString, а потом разделяем строку по этому символу
            List<String> list = bufferedReader.lines().map(line -> changeString(line)).collect(Collectors.toList());

            List<TimeEndModel> timeEndModelList;
            timeEndModelList =
                    list.stream()
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

    //Метод 3. Чтение из файла start.txt
    public List<TimeStartModel> getListOfStartTimes() {
        ClassLoader classLoader = Dao.class.getClassLoader();  // загрузчик класса

        try
                (InputStream inputStream = classLoader.getResourceAsStream("start.txt")) {
            assert inputStream != null;
            InputStreamReader inputStreamReader =
                    new InputStreamReader(inputStream, StandardCharsets.UTF_8);//читает посимвольно
            BufferedReader bufferedReader =
                    new BufferedReader(inputStreamReader); //буфер, в который записываются символы

            List<TimeStartModel> timeStartModelList;
            timeStartModelList =
                    bufferedReader.lines()
                            .map(e -> e.split("_"))
                            .map(str -> {
                                TimeStartModel timeStartModel = new TimeStartModel();
                                timeStartModel.setAbr(str[0].substring(0, 3));
                                timeStartModel.setDate(str[0].substring(3,str[0].length()));
                                timeStartModel.setTime(str[1]);
                                return timeStartModel;
                            })
                            .collect(Collectors.toList());

            return timeStartModelList;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void main(String[] args) {
//        Dao dao = new Dao();
//        System.out.println(dao.getListOfEndTimes());
//        System.out.println(dao.getListOfStartTimes());
//    }


}
