package ormCRUD.encapsulacion;

import com.sun.istack.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity()
public class Invoice implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "fecha_Compra")
    private Date fechaCompra;
    @NotNull
    private String nombreCliente;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<IteamVenta> listaProducto;

    public Invoice(Date fechaCompra, String nombreCliente, List<IteamVenta> listaProducto) {

        this.fechaCompra = fechaCompra;
        this.nombreCliente = nombreCliente;
        this.listaProducto = listaProducto;
    }
    public Invoice(){


    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public List<IteamVenta> getListaProducto() {
        return listaProducto;
    }

    public void setListaProducto(List<IteamVenta> listaProducto) {
        this.listaProducto = listaProducto;
    }
}
