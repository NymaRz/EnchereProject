<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>

</main>

<c:forEach var="articleVendu" items="${ articlesVendus }">

			<p>${articleVendu.nomArticle }</p>

		</c:forEach> 


<%@ include file="/WEB-INF/fragments/footer.jspf" %>