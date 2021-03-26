package ormCRUD.encapsulacion;

import ormCRUD.services.ServicioComentario;
import ormCRUD.services.ServicioProducto;
import ormCRUD.services.ServicioUsuario;
import ormCRUD.services.ServicioVenta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Controller {
    private static Controller controller = null;
    private final ServicioProducto servicioProducto = new ServicioProducto();
    private final ServicioUsuario servicioUsuario = new ServicioUsuario();
    private final ServicioVenta servicioVenta = new ServicioVenta();
    private final ServicioComentario servicioComentario = new ServicioComentario();
    public Controller() {
        BigDecimal aux = new BigDecimal(10);
    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    public static Controller getControladora() {
        return controller;
    }

    /**
     * Agreando controladores para la conexion de BD
     * @param user
     * @return
     */
    public boolean agregarUsuario(User user){

        return servicioUsuario.crear(user);
    }

    public User autenticarUsuario(String usuario, String password){
        User auxUser = servicioUsuario.buscar(usuario);
        if(auxUser !=null){
            if (!auxUser.getPassword().equals(password)){
                System.out.println("No se pudo  autentificar el usuario de forma correcta! \n");
                return null;
            }
        }
        return auxUser;
    }

    //GRUD PRODUCTO
    public void crearProducto(String name, BigDecimal precio, String descripcion, List<Foto> fotoList){
        servicioProducto.crear(new Product(name,precio, descripcion, fotoList));
    }

    private List<CarroCompra> miCarroComprea = new ArrayList<>();
    public List<CarroCompra> getMiCarroComprea() {
        return miCarroComprea;
    }
    public void setMiCarroComprea(List<CarroCompra> miCarroComprea) {
        this.miCarroComprea = miCarroComprea;
    }

    private int contID = 0;

    public List<Product> getMiProducto() {
        return servicioProducto.explorarTodo();
    }

    public Product buscarProducto(String idProducto) {
        return servicioProducto.buscar(idProducto);
    }

    public void agregarVenta(Invoice auxVenta) {
        servicioVenta.crear(auxVenta);
    }

    public List<Invoice> getMisVentasProducto() {
        return servicioVenta.explorarTodo();
    }

    public boolean borrarProducto(String id) {
        Product auxProduct = servicioProducto.buscar(id);
        List<Comment> list = servicioComentario.getComentarios(auxProduct);
        for (Comment co : list) {
            borrarComentario(co.getId());
        }
        return  servicioProducto.eliminar(auxProduct.getId());
    }

    public boolean actulizarProducto(String id, String name, BigDecimal precio) {
        Product aux = servicioProducto.buscar(id);
        boolean estado = false;
        if(aux!=null){
            aux.setNombre(name);
            aux.setPrecio(precio);
             estado = servicioProducto.editar(aux);
        }
        return estado;
    }

    public boolean agregarComentario(String comentario, Date fecha, String idProducto){
        Product auxProduct =  servicioProducto.buscar(idProducto);

        if(auxProduct !=null){
            return servicioComentario.crear(new Comment(auxProduct, comentario, fecha));
        }
        return false;
    }
    public List<Comment> getComentarios(String idProducto){
        return servicioComentario.getComentarios(servicioProducto.buscar(idProducto));
    }
    public boolean borrarComentario(String idComentario){

        return servicioComentario.eliminar(idComentario);
    }
    public Comment buscarCOmentario(String idComentario){

        return servicioComentario.buscar(idComentario);
    }
    public List<Product> paginacionProducto(int pagina) {
        return  servicioProducto.paginacionProducto(pagina);
    }
    public int cantProducto(){
        return servicioProducto.cantProducto();
    }
    public void crearDatosPorDefecto(){
        //Usuario por defecto
        if(servicioProducto.buscar("admin")==null){
            servicioUsuario.crear(new User("admin", "admin"));
        }
    }

}