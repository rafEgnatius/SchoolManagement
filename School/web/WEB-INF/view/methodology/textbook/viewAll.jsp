<%-- 
    Document   : TEXTBOOK viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="cancelSaveTable">
        <tr>
            <td>
                <form action="podreczniki">
                    <select name="searchOption" >
                        <c:forEach var="jezyk" items="${jezykList}">
                            <option value="${jezyk.id}" ${jezyk.id eq searchOption ? "selected" : ""}>${jezyk.nazwa}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="searchPhrase" value="${searchPhrase}" /><input type="submit" value="Szukaj" />
                </form>
            </td>
            <td class="rightCell">
                <a href="dodajPodrecznik" class="smallButton"><span class="smallButtonText">Dodaj podręcznik</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="podreczniki?pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&laquo;</a></li>
                    <li><a href="podreczniki?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="podreczniki?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="podreczniki?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&rsaquo;</a></li>
                    <li><a href="podreczniki?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty podrecznikList}">

        <table class="listTable">

            <tr>
                <th colspan="5">podręczniki w bibliotece</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="podreczniki?sortBy=id&changeSort=true">&#x25B2; id &#x25BC;</a></td>
                <td><a href="podreczniki?sortBy=nazwa&changeSort=true">&#x25B2; tytuł &#x25BC;</a></td>
                <td><a href="podreczniki?sortBy=poziom&changeSort=true">&#x25B2; poziom &#x25BC;</a></td>
                <td>język</td>
                <td>wypożyczenie</td>
            </tr>

            <c:forEach var="podrecznik" items="${podrecznikList}" varStatus="iter">
                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazPodrecznik?${podrecznik.id}'">

                    <td><a href="pokazPodrecznik?${podrecznik.id}">${podrecznik.id}</a></td>
                    <td><a href="pokazPodrecznik?${podrecznik.id}">${podrecznik.nazwa}</a></td>
                    <td><a href="pokazPodrecznik?${podrecznik.id}">${podrecznik.poziom}</a></td>
                    <td>
                        <c:forEach var="jezyk" items="${jezykList}">
                            <c:choose>
                                <c:when test="${podrecznik.jezyk eq jezyk}">
                                    ${jezyk.symbol} (${jezyk.nazwa})
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                    <td>
                        <c:forEach var="wypozyczenie" items="${wypozyczenieList}">
                            <c:choose>
                                <c:when test="${wypozyczenie.podrecznik eq podrecznik}">
                                    ${wypozyczenie.lektor.nazwa}
                                </c:when>
                            </c:choose>
                        </c:forEach>
                    </td>
                </tr>
                
            </c:forEach>

        </table>

    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>