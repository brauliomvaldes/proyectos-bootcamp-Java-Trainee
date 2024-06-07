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


    $("#titulo-movimiento").text('Depósito de Dinero');
    // muestra
    $("#operaciones-cuenta").removeAttr("hidden");
    $("#depositarBtn").removeAttr("hidden");
    $("#depositarBtn").removeAttr("disabled");;

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

  // // carga historial transacciones
   function cargaHistoryTransac() {
     //document.getElementById("historyTransac").value += transac;
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

  // cuando ingresa por primera vez monto esta vacio ''
  // esto significa que se debe actualizar el saldo por primera vez
  if ($('#amount').val() === '') {
    updateWallet();
  }

  // depositar dinero
  $('#depositarBtn').click(function () {
    let deposito = parseInt($('#amount').val());
    if (!isNaN(deposito) && deposito > 0) {
      balance += deposito;
      // datos para registro de transaccion
      tipo = 'Depósito';
      monto = deposito;
      destinatario = user;
      generaTransaccion()
      swal("Operación Exitosa!", "Depósito realizado", "success");
    } else {
      swal("Operación Fallida!", "El monto no es válido", "warning");
    }
  });


  function generaTransaccion() {
    // primero crea la transaccion
    saveTransac();
    // registra la trasaccion en la billetera
    updateWallet();
    $('#amount').val('');
  }


  // crea la transacciones para la billetera y el historial
  function saveTransac() {
    let cifraMonto = monto.toLocaleString("es-ES");
    let cifraBalance = balance.toLocaleString("es-ES");
    movimientoCuenta = fecha + ',' + tipo + ',' + cifraMonto + ',' + cifraBalance + ',' + destinatario;
    transac.push(movimientoCuenta);
    creaRegistro(fecha, tipo, cifraMonto, cifraBalance, destinatario);
  }

  // actualiza saldo en la billetera con la trasacciones creada
  function updateWallet() {
    let cifraMonto = balance.toLocaleString("es-ES");
    $('#balance').text(cifraMonto);
    //$('#balance').text(balance.toFixed(0));
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


