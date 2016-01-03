<%-- 
    Document   : MONEYIN viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty mainEntity}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">wpłata: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${mainEntity.id}</td>
            </tr>
            <tr>
                <th>Firma: </th>
                <td>${firma.nazwa}</td>
            </tr>
            <tr>
                <th>Data: </th>
                <td>${mainEntity.data}</td>
            </tr>
            <tr>
                <th>Kwota: </th>
                <td>${mainEntity.kwota} PLN</td>
            </tr>
            <tr>
                <th>Opis: </th>
                <td>${mainEntity.opis}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujWplate?${mainEntity.id}">
            <span class="smallButtonText">edytuj dane wpłaty &#x279f;</span>
        </a>

    </section>
</c:if>