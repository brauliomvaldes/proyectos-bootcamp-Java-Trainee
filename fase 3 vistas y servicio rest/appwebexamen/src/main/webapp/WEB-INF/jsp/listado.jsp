<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
	<form action="atenciones" method="post">
		<div class="container-fluid">
			<div class="row">
				<h1>Listado de Atenciones Por Realizar</h1>
				<br/><br/>
				<div class="col-4">
					<h5>
						<label for="sucursales">Sucursal</label>
					</h5>
					<select class="form-select" name="sucursal" id="sucursales">
						<option value="0">Todas</option>
						<c:forEach items="${sucursales}" var="sucursal">
							<option value="${sucursal.id}">${sucursal.nombre}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<h5>
						<label for="tipos">Tipo Atención</label>
					</h5>
					<select class="form-select" name="tipo" id="tipos">
						<option value="0">Todas</option>
						<c:forEach items="${tipos}" var="tipo">
							<option value="${tipo.id}">${tipo.nombre}</option>
						</c:forEach>
					</select>
				</div>
				<div class="col-4">
					<input class="btn btn-primary" type="submit" value="Filtrar" />
				</div>
			</div>
		</div>
	</form>

	<div>
		<div>
			<br /> <br />

			<c:if test="${not empty filtrado}">
				<h4>
					<p>
						Filtrado por :
						<c:out value="${filtrado}" />
					</p>
				</h4>
			</c:if>


			<table class="table table-striped">
				<thead class="table-dark">
					<tr>
						<th>Sucursal</th>
						<th>Doctor</th>
						<th>Tipo de Atención</th>
						<th>Nombre Mascota</th>
						<th>Nombre Dueño</th>
						<th>Fecha Próxima Revisión</th>
						<th>Box</th>
					</tr>
				</thead>
				<tbody>

					<c:forEach items="${listado}" var="item">
						<tr>
							<td>${item.sucursalNombre}</td>
							<td>${item.medicoNombre}</td>
							<td>${item.tipoAtencionDescripcion}</td>
							<td>${item.mascotaNombre}</td>
							<td>${item.duenoNombre}</td>
							<td>${item.fechaRealizacion}</td>
							<td>${item.boxAtencion}</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<c:if test="${empty listado}">
				<tr>
					<td><strong>No hay atenciones que mostrar, seleccione
							opciones y realice el filtrado</strong></td>
				</tr>
			</c:if>
		</div>
	</div>




</body>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</html>