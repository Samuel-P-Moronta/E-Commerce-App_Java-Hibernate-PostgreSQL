
console.log("Entrado a script")
$(document).ready(function($){
    var array = [];
    console.log("Entrando a funcion editado")

    $('#table tbody').on('click','#ok',function(){

        var curRow = $(this).closest('tr');

        var col0 = curRow.find('td:eq(1)').text();
        var col1 = curRow.find('td:eq(2)').text();
        var col2 = curRow.find('td:eq(3)').text();

        array.push(col1)
        array.push(col2)
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
        document.f2.idBorrar.value = col0;

    });
    $('#table tbody').on('click','#b1',function(){

    	console.log("Entrando a view");
        //	console.log("Entrando a funcion");
        var curRow = $(this).closest('tr');
        var col0 = curRow.find('td:eq(1)').text();
        var col1 = curRow.find('td:eq(2)').text();
        var col2 = curRow.find('td:eq(3)').text();

        console.log(col0)
        alert("Bienvenido")
        document.table.
        document.table.view.idView.value = col0;
        document.view.idView.value = col0;
        alert(col0);

    });
});
