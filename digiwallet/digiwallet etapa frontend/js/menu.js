// wallet digital
// utilizando localstorage para su almacenamiento
// no considera almacenamiento de usuario fuera del admin

$(document).ready(function () {

  // consulta autorización
  if(!sessionStorage.getItem('auth')){
    window.location.href = '../codigo/login.html';
  }else{
    let wallet = localStorage.getItem('wallet');
    if (wallet != null){
      // recupera datos del usuario
      let usuario = localStorage.getItem(JSON.parse(wallet).user);
      // lee los nombre pero toma el primero
      let nombre = JSON.parse(usuario).nombres.split(' ')[0];
      // transforma a mayústula el primer caracter y a minusculas los demás
      nombre = nombre.charAt(0).toUpperCase() + nombre.slice(1).toLowerCase();
      // lee los apellidos pero toma el primero
      let apellido = JSON.parse(usuario).apellidos.split(' ')[0];
      // transforma a mayústula el primer caracter y a minusculas los demás
      apellido = apellido.charAt(0).toUpperCase() + apellido.slice(1).toLowerCase();
      let bienvenida = nombre + ' '+ apellido;
      let saldo = JSON.parse(wallet).saldo.toLocaleString("es-ES");
      let fecha = new Date().toLocaleString().split(',')[0];
      $('#bienvenida').text(`Bienvenido(a) ${bienvenida}`);
      $('#cuenta').text(`Hoy ${fecha} tu saldo disponible es $${saldo}`);
    }
  }
  // logout
  $('#logoutBtn').click(function () {
    $('#username').val('');
    $('#password').val('');
    sessionStorage.setItem('auth', false);
    window.location.href = '../codigo/login.html';
  });

});


