package services;

//Сделать сервис, которые будет агрегировать данные от дао и выполнять логику

import models.RacerModel;
import models.ResultModel;
import models.TimeEndModel;
import models.TimeStartModel;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Service {

    //метод, который высчитывает длительность гонки и возвращает форматированный результат
    public static String getTimeOfRace(String date1, String time1, String date2, String time2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
        //String dateTime1 = "2018-05-24 12:04:45.513";
        String dateTime1 = date1 + " " + time1;
        String dateTime2 = date2 + " " + time2;
        LocalDateTime from = LocalDateTime.parse(dateTime1, formatter);
        LocalDateTime to = LocalDateTime.parse(dateTime2, formatter);

        //получаем длительность в милисекундах
        long ms = Duration.between(from, to).toMillis();
        long millis = ms % 1000;
        long x = ms / 1000;
        long seconds = x % 60;
        x /= 60;
        long minutes = x % 60;

        return String.format("%02d:%02d.%03d", minutes, seconds, millis);
    }


    public static void main(String[] args) {

        List<RacerModel> racerModelList = new dao.Dao().getListOfRacers();
        List<TimeStartModel> timeStartModelList = new dao.Dao().getListOfStartTimes();
        List<TimeEndModel> timeEndModelList = new dao.Dao().getListOfEndTimes();

        //бежим по листу racerModelList, из него вытаскиваем каждое имя и команду и пушим эти данные
        //в результирующий список

        List<ResultModel> resultModelList = racerModelList.stream().map(r ->
        {
            //здесь заполняем поля name и team в resultModel
            ResultModel resultModel = new ResultModel(); // создали новый объект
            resultModel.setAbr(r.getRacerAbbreviation());
            resultModel.setName(r.getRacerName());
            resultModel.setTeam(r.getRacerTeam());
            return resultModel;
        }).map(r -> { //Сравним аббревиатуры из результирующего списка с абр из листов со временем и если они совпадают - вытянем дату и время
            //дата и время начала гонки
            String dateStart = timeStartModelList.stream().filter(t -> t.getAbr().equals(r.getAbr())).findAny().orElseThrow().getDate();
            String timeStart = timeStartModelList.stream().filter(t -> t.getAbr().equals(r.getAbr())).findAny().orElseThrow().getTime();
            //дата и время окончания гонки
            String dateEnd = timeEndModelList.stream().filter(t -> t.getAbr().equals(r.getAbr())).findAny().orElseThrow().getDate();
            String timeEnd = timeEndModelList.stream().filter(t -> t.getAbr().equals(r.getAbr())).findAny().orElseThrow().getTime();
            r.setDateStart(dateStart);
            r.setTimeStart(timeStart);
            r.setDateEnd(dateEnd);
            r.setTimeEnd(timeEnd);
            return r; //возвращаем каждого заполненного рейсера назад
        }).toList();

        //высчитываем для результирующей модели длительность гонки для каждого гонщика
        resultModelList.stream()
                .map(h -> {
                    String dateStart = h.getDateStart();
                    String dateEnd = h.getDateEnd();
                    String timeStart = h.getTimeStart();
                    String timeEnd = h.getTimeEnd();
                    String time = getTimeOfRace(dateStart, timeStart, dateEnd, timeEnd);
                    h.setTime(time);
                    return h; //возвращаем каждого заполненного рейсера (УЖЕ ВМЕСТЕ С ДЛИТЕЛЬНОСТЬЮ ГОНКИ) назад
                }).toList();


        System.out.println(racerModelList.toString());
        System.out.println(timeStartModelList.toString());
        System.out.println(timeEndModelList.toString());
        System.out.println(resultModelList.toString());

    }


}
