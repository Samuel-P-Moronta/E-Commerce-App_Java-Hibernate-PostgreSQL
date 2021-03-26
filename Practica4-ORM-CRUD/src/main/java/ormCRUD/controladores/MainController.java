package ormCRUD.controladores;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import ormCRUD.encapsulacion.*;
import ormCRUD.util.ControladorBase;
import io.javalin.Javalin;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

import static io.javalin.apibuilder.ApiBuilder.*;
import static io.javalin.apibuilder.ApiBuilder.post;

public class MainController extends ControladorBase {

    public MainController(Javalin app) {
        super(app);
    }

    private static String mpCryptoPassword = "BornToFight";

    private static int alerta = -1;
    private static int numeroPagina = 1;
    private static int paginacion = 1;
    static String URL = "";

    @Override
    public void aplicarRutas() {
        app.get("/", ctx -> ctx.redirect("/products"));
        app.routes(() -> {
            path("/products", () -> {
                get("/", ctx -> {
                    if (ctx.queryParam("pagina") == null) {
                        paginacion = 1;
                    } else {
                        numeroPagina = Integer.parseInt(ctx.queryParam("pagina"));
                        paginacion = numeroPagina == 0 ? 1 : numeroPagina;
                    }
                    alerta = -1;
                    Map<String, Object> modelo = new HashMap<>();
                    List<Product> auxProduct = Controller.getInstance().paginacionProducto(paginacion);
                    modelo.put("titulo", "Lista de producto disponible:");
                    modelo.put("lista", auxProduct);
                    modelo.put("pagina", paginacion);

                    int cantProducto = (int) Math.ceil((double) Controller.getControladora().cantProducto() / 3);
                    modelo.put("cantPagina", cantProducto);

                    String a = "(" + (((CarroCompra) ctx.sessionAttribute("cart")).getCont()) + ")";
                    modelo.put("cantCarrito", a);
                    ctx.render("/public/html/productList.html", modelo);
                });
                before("/admin", ctx -> {
                    User user = ctx.sessionAttribute("usuario");
                    if (user == null) {
                        ctx.redirect("/iniciarSession");
                    }
                });
                get("/admin", ctx -> {
                    List<Product> auxProduct = Controller.getInstance().getMiProducto();
                    Map<String, Object> model = new HashMap<>();
                    model.put("listProduct", auxProduct);

                    if (ctx.sessionAttribute("cart") == null) {
                        model.put("itemCant", 0);
                    }
                    ctx.render("/public/html/login.html", model);
                });
                before("/salesHistory", ctx -> {
                    User auxUser = ctx.sessionAttribute("usuario");
                    if (auxUser == null) {
                        URL = ctx.req.getRequestURI();
                        ctx.redirect("/html/login.html");
                    }
                });
                get("/salesHistory", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("titulo", "Producto en el carrito");
                    try {
                        List<Invoice> aux = new ArrayList<>();
                        aux = Controller.getControladora().getMisVentasProducto();
                        modelo.put("lista", aux);
                        System.out.print("/SIZE: " + aux.size());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ctx.render("/public/html/salesHistory.html", modelo);
                });
            });
            path("/cart", () -> {
                get("/", ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    modelo.put("valor", alerta);
                    ArrayList<IteamVenta> aux = (ArrayList<IteamVenta>) (((CarroCompra) ctx.sessionAttribute("cart")).getListaProducto());
                    String a = "(" + (((CarroCompra) ctx.sessionAttribute("cart")).getCont()) + ")";
                    modelo.put("cantCarrito", a);
                    modelo.put("listProductInCart", aux);
                    modelo.put("total", (((CarroCompra) ctx.sessionAttribute("cart")).calcularTotal()));
                    ctx.render("/public/html/shoppingCart.html", modelo);
                });
                post("/deleteProductToCart", ctx -> {
                    String id = ctx.formParam("idBorrar");
                    try{
                        ((CarroCompra) ctx.sessionAttribute("cart")).deleteProductInCart(id);
                        System.out.println("DELETE...[SUCCESS]");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    ctx.redirect("/cart");
                });
                post("/addClient", ctx -> {
                    String nombreCliente = ctx.formParam("nombre");
                    System.out.print("\n Realizando compra*************\n");
                    Map<String, Object> modelo = new HashMap<>();
                    List<Product> auxProduct = Controller.getInstance().getMiProducto();
                    try {
                        if (((CarroCompra) ctx.sessionAttribute("cart")).getCont() > 0) {
                            ArrayList<IteamVenta> aux = (ArrayList<IteamVenta>) (((CarroCompra) ctx.sessionAttribute("cart")).getListaProducto());

                            Date fecha = new Date();
                            Invoice auxVenta = new Invoice(fecha, nombreCliente, aux);
                            Controller.getInstance().agregarVenta(auxVenta);
                            ((CarroCompra) ctx.sessionAttribute("cart")).limpiarCarrito();
                            ctx.redirect("/cart");
                            alerta = 0;
                        } else {
                            ctx.redirect("/cart");
                            alerta = 1;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            });
            path("/salesHistory", () -> {
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    try {
                        modelo.put("lista", Controller.getControladora().getMisVentasProducto());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    ctx.render("/public/html/salesHistory.html", modelo);
                });
            });
            path("/admin", () -> {
                get(ctx -> {
                    Map<String, Object> modelo = new HashMap<>();
                    List<Product> auxProduct = Controller.getInstance().getMiProducto();
                    modelo.put("titulo", "Listado de producto");
                    modelo.put("lista", auxProduct);
                    ctx.render("/public/html/admin.html", modelo);
                });
                post("/", ctx -> {
                    User user = ctx.sessionAttribute("usuario");
                    if (user == null) {
                        String nombre_Usuario = ctx.cookie("Username");
                        String password_Encriptada = ctx.cookie("Password");
                        if (nombre_Usuario != null && password_Encriptada != null) {
                            StandardPBEStringEncryptor decryptor = new StandardPBEStringEncryptor();
                            decryptor.setPassword(mpCryptoPassword);
                            user = Controller.getInstance().autenticarUsuario(nombre_Usuario, decryptor.decrypt(password_Encriptada));
                            if (user != null) {
                                ctx.sessionAttribute("usuario", user);
                            }
                        }
                        ctx.redirect("/iniciarSession");
                    }
                });
            });
        });
        app.get("/administrado", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            List<Product> auxProduct = Controller.getInstance().getMiProducto();
            modelo.put("titulo", "Listado de producto");
            modelo.put("lista", auxProduct);
            ctx.render("/public/html/productList.html", modelo);

        });
        app.post("/addToCart", ctx -> {

            String idProducto = ctx.formParam("idProduct");
            String cantProducto = ctx.formParam("cant");
            Product product = Controller.getInstance().buscarProducto(idProducto);
            if (product != null) {
                int cantidad = Integer.parseInt(cantProducto);
                ((CarroCompra) ctx.sessionAttribute("cart")).agregarProducto(product, cantidad);
            }
            ctx.redirect("/products" + "?pagina=" + numeroPagina);
        });

        app.post("/limpiarCarro", ctx -> {
            ((CarroCompra) ctx.sessionAttribute("cart")).limpiarCarrito();
            ctx.redirect("/cart");

        });
        app.before(ctx -> {
            CarroCompra carrito = ctx.sessionAttribute("cart");

            if (carrito == null) {
                carrito = new CarroCompra();
                ctx.sessionAttribute("cart", carrito);
            }
        });
        app.get("/agregarcomentario", ctx -> {
            String idProducto = ctx.queryParam("Id");
            System.out.println("El tipo de datos recibido: " + ctx.header("Comtemt") + "Matricula:" + ctx.queryParam("Id"));
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Producto en el carrito");
            Product aux = null;
            aux = Controller.getControladora().buscarProducto(idProducto);
            if (aux != null) {
                List<Foto> a = aux.getFotoList();
                modelo.put("id", idProducto);
                modelo.put("fotoList", a);
                System.out.println("LA CANTIDAD DE IAMGENES ES: " + a.size());
                List<Comment> lista = Controller.getInstance().getComentarios(idProducto);
                modelo.put("listaComentario", lista);
                ctx.render("/public/html/commentPrincipal.html", modelo);
            } else {
                ctx.result("Error al buscar la info solicitada");
            }
        });
        app.post("/agregarComentario", ctx -> {
            String id = ctx.formParam("id");
            Product aux = null;
            aux = Controller.getControladora().buscarProducto(id);
            Map<String, Object> modelo = new HashMap<>();
            Date fecha = new Date();
            String comentario = ctx.formParam("inputMessage");
            Controller.getInstance().agregarComentario(comentario, fecha, id);
            List<Comment> lista = Controller.getInstance().getComentarios(id);
            List<Foto> a = aux.getFotoList();
            modelo.put("id", id);
            modelo.put("fotoList", a);
            modelo.put("listaComentario", lista);
            ctx.render("/public/html/commentPrincipal.html", modelo);

        });
        app.get("/eliminarComentario", ctx -> {
            String idComentario = ctx.queryParam("Id");
            String idProducto = Controller.getInstance().buscarCOmentario(idComentario).getProducto().getId();
            boolean a = Controller.getInstance().borrarComentario(idComentario);
            ctx.redirect("view" + "?Id=" + idProducto);
        });
        app.get("/logout", ctx -> {
            System.out.println("Eliminado Cookie: Usuario & Password\n");
            ctx.removeCookie("usuario");
            ctx.removeCookie("password");
            ctx.redirect("/iniciarSession");
            ctx.sessionAttribute("usuario", null);
        });
        app.get("/iniciarSession", ctx -> {
            ctx.render("/public/html/login.html");
        });
        app.post("/autenticar", ctx -> {
            String estadoRecodar = "";
            String nombreUsuario = ctx.formParam("Username");
            String password = ctx.formParam("Password");
            User auxUser = null;
            auxUser = Controller.getInstance().autenticarUsuario(nombreUsuario, password);
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("usuario", auxUser);
            if (auxUser != null) {
                estadoRecodar = ctx.formParam("Recordar");
                if (estadoRecodar != null) {
                    if (estadoRecodar.equalsIgnoreCase("ON")) {
                        System.out.println("Creando cookie...\n");
                        StandardPBEStringEncryptor encryptor = new StandardPBEStringEncryptor();
                        encryptor.setPassword(mpCryptoPassword);
                        encryptor.encrypt(auxUser.getPassword());
                        ctx.cookie("usuario", auxUser.getUsuario(), 604800);// se crea la cookie por una semana
                        ctx.cookie("password", encryptor.encrypt(auxUser.getPassword()), 604800);
                    }
                }
                ctx.sessionAttribute("usuario", auxUser);
                ctx.redirect("/admin");
            } else {
                modelo.put("Error", "Please check username & password! ");
                ctx.render("/public/html/login.html", modelo);
                System.out.print("\n Usuario no encontrado. Revise su nombre  de usuario y su password");
            }
        });
        app.get("/sales", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            try {
                modelo.put("lista", Controller.getControladora().getMisVentasProducto());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ctx.render("/public/html/salesHistory.html", modelo);
        });
        app.before("/zonaAdmin/lista-Venta", ctx -> {
            User user = ctx.sessionAttribute("usuario");
            if (user == null) {
                ctx.redirect("/iniciarSession");
            }
        });
        app.get("/zonaAdmin/lista-Venta", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Producto en el carrito");
            try {
                List<Invoice> aux = new ArrayList<>();
                aux = Controller.getControladora().getMisVentasProducto();
                modelo.put("lista", aux);
                System.out.print("/SIZE: " + aux.size());
            } catch (Exception e) {
                e.printStackTrace();
            }
            ctx.render("/public/html/salesHistory.html", modelo);

        });
        app.post("/Menu", ctx -> {
            Map<String, Object> modelo = new HashMap<>();
            System.out.print(ctx.formParam("addName"));
            String name = ctx.formParam("addName");
            String descripcion = ctx.formParam("addDescripcion");
            String p = "0.00";
            p = ctx.formParam("addPrice");
            BigDecimal precio = new BigDecimal(p);
            List<Foto> misFotos = new ArrayList<>();

            ctx.uploadedFiles("foto").forEach(uploadedFile -> {
                System.out.println("\n ENTRANDO A METODO PARA CARGAR IMAGENES.... \n");
                try {
                    byte[] bytes = uploadedFile.getContent().readAllBytes();
                    String encodedString = Base64.getEncoder().encodeToString(bytes);
                    Foto foto = new Foto(uploadedFile.getFilename(), uploadedFile.getContentType(), encodedString);
                    misFotos.add(foto);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            if (misFotos.size() > 0) {
                Controller.getInstance().crearProducto(name, precio, descripcion, misFotos);
            }
            ctx.redirect("/admin");
        });
        app.post("/borrar", ctx -> {
            String id = ctx.formParam("idBorrar");
            if (Controller.getInstance().borrarProducto(id) == true) {
                ctx.redirect("/admin");
            }
        });
        app.post("/administrado", ctx -> {
            System.out.print("Reciviendo por metodo POST, para editar producto\n");
            Map<String, Object> modelo = new HashMap<>();
            String id = ctx.formParam("id");
            String name = ctx.formParam("nombre");
            String p = "0.00";
            p = ctx.formParam("precio");
            BigDecimal precio = new BigDecimal(p);
            System.out.println("\nID=" + id + "PRECIO: " + p + "\n" + "Nombre=" + name);
            System.out.print(Controller.getInstance().actulizarProducto(id, name, precio));
            if (true == Controller.getInstance().actulizarProducto(id, name, precio)) {
            }
            List<Product> auxProduct = Controller.getInstance().getMiProducto();
            modelo.put("titulo", "Listado de producto");
            modelo.put("lista", auxProduct);
            ctx.render("/public/html/admin.html", modelo);

        });
        app.get("/view", ctx -> {

            System.out.println("El tipo de datos recibido: " + ctx.header("Comtemt") + "Matricula:" + ctx.queryParam("Id"));

            Map<String, Object> modelo = new HashMap<>();
            modelo.put("titulo", "Producto en el carrito");
            String idProducto = ctx.formParam("idView");
            Product aux = null;
            aux = Controller.getControladora().buscarProducto(ctx.queryParam("Id"));
            List<Comment> lista = Controller.getInstance().getComentarios(aux.getId());
            if (aux != null) {
                List<Foto> a = aux.getFotoList();
                modelo.put("lista", a);
                modelo.put("listaComentario", lista);
                ctx.render("/public/html/comment.html", modelo);
            } else {
                ctx.result("Error al buscar la info solicitada");
            }
        });

    }

}
