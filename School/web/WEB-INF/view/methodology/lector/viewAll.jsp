<%-- 
    Document   : LECTOR viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="viewAllTable">
        <tr>
            <td>
                <form action="lektorzy">
                    <select name="searchOption" >
                        <c:forEach var="jezyk" items="${jezykList}">
                            <option value="${jezyk.id}" ${jezyk.id eq searchOption ? "selected" : ""}>${jezyk.nazwa}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="searchPhrase" value="${searchPhrase}" /><input type="submit" value="Szukaj" />
                </form>
            </td>
            <td class="rightCell">
                <a href="dodajLektora" class="smallButton"><span class="smallButtonText">Dodaj nowego lektora</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="lektorzy?pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="lektorzy?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="lektorzy?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="lektorzy?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="lektorzy?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty lektorList}">

        <table class="listTable">

            <tr>
                <th colspan="6">wszyscy lektorzy</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="lektorzy?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="lektorzy?sortBy=nazwa&changeSort=true&sortAsc=${sortAsc}">&#x25B2; imię i nazwisko &#x25BC;</a></td>
                <td><a href="lektorzy?sortBy=miasto&changeSort=true&sortAsc=${sortAsc}">&#x25B2; miasto &#x25BC;</a></td>
                <td><a href="lektorzy?sortBy=telefon&changeSort=true&sortAsc=${sortAsc}">&#x25B2; telefon &#x25BC;</a></td>
                <td><a href="lektorzy?sortBy=email&changeSort=true&sortAsc=${sortAsc}">&#x25B2; email &#x25BC;</a></td>
                <td>język</td>
            </tr>

            <c:forEach var="lektor" items="${lektorList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazLektora?${lektor.id}'">

                    <td><a href="pokazLektora?${lektor.id}">${lektor.id}</a></td>
                    <td><a href="pokazLektora?${lektor.id}">${lektor.nazwa}</a></td>
                    <td><a href="pokazLektora?${lektor.id}">${lektor.miasto}</a></td>
                    <td><a href="pokazLektora?${lektor.id}">${lektor.telefon}</a></td>
                    <td><a href="pokazLektora?${lektor.id}">${lektor.email}</a></td>
                    <td>
                        <ul>
                            <c:forEach var="jezykLektora" items="${jezykLektoraList}">
                                <c:choose>
                                    <c:when test="${jezykLektora.lektor eq lektor}">
                                        <c:forEach var="jezyk" items="${jezykList}">
                                            <c:choose>
                                                <c:when test="${jezyk eq jezykLektora.jezyk}">
                                                    <li>${jezyk.symbol}${(jezykLektora.natywny) ? ", native" : ""}</li>
                                                    </c:when>
                                                </c:choose>
                                            </c:forEach>
                                        </c:when>
                                    </c:choose>
                                </c:forEach>
                        </ul>
                    </td>
                </tr>

            </c:forEach>

        </table>

    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>