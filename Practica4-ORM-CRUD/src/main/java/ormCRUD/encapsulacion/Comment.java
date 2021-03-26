package ormCRUD.encapsulacion;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


@Entity()
public class Comment implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Comentario")
    private String id;
    @ManyToOne
    private Product product;
    @Column(name = "Comment")
    private String comentario;
    @Column(name = "Fecha")
    private Date fecha;

    public Comment() {
    }

    public Comment(Product product, String comentario, Date fecha) {
        this.product = product;
        this.comentario = comentario;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Product getProducto() {
        return product;
    }

    public void setProducto(Product product) {
        this.product = product;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
