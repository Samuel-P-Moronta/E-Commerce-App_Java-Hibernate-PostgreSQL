console.log("Entrado a script")
$(document).ready(function($){
    var array = [];
    console.log("Entrando a funcion editado")
    /**
     *Se debe poner el id de la table con un # delante
     */
    $('#table tbody').on('click','#ok',function(){
        console.log("Entrando a funcion ok");
        //	console.log("Entrando a funcion");
        var curRow = $(this).closest('tr');
        var col0 = curRow.find('td:eq(1)').text();
        //var col3 = curRow.find('input[type="number"]').val();
        /**
         * Acciendiendo al DOM para pasar el ID de annadir al carrito
         */
        document.fcarro.idProducto =col0;


    });

});