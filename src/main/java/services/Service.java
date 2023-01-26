package services;

import dao.Dao;
import models.RacerModel;
import models.ResultModel;
import models.TimeEndModel;
import models.TimeStartModel;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Service {

    private List<ResultModel> fillResultModel() {

        List<RacerModel> racerModelList = new Dao().getListOfRacers();
        return racerModelList.stream().map(r ->
        {
            ResultModel resultModel = new ResultModel();
            resultModel.setAbr(r.getRacerAbbreviation());
            resultModel.setName(r.getRacerName());
            resultModel.setTeam(r.getRacerTeam());
            return resultModel;
        }).peek(r -> {
            r.setDateStart(getDateStartForStream(r));
            r.setTimeStart(getTimeStartForStream(r));
            r.setDateEnd(getDateEndForStream(r));
            r.setTimeEnd(getTimeEndForStream(r));
            r.setTimeStartResult(r.getDateStart(), r.getTimeStart());
            r.setTimeEndResult(r.getDateEnd(), r.getTimeEnd());
            r.setDurationTimeMillis(r.getTimeStartResult(), r.getTimeEndResult());
            r.setDurationTimeStr(r.getTimeStartResult(), r.getTimeEndResult());
        }).toList();
    }

    public List<ResultModel> getSortedResultModel() {
        List<ResultModel> resultModelList = new Service().fillResultModel();
        return resultModelList.stream()
                .sorted(Comparator.comparingLong(ResultModel::getDurationTimeMillis))
                .collect(Collectors.toList());
    }

    private String getDateStartForStream(ResultModel resultModel) {
        List<TimeStartModel> timeStartModelList = new Dao().getListOfStartTimes();
        return timeStartModelList.stream().filter(t -> t.getAbr().equals(resultModel.getAbr())).findAny().orElseThrow().getDate();
    }

    private String getTimeStartForStream(ResultModel resultModel) {
        List<TimeStartModel> timeStartModelList = new Dao().getListOfStartTimes();
        return timeStartModelList.stream().filter(t -> t.getAbr().equals(resultModel.getAbr())).findAny().orElseThrow().getTime();
    }

    private String getDateEndForStream(ResultModel resultModel) {
        List<TimeEndModel> timeEndModelList = new Dao().getListOfEndTimes();
        return timeEndModelList.stream().filter(t -> t.getAbr().equals(resultModel.getAbr())).findAny().orElseThrow().getDate();
    }

    private String getTimeEndForStream(ResultModel resultModel) {
        List<TimeEndModel> timeEndModelList = new Dao().getListOfEndTimes();
        return timeEndModelList.stream().filter(t -> t.getAbr().equals(resultModel.getAbr())).findAny().orElseThrow().getTime();
    }
}