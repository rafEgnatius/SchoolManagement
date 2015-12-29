<%-- 
    Document   : INVOICE viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty mainEntity}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">język: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${mainEntity.id}</td>
            </tr>
            <tr>
                <th>Język:</th>
                <td>${mainEntity.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol:</th>
                <td>${mainEntity.symbol}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujFakture?${mainEntity.id}">
            <span class="smallButtonText">edytuj dane faktury &#x279f;</span>
        </a>

    </section>
</c:if>