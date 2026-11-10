package es.cesur.dam.model;

public class Dispositivo {
    private Long id;
    private String nombre;
    private String tipo;
    private Boolean operativo;

    public Dispositivo() {
    }

    public Dispositivo(Long id, String nombre, String tipo, Boolean operativo) {
        this.id = id;
        this.nombre = nombre;
        this.tipo = tipo;
        this.operativo = operativo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Boolean getOperativo() {
        return operativo;
    }

    public void setOperativo(Boolean operativo) {
        this.operativo = operativo;
    }

    @Override
    public String toString() {
        return "Dispositivo{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", tipo='" + tipo + '\'' +
                ", operativo=" + operativo +
                '}';
    }
}