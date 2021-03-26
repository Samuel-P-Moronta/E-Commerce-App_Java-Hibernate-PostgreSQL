package ormCRUD.encapsulacion;


import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;


@Entity() //para indicar que la entidad será maneja por el provedor de persistencia

@Table(name = "Product")
public class Product implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private BigDecimal precio;
    @ElementCollection(fetch = FetchType.EAGER)
    List<Foto> fotoList;
    @Column(name = "Descripcion")
    private String descripcion;
    public Product() {
    }

    public Product(String nombre, BigDecimal precio, String descripcion, List<Foto> fotoList) {
        this.nombre = nombre;
        this.precio = precio;
        this.descripcion = descripcion;
        this.fotoList = fotoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public List<Foto> getFotoList() {
        return fotoList;
    }

    public void setFotoList(List<Foto> fotoList) {
        this.fotoList = fotoList;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}