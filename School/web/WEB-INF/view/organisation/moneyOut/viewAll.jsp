<%-- 
    Document   : MONEYOUT viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="cancelSaveTable">
        <tr>
            <td>
                <a href="dodajWyplate" class="smallButton"><span class="smallButtonText">Dodaj nową wypłatę</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="wyplaty?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="wyplaty?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="wyplaty?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="wyplaty?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="wyplaty?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty mainEntityList}">

        <table class="listTable">

            <tr>
                <th colspan="4">wypłaty</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="wyplaty?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="wyplaty?sortBy=data&changeSort=true&sortAsc=${sortAsc}">&#x25B2; data &#x25BC;</a></td>
                <td><a href="wyplaty?sortBy=kwota&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kwota &#x25BC;</a></td>
                <td><a href="wyplaty?sortBy=lektor&changeSort=true&sortAsc=${sortAsc}">&#x25B2; lektor &#x25BC;</a></td>
            </tr>

            <c:forEach var="mainEntity" items="${mainEntityList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazWyplate?${mainEntity.id}'">

                    <td><a href="pokazWyplate?${mainEntity.id}">${mainEntity.id}</a></td>
                    <td><a href="pokazWyplate?${mainEntity.id}">${mainEntity.data}</a></td>
                    <td><a href="pokazWyplate?${mainEntity.id}">${mainEntity.kwota}</a></td>
                        <c:forEach var="lektor" items="${lektorList}">
                            <c:choose>
                                <c:when test="${lektor eq mainEntity.lektor}">
                                    <td><a href="pokazWyplate?${mainEntity.id}">${lektor.nazwa}</a></td>
                            </c:when>
                            </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>