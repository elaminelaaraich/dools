package com.dools;

import java.util.List;

public class OutputData extends Data {
    private String fodep;

    public OutputData(Data data) {
        setRef(data.getRef());
        setDureeResiduelle(data.getDureeResiduelle());
        setEmetteurContrePartie(data.getEmetteurContrePartie());
        setNote(data.getNote());
        setPosition(data.getPosition());
    }

    public String getFodep() {
        return fodep;
    }

    private void setFodep(String fodep) {
        this.fodep = fodep;
    }

    public void calculateFodepBasedOnConditions(List<Condition> conditions) {
        Condition condition = conditions.stream()
                .filter(currentCondition -> currentCondition.accept(this))
                .findFirst()
                .orElse(null);
        this.setFodep(condition != null ? condition.getFODEP() : null);

    }

    @Override
    public String toString() {
        return "OutputData{" +
                "fodep='" + fodep + '\'' +
                '}';
    }

    public static String toCsv(OutputData outputData) {
        return outputData.getRef() + "," +
                outputData.getEmetteurContrePartie() + "," +
                outputData.getNote() + "," +
                outputData.getPosition() + "," +
                outputData.getDureeResiduelle() + "," +
                outputData.getFodep();
    }
}
