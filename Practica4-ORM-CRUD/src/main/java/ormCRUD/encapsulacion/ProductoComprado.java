package ormCRUD.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Embeddable
@Table(name = "ProductoComprado")
public class ProductoComprado implements Serializable {
    private static final long serialVersionUID = 1L;


    @Column(name = "nombre")
    private String nombre;
    @Column(name = "precio")
    private BigDecimal precio;

    public ProductoComprado() {
    }

    public ProductoComprado(Product product) {
        this.nombre = product.getNombre();
        this.precio = product.getPrecio();
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


}
