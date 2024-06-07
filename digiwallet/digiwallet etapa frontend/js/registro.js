// registro de usuario
$(document).ready(function () {
  // envia al login por click boton login
  $('#loginBtn').click(function () {
    window.location.href = '../codigo/login.html';
  })
  // si existe registro no puede volver a registrarse
  if (localStorage.getItem('wallet')) {
    swal("Registro fallido", "Ya existe Cliente con Wallet, debe recuperar sus credenciales!", "error");
  }
  $('#terminosycondiciones').click(function () {
    swal("Términos y Condiciones", "Detalle de términos y condiciones ......., al registrarse acepta estos términos y condiciones!", "info");
  })
  // controla ocultar o no password
  $(document).on('change', 'input[type="checkbox"]', function (e) {
    if (!this.checked) {
      $('#password').prop('type', 'text');
      $('#password-repite').prop('type', 'text');
    } else {
      $('#password').prop('type', 'password');
      $('#password-repite').prop('type', 'password');
    }
  });
  // compara password 
  $(document).on('keyup', 'input[name="password"]', function (e) {
    if ($('#password-repite').val().trim() == $('#password').val().trim()) {
      $('#password').prop('style', 'color: blue;');
      $('#password-repite').prop('style', 'color: blue;');
    } else {
      $('#password').prop('style', 'color: red;');
      $('#password-repite').prop('style', 'color: red;');
    };
  });
  // compara password
  $(document).on('keyup', 'input[name="password-repite"]', function (e) {
    if ($('#password').val().trim() == $('#password-repite').val().trim()) {
      $('#password').prop('style', 'color: blue;');
      $('#password-repite').prop('style', 'color: blue;');
    } else {
      $('#password').prop('style', 'color: red;');
      $('#password-repite').prop('style', 'color: red;');
    };
  });
  // procesa formulario
  $('.form-registro').submit(function (event) {
    event.preventDefault();
    // si quiere volver a repetir el registro
    if (localStorage.getItem('wallet')) {
      swal("Registro fallido", "Ya existe Cliente con Wallet, debe recuperar sus credenciales!", "error");
    } else {
      let nombres = $('#nombres').val().trim();
      let apellidos = $('#apellidos').val().trim();

      let email = $('#email').val().trim();
      let password = $('#password').val().trim();
      let passwordrepite = $('#password-repite').val().trim();
      if (password != passwordrepite) {
        swal("Registro fallido", "Las contraseñas no coinciden, vuelva a intentarlo!", "error");
      } else {
        // pregunta si no existe una wallet
        usuario = {
          nombres: nombres,
          apellidos: apellidos,
          email: email,
          // simula registro de clave/password encriptado (tipo hash bcrypt) en BD 
          password: password
        };
        localStorage.setItem(email, JSON.stringify(usuario));
        // se crea la wallet para asociarla al usaurio creado
        const myWallet = {
          user: email,
          saldo: 0,
          trans: [],
          destinatarios: []
        };
        // almacena la billetera
        localStorage.setItem('wallet', JSON.stringify(myWallet));
        swal("Registro existoso", "Se registro correctamente el usuario!", "success");
      }
    }
    // limpia campos del formulario de registro
    $(this).closest('form').find("input").val("");
  });
});