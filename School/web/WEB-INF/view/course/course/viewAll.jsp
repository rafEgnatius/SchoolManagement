<%-- 
    Document   : COURSE viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <form action="kursy">
                    <select name="searchOption" >
                        <c:forEach var="jezyk" items="${jezykList}">
                            <option value="${jezyk.id}" ${jezyk.id eq searchOption ? "selected" : ""}>${jezyk.nazwa}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="searchPhrase" value="${searchPhrase}" /><input type="submit" value="Szukaj" />
                </form>
            </td>
            <td>
                <a href="dodajKurs" class="smallButton"><span class="smallButtonText">Dodaj nowy kurs</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="kursy?pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="kursy?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="kursy?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="kursy?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="kursy?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty kursList}">

        <table class="listTable">

            <tr>
                <th colspan="5">kursy</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="kursy?sortBy=id&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="kursy?sortBy=symbol&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&#x25B2; symbol &#x25BC;</a></td>
                <td><a href="kursy?sortBy=firma&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&#x25B2; firma &#x25BC;</a></td>
                <td><a href="kursy?sortBy=lektor&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&#x25B2; lektor &#x25BC;</a></td>
                <td><a href="kursy?sortBy=jezyk&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&#x25B2; język &#x25BC;</a></td>
            </tr>

            <c:forEach var="kurs" items="${kursList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazKurs?${kurs.id}'">

                    <td><a href="pokazKurs?${kurs.id}">${kurs.id}</a></td>
                    <td><a href="pokazKurs?${kurs.id}">${kurs.symbol}</a></td>

                    <c:if test="${not empty kurs.firma}">
                        <c:forEach var="firma" items="${firmaList}">
                            <c:choose>
                                <c:when test="${firma eq kurs.firma}">
                                    <td>
                                        <a href="pokazKurs?${kurs.id}">${firma.nazwa}</a>
                                    </td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty kurs.firma}">
                        <td><a href="#">-</a></td>
                        </c:if>
                        <c:if test="${not empty kurs.lektor}">
                            <c:forEach var="lektor" items="${lektorList}">
                                <c:choose>
                                    <c:when test="${lektor eq kurs.lektor}">
                                    <td>
                                        <a href="pokazKurs?${kurs.id}">${lektor.nazwa}</a>
                                    </td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty kurs.lektor}">
                        <td><a href="#">-</a></td>
                    </c:if>

                    <c:forEach var="jezyk" items="${jezykList}">
                        <c:choose>
                            <c:when test="${jezyk eq kurs.jezyk}">
                                <td><a href="pokazKurs?${kurs.id}">${jezyk.nazwa}</a></td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>