// login
$(document).ready(function () {
  $('#login-form').submit(function (event) {
    event.preventDefault();
    let email = $('#email').val();
    let password = $('#password').val();
    // Verificar las credenciales almacenadas en localstore
    // simula consulta al backend por la credenciales almacenadas en BD
    let logged = consultaRegistros(email, password);
    //let logged = username === 'admin' && password === '12345';
    if (logged) {
      sessionStorage.setItem('auth', true);
      // Si las credenciales son válidas, redirigir a la pantalla de wallet
      cargaWallet(email);
      window.location.href = '../codigo/menu.html';
    } else {
      // Si las credenciales son inválidas, mostrar mensaje de error
      sessionStorage.setItem('auth', false);
      swal("Usuario o contraseña invalido", "reintente nuevamente.!", "error");
    }
  });


  const consultaRegistros = (email, password)=>{
    let registrado = localStorage.getItem(email);
    if(registrado){
      return password = JSON.parse(registrado).password;
    }
    return false;
  }

  // inicializa wallet si no existe
  function cargaWallet(email) {
    if (!localStorage.getItem('wallet')) {
      const myWallet = {
        user: email,
        saldo: 0,
        trans: [],
        destinatarios: []
      };      
      // almacena la billetera
      localStorage.setItem('wallet', JSON.stringify(myWallet));
    }
  };
});

