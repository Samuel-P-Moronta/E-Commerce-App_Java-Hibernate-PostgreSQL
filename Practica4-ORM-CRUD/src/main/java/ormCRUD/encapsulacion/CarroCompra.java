package ormCRUD.encapsulacion;

import java.math.BigDecimal;


import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class CarroCompra {

    private String id;
    private int cont=0;
    private BigDecimal total;
    private List<IteamVenta> listaProducto;

    public CarroCompra() {
        this.listaProducto = new ArrayList<IteamVenta>();
        total = calcularTotal();
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public List<IteamVenta> getListaProducto() {
        return listaProducto;
    }
    public void limpiarCarrito(){
        this.listaProducto = new ArrayList<>();
        cont =0;
    }
    public int getCont() {
        return cont;
    }
    public void setCont(int cont) {
        this.cont = cont;
    }
    public BigDecimal getTotal() {
        return total;
    }
    public void setTotal(BigDecimal total) {
        this.total = total;
    }


    public void agregarProducto(Product product, int cantidad) {
        IteamVenta comprar = this.buscarIteam(product.getId());
        cont++;
        if (comprar != null) {
            System.out.print("El producto ya se habia agregado");
            this.listaProducto.add(new IteamVenta(new ProductoComprado(product), cantidad));

        } else {
            this.listaProducto.add(new IteamVenta(new ProductoComprado(product), cantidad));
            System.out.print("El producto aun no se ha puesto ");
        }
        setId("CP:"+cont);
        System.out.print(cantidad);
    }

    public void deleteProductInCart(String codigo) {
        this.listaProducto.remove(this.buscarIteam(codigo));
        cont = cont -1;
    }
    private IteamVenta buscarIteam(String idIteam) {
        for (IteamVenta compra : listaProducto) {
            if (compra.getIdIteam().equals(idIteam)) {
                return  compra;
            }
        }
        return null;
    }

    public BigDecimal calcularTotal() {
        BigDecimal total = new BigDecimal(0);
        for (IteamVenta comprar : this.listaProducto) {
            total = total.add(new BigDecimal(comprar.getCantProducto() * comprar.getProductoVenta().getPrecio().doubleValue()));
        }

        return total.setScale(2, RoundingMode.CEILING);
    }
    public void setListaProducto(List<IteamVenta> listaProducto) {
        this.listaProducto = listaProducto;
    }

}
