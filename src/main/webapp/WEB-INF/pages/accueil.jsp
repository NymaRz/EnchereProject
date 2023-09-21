<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf" %>

<main>
<%@include file="/WEB-INF/fragments/recherche.jspf"%>

<<<<<<< HEAD
<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>

=======
>>>>>>> 2572952b3d71c004c29fec8b53f37e5a5edf2f50
</main>

<c:forEach var="articleVendu" items="${ articlesVendus }">

			<p>${articleVendu.nomArticle }</p>

		</c:forEach> 


<%@ include file="/WEB-INF/fragments/footer.jspf" %>