package com.dools;

public class Data {

    private long ref;
    private String emetteurContrePartie;
    private String note;
    private double position;
    private int dureeResiduelle;

    public long getRef() {
        return ref;
    }

    public String getEmetteurContrePartie() {
        return emetteurContrePartie;
    }

    public String getNote() {
        return note;
    }

    public double getPosition() {
        return position;
    }

    public int getDureeResiduelle() {
        return dureeResiduelle;
    }

    public void setRef(long ref) {
        this.ref = ref;
    }

    public void setEmetteurContrePartie(String emetteurContrePartie) {
        this.emetteurContrePartie = emetteurContrePartie;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public void setPosition(double position) {
        this.position = position;
    }

    public void setDureeResiduelle(int dureeResiduelle) {
        this.dureeResiduelle = dureeResiduelle;
    }

    public static class Builder {
        private String[] infos;

        public Builder(String line) {
            this.infos = line.split(",");
        }

        public Data build() {
            Data newData = new Data();
            int index = 0;
            newData.setRef(Long.parseLong(this.infos[index++]));
            newData.setEmetteurContrePartie(this.infos[index++]);
            newData.setNote(this.infos[index++]);
            newData.setPosition(Double.parseDouble(this.infos[index] != null ? this.infos[index++] : "0"));
            newData.setDureeResiduelle(Integer.parseInt(index < this.infos.length && this.infos[index] != null ? this.infos[index++] : "0"));
            return newData;
        }
    }

    @Override
    public String toString() {
        return "Data{" +
                "ref=" + ref +
                ", emetteurContrePartie='" + emetteurContrePartie + '\'' +
                ", note='" + note + '\'' +
                ", position=" + position +
                ", dureeResiduelle=" + dureeResiduelle +
                '}';
    }
}
