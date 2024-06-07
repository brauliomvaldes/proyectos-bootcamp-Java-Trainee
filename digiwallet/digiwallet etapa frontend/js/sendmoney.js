// wallet digital
// utilizando localstorage para su almacenamiento
// no considera almacenamiento de usuario fuera del admin

$(document).ready(function () {

  // consula autorización
  if(!sessionStorage.getItem('auth')){
    window.location.href = '../codigo/login.html';
  }
  // logout
  $('#logoutBtn').click(function () {
    sessionStorage.setItem('auth', false);
    window.location.href = '../codigo/login.html';
  });

  // define variable necesarias para registro de transacciones
  let user = '';
  let tipo = '';
  let monto = 0;
  let balance = 0;
  let movimientoCuenta = '';
  // almacena las transacciones
  let transac = [];
  // almacena todos los destinatarios de transferencias a terceros
  let contactos = [];
  // almacena el nombre del destinatario 
  let destinatario = '';
  // set fecha actual, toma el año
  let fecha = new Date().toLocaleString().split(',')[0];

  // recuperar billetera 
  if (localStorage.getItem('wallet')) {
    transac = (JSON.parse(localStorage.getItem('wallet'))).trans;
  } else {
    transac = [];
  }
  if (transac.length > 0) {
    cargaHistoryTransac();
  }

  // carga historial transacciones
  function cargaHistoryTransac() {
    transac.forEach((e) => {
      let elementos = e.split(',');
      creaRegistro(elementos[0], elementos[1], elementos[2], elementos[3], elementos[4])
    })
  }

  function creaRegistro(fecha, tipo, monto, balance, destinatario) {
    $("#registros").prepend('<tr><td>' + fecha + '</td><td>' + tipo + '</td><td>' + monto + '</td><td>' + balance + '</td><td>' + destinatario + '</td></tr>');
  }

  // recupera el balance del cliente
  getBalance();
  if (balance == null) {
    balance = 0;
  }

  if(balance > 0){
    $("#titulo-movimiento").text('Transferencia de Fondos');
    // muestra
    $("#operaciones-cuenta").removeAttr("hidden");
    $("#transferirBtn").removeAttr("hidden");
    $("#transferirBtn").removeAttr("disabled");


  }else{
    swal("Proceso no Disponible!", "No cuenta con fondos suficientes para realizar transferencia", "warning");
  }

  
  // cuando ingresa por primera vez monto esta vacio ''
  // esto significa que se debe actualizar el saldo por primera vez
  if ($('#amount').val() === '') {
    updateWallet();
  }

  // girar dinero
  $('#girarBtn').click(function () {
    let giro = parseInt($('#amount').val());
    if (!isNaN(giro) && giro > 0 && giro <= balance) {
      balance -= giro;
      // datos para registro de transaccion
      tipo = 'Giro';
      monto = giro;
      destinatario = '->' + user;
      generaTransaccion()
      swal("Operación Exitosa!", "Giro de dinero exitoso", "success");
    } else {
      swal("El monto no es válido", "ingrese un monto positivo", "warning");
    }
  });

  function generaTransaccion() {
    // primero crea la transaccion
    saveTransac();
    // registra la trasaccion en la billetera
    updateWallet();
    $('#amount').val('');
  }




  // transferir dinero a un tercero
  $('#transferirBtn').click(function () {
    
    // limpia lista elementos creados para los destinatarios
    const list = document.getElementById("destinatario");
    while (list.hasChildNodes()) {
      list.removeChild(list.firstChild);
    }
    // creación de nuevos elementos para realizar transferencia
    let nuevosElementosHtml = creacionElementosHtml();
    $("#destinatario").append(nuevosElementosHtml);
    // selecciona el select de los destinatario
    const selectDestinatario = document.querySelector("#listaContactos");
    // crea evento onChange al select
    selectDestinatario.addEventListener("change", accionMostrarDestinatario);
    // selecciona el boton crear destinatario
    const botonCrearDestinatario = document.querySelector("#nuevoDestinatarioBtn");
    // crea evento click al botón seleccionado
    botonCrearDestinatario.addEventListener("click", accionCrearDestinatario);
    // selecciona el boton creado para confirmar transacción
    const botonConfirma = document.querySelector("#confirmarBtn");
    // crea evento click al botón seleccionado
    botonConfirma.addEventListener("click", accionConfirmaTransferir);
    // selecciona el boton creado para confirmar transacción
    const botonCancelar = document.querySelector("#cancelarBtn");
    // crea evento click al botón seleccionado
    botonCancelar.addEventListener("click", accionCancelarTransferir);
    
    //modalCreaDestinatario();
    // deshabilito botón transferir para no volver a crear los elementos
    $('#transferirBtn').prop('disabled', true);
    $('#depositarBtn').prop('disabled', true);
    $('#girarBtn').prop('disabled', true);
    $("#destinatarioDiv").removeAttr("hidden");
    $("#nuevoDestinatarioBtn").removeAttr("hidden");
    // recarga lista de destinatario
    // recaga combobox
    recargaComboboxListaDestinatario();
    
  });

  // muestra modal para creacion de nuevos destinatarios
  const accionCrearDestinatario = ()=>{
    $('#destinatarioModal').modal('show')
  };
  
  
  // lee los destinatarios almacenados para incluirlos en combobox destinatarios
  const recargaComboboxListaDestinatario = () =>{
    let elementoOption='';
    const lista = document.getElementById("listaContactos");
    contactos.forEach(function (value, key) {
        elementoOption = '<option value="' + value + '">'+value+'</option>';
        lista.insertAdjacentHTML("beforeend",elementoOption);
    });
  }
  
  
  // crea elemento html de forma dínamica para realizar transferencia de dinero
  //
  const creacionElementosHtml = () => {
    // como práctica de como agregar elementos de forma dinámica a otros 
    // elementos html por medio de js
    let elementoHtml = '';
    elementoHtml += '<div class="input-group mb-3">'; 
    elementoHtml +=   '<div class="contactosDiv input-group-text">';
    elementoHtml +=     '<span class="input-group-text">Destinatario</span>';
    elementoHtml +=     '<select class="form-select" selected name="listaContactos" id="listaContactos">';
    elementoHtml +=        '<option value="" selected>nombre del destinatario</option>';
    elementoHtml +=     '</select>';
    elementoHtml +=     "<button type='button' class='form-control btn btn-warning'";
    elementoHtml +=         " data-toggle='modal' data-target='#destinatarioModal' data-whatever=''";
    elementoHtml +=         "id='nuevoDestinatarioBtn'>";
    elementoHtml +=         "Nuevo Destinatario</button>";
    elementoHtml +=   '</div>';
    elementoHtml += '</div>';
    elementoHtml += '<div>';
    elementoHtml +=   '<h3 id="txtDestinatarioSeleccionado"></h3>';
    elementoHtml +=   '<br>';
    elementoHtml +=   "<button class='form-control btn btn-success' id='confirmarBtn'>";
    elementoHtml +=   "Confirmar la Transferencia</button>";
    elementoHtml +=   "<button class='form-control btn btn-danger' id='cancelarBtn'>";
    elementoHtml +=   "Cancela la Transferencia</button>";
    elementoHtml += "</div>";
    
    return elementoHtml;
  };
  
  // mostrar destinatario seleccionado
  const accionMostrarDestinatario = ()=>{
    // lee destinatario seleccionado
    let d = $("#listaContactos").val().trim();
    if(d.length  > 0){
      let nombre = d.split('-')[0];
      $('#txtDestinatarioSeleccionado').text(nombre);
    }
  }

  // cancela el proceso de tranferencia de dinero
  const accionCancelarTransferir = ()=>{
  // oculta botones
    $("#operaciones-cuenta").prop("hidden", true);
    $("#destinatarioDiv").prop("hidden", true);
    $("#nuevoDestinatarioBtn").prop("hidden", true);
    // limpia campos de nuevo destinatario
    $("#recipient-name").val('');
    $("#recipient-nrocuenta").val('');
  }


  // una vez cerrado el modeal destinatario se
  // agrega nuevo destinatario a combobox destinatarios
  $(".cerrarModal").click(function(){
    $("#destinatarioModal").modal('hide');
    let nombre = $("#recipient-name").val().trim();
    let cuenta = $("#recipient-nrocuenta").val().trim();
    let banco = $("#recipient-banco").val().trim();
    if(nombre.length > 0 && cuenta.length > 0 && banco.length > 0){
      let nd = nombre+' - cta nro:'+cuenta+ ' '+banco;
      document.getElementById("listaContactos")
      .insertAdjacentHTML("beforeend",'<option value="' + nd + '">'+nd+'</option>');
      // registra nuevo contacto
      registraNvoDestinatario(nd);
      swal("Operación Exitosa!", "Destinatario creado con exitoso", "success");
    }else{
      swal("Operación Fallida!", "Destinatario no fue creado", "warning");
    }
    // limpia campos de nuevo destinatario
    $("#recipient-name").val('');
    $("#recipient-nrocuenta").val('');
  });

  // agrega nuevo destinatario a la lista de destinatarios 
  const registraNvoDestinatario = (nvoDestinatario)=>{
    // agrega nuevo destinatario
    contactos.push(nvoDestinatario);
    // actualiza estado de wallet
    actualizaDigiWallet();
  }

  // realiza la trasaccion de dinero a tercero
  const accionConfirmaTransferir = () => {
    monto = parseInt($('#amount').val());
    if(monto <= balance){
      destinatario = $("#listaContactos").val().trim();
      if (destinatario.length > 0 && monto > 0) {
        // simula evento click para realizar la transaccion
        commitTransaccion(destinatario, monto);
        // limpiar campos para repetir misma transacción
        $('#amount').val('');
        $("#listaContactos").val('');
      } else {
        swal("Transacción fallida", "no fue posible realizar la transaación por destinatario o monto no vàlido.", "error");
      }
      // limpia campo monto
      // habilito botón transferir para permitir transferencias
      $('#transferirBtn').prop('disabled', false);
      $('#depositarBtn').prop('disabled', false);
      $('#girarBtn').prop('disabled', false);
      // elimina los elementos hijos creados para realizar transferencias
      $("#destinatario").empty();
    }else{
      swal("Transacción fallida!", "El monto ingresado supera los fondos disponibles para transferir", "warning");
    }
  }

  // confirma transferencia de dinero a tercero
  const commitTransaccion = (destinatario, montoTransferencia) => {
    // valida que el monto ingresado sea válido,para la operación
    if (!isNaN(montoTransferencia) && montoTransferencia > 0 && montoTransferencia <= balance) {
      balance -= montoTransferencia;
      // datos para registro de transaccion
      tipo = 'Trf';
      monto = montoTransferencia;
      destinatario = '->' + destinatario;
      generaTransaccion()
      swal("Operación Exitosa!", "Transferencia de dinero exitosa!", "success");
    } else {
      swal("El monto no es válido", "ingrese un monto positivo.", "warning");
    }
  };

  // crea la transacciones para la billetera y el historial
  function saveTransac() {
    let cifraMonto = (-monto).toLocaleString("es-ES");
    let cifraBalance = balance.toLocaleString("es-ES");
    movimientoCuenta = fecha + ',' + tipo + ',' + cifraMonto + ',' + cifraBalance + ',' + destinatario;
    transac.push(movimientoCuenta);
    creaRegistro(fecha, tipo, cifraMonto, cifraBalance, destinatario);
  }

  // actualiza saldo en la billetera con la trasacciones creada
  function updateWallet() {
    let cifraMonto = balance.toLocaleString("es-ES");
    $('#balance').text(cifraMonto);
    actualizaDigiWallet();
  }

  // Almacena la billetera
  function actualizaDigiWallet() {
    const myWallet = {
      user: user,
      saldo: balance,
      trans: transac,
      destinatarios: contactos
    };
    // almacena la billetera
    localStorage.setItem('wallet', JSON.stringify(myWallet));
  }

 
  // Recuperando la billetera
  function getBalance() {
    const wallet = localStorage.getItem('wallet');
    if (wallet != null) {
      user = JSON.parse(wallet).user;
      transac = JSON.parse(wallet).trans;
      balance = JSON.parse(wallet).saldo;
      contactos = JSON.parse(wallet).destinatarios;
    }
  }

});


