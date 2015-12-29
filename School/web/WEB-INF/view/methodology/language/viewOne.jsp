<%-- 
    Document   : LANGUAGE viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty jezyk}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">język: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${jezyk.id}</td>
            </tr>
            <tr>
                <th>Język:</th>
                <td>${jezyk.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol:</th>
                <td>${jezyk.symbol}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujJezyk?${jezyk.id}">
            <span class="smallButtonText">edytuj dane dotyczące języka &#x279f;</span>
        </a>

    </section>
</c:if>