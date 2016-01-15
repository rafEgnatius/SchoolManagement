<%-- 
    Document   : INVOICE viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <a href="dodajFakture" class="smallButton"><span class="smallButtonText">Dodaj nową fakturę</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="faktury?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="faktury?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="faktury?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="faktury?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="faktury?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty mainEntityList}">

        <table class="listTable">

            <tr>
                <th colspan="5">faktury</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="faktury?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="faktury?sortBy=numer&changeSort=true&sortAsc=${sortAsc}">&#x25B2; numer &#x25BC;</a></td>
                <td><a href="faktury?sortBy=data&changeSort=true&sortAsc=${sortAsc}">&#x25B2; data &#x25BC;</a></td>
                <td><a href="faktury?sortBy=kwota&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kwota &#x25BC;</a></td>
                <td><a href="faktury?sortBy=firma&changeSort=true&sortAsc=${sortAsc}">&#x25B2; firma &#x25BC;</a></td>
            </tr>

            <c:forEach var="mainEntity" items="${mainEntityList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazFakture?${mainEntity.id}'">

                    <td><a href="pokazFakture?${mainEntity.id}">${mainEntity.id}</a></td>
                    <td><a href="pokazFakture?${mainEntity.id}">${mainEntity.numer}</a></td>
                    <td><a href="pokazFakture?${mainEntity.id}">${mainEntity.data}</a></td>
                    <td><a href="pokazFakture?${mainEntity.id}">${mainEntity.kwota}</a></td>
                        <c:forEach var="firma" items="${firmaList}">
                            <c:choose>
                                <c:when test="${firma eq mainEntity.firma}">
                                    <td><a href="pokazFakture?${mainEntity.id}">${firma.nazwa}</a></td>
                            </c:when>
                            </c:choose>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>