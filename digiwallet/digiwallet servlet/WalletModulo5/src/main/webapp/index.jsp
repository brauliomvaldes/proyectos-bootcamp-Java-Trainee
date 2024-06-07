<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
<link rel="stylesheet" href="css/style.css"/>
<title>login Wallet</title>
</head>
<body>
	<main>
		<header>
			<div>
				<h1>DigiWallet</h1>
				<div>Login</div>
			</div>
		</header>
		<section>
			<div class="container-login">
				<div class="row">
					<form id="login-form" method="post" action="UsuarioController">
						<input name="username" type="text" id="username"
							placeholder="username" class="form-control controls" required /> 
						<input name="password" type="password" id="password"
							placeholder="password" class="form-control controls" required />
						<button type="submit"
							class="form-control btn btn-primary controls">Ingresar</button>
					</form>
				</div>
			</div>
		</section>
	</main>
</body>
</html>