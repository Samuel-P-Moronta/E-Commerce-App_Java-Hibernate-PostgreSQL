
console.log("Entrado a script")
$(document).ready(function($){
    var array = [];
    console.log("Entrando a funcion editado")
    /**
     *Se debe poner el id de la table con un # delante
     */
    $('#table tbody').on('click','#ok',function(){

        //	console.log("Entrando a funcion");
        var curRow = $(this).closest('tr');
        //	var contenido =$('table tbody td');
        //	console.log(contenido.text);
        //	console.log(contenido);
        //   var col1 = curRow.find('td:eq(0)').text();
        var col0 = curRow.find('td:eq(1)').text();
        var col1 = curRow.find('td:eq(2)').text();
        var col2 = curRow.find('td:eq(3)').text();
        //console.log(curRow);
        //console.log(col2, col1);
        array.push(col1)
        array.push(col2)
        //console.log("Con array");
        //alert(array)
        //alert(array);
        //alert($("tr.selected td:eq(1)" ).html());
        //document.getElementsByName('cont1').value = col1;
        //document.getElementById('cont1').innerHTML=col1;
        /**
         * Acciendiendo al DOM para pasar los valores de la table a venta de editar
         */
        document.f1.id.value = col0;
        document.f1.nombre.value = col1;
        document.f1.precio.value = col2;
        console.log(col1);
        console.log(col2);
        console.log(col0);



    });
    $('#table tbody').on('click','#borrar',function(){

        //	console.log("Entrando a funcion");
        var curRow = $(this).closest('tr');
        var col0 = curRow.find('td:eq(1)').text();
        var col1 = curRow.find('td:eq(2)').text();
        var col2 = curRow.find('td:eq(3)').text();
        /**
         * Acciendiendo al DOM para pasar los valores de la table a venta de borrar
         */
        document.f2.idBorrar.value = col0;

    });
    $('#table tbody').on('click','#b1',function(){

        console.log("Entrando a view");
        //	console.log("Entrando a funcion");
        var curRow = $(this).closest('tr');
        var col0 = curRow.find('td:eq(1)').text();
        var col1 = curRow.find('td:eq(2)').text();
        var col2 = curRow.find('td:eq(3)').text();
        /**
         * Acciendiendo al DOM para pasar los valores de la table a venta de borrar
         */
        console.log("BIEN DE BIEN DE VIEND ")
        console.log(col0)
        alert("Bienvenido")
        document.table.
            document.table.view.idView.value = col0;
        document.view.idView.value = col0;
        alert(col0);

    });
});
