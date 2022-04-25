package sv.edu.udb.catedradsm.models;

import java.util.Date;

public class CitasModel {
    private int id;
    private int idCliente;
    private int idEstilista;
    private int idSucursal;
    private String motivo;
    private String fecha;
    private String hora;
    private double costo;

    public CitasModel(int id) {
        this.id = id;
    }

    public CitasModel(int id, int idCliente, int idEstilista, int idSucursal, String motivo, String fecha, String hora, double costo) {
        this.id = id;
        this.idCliente = idCliente;
        this.idEstilista = idEstilista;
        this.idSucursal = idSucursal;
        this.motivo = motivo;
        this.fecha = fecha;
        this.hora = hora;
        this.costo = costo;
    }

    public CitasModel(int idCliente, int idEstilista, int idSucursal, String motivo, String fecha, String hora, double costo) {
        this.idCliente = idCliente;
        this.idEstilista = idEstilista;
        this.idSucursal = idSucursal;
        this.motivo = motivo;
        this.fecha = fecha;
        this.hora = hora;
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdEstilista() {
        return idEstilista;
    }

    public void setIdEstilista(int idEstilista) {
        this.idEstilista = idEstilista;
    }

    public int getIdSucursal() {
        return idSucursal;
    }

    public void setIdSucursal(int idSucursal) {
        this.idSucursal = idSucursal;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    @Override
    public String toString() {
        return "CitasModel{" +
                "id=" + id +
                ", idCliente='" + idCliente + '\'' +
                ", idEstilista='" + idEstilista + '\'' +
                ", idSucursal='" + idSucursal + '\'' +
                ", motivo='" + motivo + '\'' +
                ", fecha='" + fecha + '\'' +
                ", hora='" + hora + '\'' +
                ", costo='" + costo + '\'' +
                '}';
    }
}
