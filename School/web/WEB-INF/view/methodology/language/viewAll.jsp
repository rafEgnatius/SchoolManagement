<%-- 
    Document   : LANGUAGE viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <a href="dodajJezyk" class="smallButton"><span class="smallButtonText">Dodaj nowy język</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="jezyki?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="jezyki?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="jezyki?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="jezyki?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="jezyki?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty jezykList}">

        <table class="listTable">

            <tr>
                <th colspan="3">języki</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="jezyki?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="jezyki?sortBy=nazwa&changeSort=true&sortAsc=${sortAsc}">&#x25B2; język &#x25BC;</a></td>
                <td><a href="jezyki?sortBy=symbol&changeSort=true&sortAsc=${sortAsc}">&#x25B2; symbol &#x25BC;</a></td>
            </tr>

            <c:forEach var="jezyk" items="${jezykList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazJezyk?${jezyk.id}'">

                    <td><a href="pokazJezyk?${jezyk.id}">${jezyk.id}</a></td>
                    <td><a href="pokazJezyk?${jezyk.id}">${jezyk.nazwa}</a></td>
                    <td><a href="pokazJezyk?${jezyk.id}">${jezyk.symbol}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>