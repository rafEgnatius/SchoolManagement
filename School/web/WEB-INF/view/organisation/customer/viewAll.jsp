<%-- 
    Document   : CUSTOMER viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <a href="dodajFirme" class="smallButton"><span class="smallButtonText">Dodaj nową firmę (klienta)</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="firmy?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="firmy?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="firmy?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="firmy?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="firmy?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty firmaList}">

        <table class="listTable">

            <tr>
                <th colspan="5">firmy</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="firmy?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="firmy?sortBy=nazwa&changeSort=true&sortAsc=${sortAsc}">&#x25B2; firma &#x25BC;</a></td>
                <td><a href="firmy?sortBy=symbol&changeSort=true&sortAsc=${sortAsc}">&#x25B2; symbol &#x25BC;</a></td>
                <td><a href="firmy?sortBy=miasto&changeSort=true&sortAsc=${sortAsc}">&#x25B2; miasto &#x25BC;</a></td>
                <td><a href="#">nip</a></td>
            </tr>

            <c:forEach var="firma" items="${firmaList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazFirme?${firma.id}'">

                    <td><a href="pokazFirme?${firma.id}">${firma.id}</a></td>
                    <td><a href="pokazFirme?${firma.id}">${firma.nazwa}</a></td>
                    <td><a href="pokazFirme?${firma.id}">${firma.symbol}</a></td>
                    <td><a href="pokazFirme?${firma.id}">${firma.miasto}</a></td>
                    <td><a href="pokazFirme?${firma.id}">${firma.nip}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>