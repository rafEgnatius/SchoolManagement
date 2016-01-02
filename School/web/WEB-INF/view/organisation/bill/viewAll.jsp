<%-- 
    Document   : BILL viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="cancelSaveTable">
        <tr>
            <td>
                <a href="dodajRachunek" class="smallButton"><span class="smallButtonText">Dodaj nowy rachunek</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="rachunki?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="rachunki?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="rachunki?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="rachunki?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="rachunki?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty mainEntityList}">

        <table class="listTable">

            <tr>
                <th colspan="5">rachunki</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="rachunki?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="rachunki?sortBy=numer&changeSort=true&sortAsc=${sortAsc}">&#x25B2; numer &#x25BC;</a></td>
                <td><a href="rachunki?sortBy=data&changeSort=true&sortAsc=${sortAsc}">&#x25B2; data &#x25BC;</a></td>
                <td><a href="rachunki?sortBy=kwota&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kwota &#x25BC;</a></td>
                <td><a href="rachunki?sortBy=lektor&changeSort=true&sortAsc=${sortAsc}">&#x25B2; lektor &#x25BC;</a></td>
            </tr>

            <c:forEach var="mainEntity" items="${mainEntityList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazRachunek?${mainEntity.id}'">

                    <td><a href="pokazRachunek?${mainEntity.id}">${mainEntity.id}</a></td>
                    <td><a href="pokazRachunek?${mainEntity.id}">${mainEntity.numer}</a></td>
                    <td><a href="pokazRachunek?${mainEntity.id}">${mainEntity.data}</a></td>
                    <td><a href="pokazRachunek?${mainEntity.id}">${mainEntity.kwota}</a></td>
                        <c:forEach var="lektor" items="${lektorList}">
                            <c:choose>
                                <c:when test="${lektor eq mainEntity.lektor}">
                                    <td><a href="pokazRachunek?${mainEntity.id}">${lektor.nazwa}</a></td>
                            </c:when>
                            </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>