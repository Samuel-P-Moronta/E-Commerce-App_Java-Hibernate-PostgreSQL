package ormCRUD.encapsulacion;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "User")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "usuario")
    private String usuario;
    @Column(name = "password")
    private String password;

    public User(String nombre, String password) {
       // this.id = id;
        this.usuario = nombre;
        this.password = password;
    }

    public User() {
    }
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String nombre) {
        this.usuario = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
