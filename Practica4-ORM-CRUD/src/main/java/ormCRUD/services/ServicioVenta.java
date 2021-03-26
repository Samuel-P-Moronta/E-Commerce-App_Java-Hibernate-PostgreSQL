package ormCRUD.services;

import ormCRUD.encapsulacion.Invoice;
public class ServicioVenta extends ManejadorBD<Invoice>{

    public ServicioVenta() {
        super(Invoice.class);
    }

}
