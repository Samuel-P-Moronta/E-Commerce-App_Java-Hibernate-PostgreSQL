package ormCRUD.services;

import ormCRUD.encapsulacion.Comment;
import ormCRUD.encapsulacion.Product;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

public class ServicioComentario extends ManejadorBD<Comment>{
    public ServicioComentario() {
        super(Comment.class);
    }

    public List<Comment> getComentarios(Product product){
        String id = product.getId();
        List<Comment> commentList = new ArrayList<>();
        EntityManager entityManager = getEntityManager();
        EntityManager em = getEntityManager();
        Query query = em.createNativeQuery("select * from Comment  WHERE Comment.PRODUCT_ID LIKE :id", Comment.class);
        query.setParameter("id", id);
        List<Comment> lista = query.getResultList();
        return lista;
    }
}
