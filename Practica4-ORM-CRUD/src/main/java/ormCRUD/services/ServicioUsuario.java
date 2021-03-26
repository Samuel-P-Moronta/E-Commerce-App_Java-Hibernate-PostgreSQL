package ormCRUD.services;

import ormCRUD.encapsulacion.User;

public class ServicioUsuario extends ManejadorBD<User>{

    public ServicioUsuario() {
        super(User.class);
    }

}
