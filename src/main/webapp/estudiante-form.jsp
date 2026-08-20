<%@ include file="/WEB-INF/jspf/header.jspf" %>
<div class="row justify-content-center">
    <div class="col-md-6">
        <div class="card shadow-sm">
            <div class="card-body">
                <h3 class="mb-4">
                    <c:choose>
                        <c:when test="${estudiante != null && estudiante.id > 0}">Editar Estudiante</c:when>
                        <c:otherwise>Nuevo Estudiante</c:otherwise>
                    </c:choose>
                </h3>
<form action="estudiantes" method="post">
    <div class="mb-3">
        <label for="rut" class="form-label">RUT</label>
        <input type="text" class="form-control" id="rut" name="rut" value="${fn:escapeXml(estudiante.rut)}" required maxlength="12">
    </div>
    <div class="mb-3">
        <label for="nombre" class="form-label">Nombre</label>
        <input type="text" class="form-control" id="nombre" name="nombre" value="${fn:escapeXml(estudiante.nombre)}" required maxlength="50">
    </div>
    <div class="mb-3">
        <label for="email" class="form-label">Email</label>
        <input type="email" class="form-control" id="email" name="email" value="${fn:escapeXml(estudiante.email)}" required maxlength="50">
    </div>
    <div class="mb-3 form-check">
        <input type="checkbox" class="form-check-input" id="activo" name="activo" <c:if test="${estudiante.activo}">checked</c:if>>
        <label class="form-check-label" for="activo">Activo</label>
    </div>
    <button type="submit" class="btn btn-success">Guardar</button>
    <a href="estudiantes" class="btn btn-secondary">Cancelar</a>
</form>
            </div>
        </div>
    </div>
</div>
<%@ include file="/WEB-INF/jspf/footer.jspf" %>
