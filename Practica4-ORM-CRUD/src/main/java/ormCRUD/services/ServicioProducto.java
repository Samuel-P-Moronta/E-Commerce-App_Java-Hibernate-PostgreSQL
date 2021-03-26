package ormCRUD.services;

import ormCRUD.encapsulacion.Product;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;


public class ServicioProducto extends ManejadorBD<Product> {

    public ServicioProducto() {
        super(Product.class);
    }
    public List<Product> paginacionProducto(int pagina){
        int pageSize = 10;
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createNativeQuery("SELECT * FROM Product", Product.class);
        query.setFirstResult((pagina - 1) * pageSize);
        query.setMaxResults(pageSize);
        return query.getResultList();
    }
    public int cantProducto(){
        EntityManager entityManager = getEntityManager();
        String countQ = "SELECT COUNT(P.id) FROM Product P";
        Query countQuery = entityManager.createQuery(countQ);//consulta JQPL
        return ((Number) countQuery.getSingleResult()).intValue();
    }


}
