package edu.upc.dsa.kebabsimulator_android.models;

public class Weapon {
    private String weaponName;
    private String description;

    public Weapon(String nombre, String descripcion) {
        this.weaponName = nombre;
        this.description = descripcion;
    }

    public String getNombre() {
        return weaponName;
    }

    public void setNombre(String nombre) {
        this.weaponName = nombre;
    }

    public String getDescripcion() {
        return description;
    }

    public void setDescripcion(String descripcion) {
        this.description = descripcion;
    }
}
