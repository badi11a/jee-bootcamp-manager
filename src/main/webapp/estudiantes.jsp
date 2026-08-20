<%@ include file="/WEB-INF/jspf/header.jspf" %>
<h2 class="mb-4">Listado de Estudiantes</h2>
<c:if test="${not empty error}">
    <div class="alert alert-danger">${error}</div>
</c:if>
<a href="estudiantes?action=nuevo" class="btn btn-primary mb-3">Nuevo Alumno</a>
<table class="table table-striped">
    <thead>
        <tr>
            <th>ID</th>
            <th>RUT</th>
            <th>Nombre</th>
            <th>Email</th>
            <th>Acciones</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="e" items="${estudiantes}">
            <tr>
                <td>${e.id}</td>
                <td>${fn:escapeXml(e.rut)}</td>
                <td>${fn:escapeXml(e.nombre)}</td>
                <td>${fn:escapeXml(e.email)}</td>
                <td>
                    <a href="estudiantes?action=editar&id=${e.id}" class="btn btn-sm btn-warning">Editar</a>
                    <a href="estudiantes?action=eliminar&id=${e.id}" class="btn btn-sm btn-danger" onclick="return confirm('\u00BFEliminar estudiante?');">Eliminar</a>
                    <a href="inscripciones?estudianteId=${e.id}" class="btn btn-sm btn-info">Ver Inscripciones</a>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
