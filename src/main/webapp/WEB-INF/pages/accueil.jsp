<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/fragments/headerAccueilNONConnecte.jspf"%>
<main>
<<<<<<< HEAD
	<%@include file="/WEB-INF/fragments/recherche.jspf"%>
</main>
<%@ include file="/WEB-INF/fragments/footer.jspf"%>
=======
<%@include file="/WEB-INF/fragments/recherche.jspf"%>
<<<<<<< HEAD
<%List<ArticleVendu> articlesVendus = (List<ArticleVendu>)request.getAttribute("articlesVendus"); %>
=======

>>>>>>> b3f4c10537f59790727f2f60cf533dc2385399aa
</main>

<c:forEach var="articleVendu" items="${ articlesVendus }">

			<p>${articleVendu.nomArticle }</p>

		</c:forEach> 


<%@ include file="/WEB-INF/fragments/footer.jspf" %>
>>>>>>> b55c472b9e4a87a210ea4f856746b20bf49b08c1
