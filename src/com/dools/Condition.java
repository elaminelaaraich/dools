package com.dools;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

public class Condition {
    private final ScriptEngine engine;
    private String name;
    private String emetteurContrePartie;
    private String note;
    private String position;
    private String dureeResiduelle;
    private String FODEP;

    public Condition() {
        ScriptEngineManager mgr = new ScriptEngineManager();
        this.engine = mgr.getEngineByName("JavaScript");
    }

    public String getName() {
        return name;
    }

    public String getEmetteurContrePartie() {
        return emetteurContrePartie;
    }

    public String getNote() {
        return note;
    }

    public String getPosition() {
        return position;
    }

    public String getDureeResiduelle() {
        return dureeResiduelle;
    }

    public String getFODEP() {
        return FODEP;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmetteurContrePartie(String emetteurContrePartie) {
        this.emetteurContrePartie = emetteurContrePartie;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setDureeResiduelle(String dureeResiduelle) {
        this.dureeResiduelle = dureeResiduelle;
    }

    public void setFODEP(String FODEP) {
        this.FODEP = FODEP;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "name='" + name + '\'' +
                ", emetteurContrePartie='" + emetteurContrePartie + '\'' +
                ", note='" + note + '\'' +
                ", position='" + position + '\'' +
                ", dureeResiduelle='" + dureeResiduelle + '\'' +
                ", FODEP='" + FODEP + '\'' +
                '}';
    }

    public boolean accept(OutputData outputData) {
        boolean IsEmetteurContrePartieTheSame = this.emetteurContrePartie.equalsIgnoreCase(outputData.getEmetteurContrePartie());
        boolean isNoteTheSame = this.note.length() == 0 || this.note.equals(outputData.getNote());
        boolean isPositionSatisfied = isPositionSatisfied(outputData.getPosition());
        boolean isDureeResiduelleSatisfied = isDureeResiduelleSatisfied(outputData.getDureeResiduelle());

        return IsEmetteurContrePartieTheSame &&
                isNoteTheSame &&
                isPositionSatisfied &&
                isDureeResiduelleSatisfied;
    }

    private boolean isDureeResiduelleSatisfied(int dureeResiduelle) {
        if (this.dureeResiduelle.length() == 0) {
            return true;
        } else {
            String expression = this.dureeResiduelle.replaceAll("=<", "<=")
                    .replaceAll("=>", ">=")
                    .replaceAll("duree_residuelle", Integer.toString(dureeResiduelle));
            try {
                return Boolean.parseBoolean(engine.eval(expression).toString());
            } catch (ScriptException e) {
                return false;
            }
        }
    }

    private boolean isPositionSatisfied(double position) {
        if (this.position.contains("=<")) {
            double positionBalance = Double.parseDouble(this.position.split("=<")[1].trim());
            if (position <= positionBalance) return true;
        } else if (this.position.contains("<=")) {
            double positionBalance = Double.parseDouble(this.position.split("<=")[1].trim());
            if (position <= positionBalance) return true;
        } else if (this.position.contains(">=")) {
            double positionBalance = Double.parseDouble(this.position.split(">=")[1].trim());
            if (position >= positionBalance) return true;
        } else if (this.position.contains("=>")) {
            double positionBalance = Double.parseDouble(this.position.split("=>")[1].trim());
            if (position >= positionBalance) return true;
        }
        return false;
    }
}
