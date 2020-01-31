package com.example.pelisomdb;

public class Pelicula {
    private String Title;

    public void setTitle(String title) {
        Title = title;
    }

    public void setYear(String year) {
        Year = year;
    }

    public void setDirector(String director) {
        Director = director;
    }

    public void setPlot(String plot) {
        Plot = plot;
    }

    public String getTitle() {
        return Title;
    }

    public String getYear() {
        return Year;
    }

    public String getDirector() {
        return Director;
    }

    public String getPlot() {
        return Plot;
    }

    private String Year;
    private String Director;
    private String Plot;

    public Pelicula(String titulo, String anyo, String director, String trama) {
        this.Title = titulo;
        this.Year = anyo;
        this.Director = director;
        this.Plot = trama;
    }
    public Pelicula() {
    }


}
