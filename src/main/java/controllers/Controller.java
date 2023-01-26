package controllers;

import models.ResultModel;
import services.Service;

import java.util.List;

public class Controller {

    public void printResult() {
        System.out.printf("%5s %3s %20s %5s %30s %5s %10s%n", "Rank", "|", "Driver", "|", "Team", "|", "Time");
        System.out.printf("%s%n", "---------------------------------------------------------------------------------------");
        List<ResultModel> sortedResultModelList = new Service().getSortedResultModel();

        final int[] i = {0};
        sortedResultModelList.stream()
                .peek(r ->
                {
                    r.setLineNumber(i[0]);
                    i[0] = i[0] + 1;
                }).toList();

        sortedResultModelList.stream()
                .filter(ResultModel -> ResultModel.getLineNumber() <= 15)
                .peek(ResultModel::printRaceResult)
                .toList();
        System.out.printf("%s%n", "---------------------------------------------------------------------------------------");
        sortedResultModelList.stream()
                .filter(ResultModel -> ResultModel.getLineNumber() > 15)
                .peek(ResultModel::printRaceResult)
                .toList();
    }

    public static void main(String[] args) {
        Controller controller = new Controller();
        controller.printResult();
    }
}