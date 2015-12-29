<%-- 
    Document   : COURSE addLector
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--if both are empty means something went wrong-->

<section id="section-szkola-one-column">

    <!--    if lector id not set show this part,
        we search for database record here-->
    <table class="cancelSaveTable">
        <tr>
            <td>
                <form action="dodajLektoraDoKursu">
                    <select name="searchOption" >
                        <c:forEach var="jezyk" items="${jezykList}">
                            <option value="${jezyk.id}" ${jezyk.id eq searchOption ? "selected" : ""}>${jezyk.nazwa}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="searchPhrase" value="${searchPhrase}" />
                    <input type="hidden" name="kursId" value="${kursId}" />
                    <input type="submit" value="Szukaj" />
                </form>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="dodajLektoraDoKursu?kursId=${kursId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="dodajLektoraDoKursu?kursId=${kursId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="dodajLektoraDoKursu?kursId=${kursId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="dodajLektoraDoKursu?kursId=${kursId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="dodajLektoraDoKursu?kursId=${kursId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
        </tr>
    </table>

    <c:if test="${!empty lektorList}">
        <table class="listTable">

            <tr>
                <th colspan="4">wybierz lektora</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="dodajLektoraDoKursu?kursId=${kursId}&sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="dodajLektoraDoKursu?kursId=${kursId}&sortBy=nazwa&changeSort=true&sortAsc=${sortAsc}">&#x25B2; imię i nazwisko &#x25BC;</a></td>
                <td><a href="dodajLektoraDoKursu?kursId=${kursId}&sortBy=miasto&changeSort=true&sortAsc=${sortAsc}">&#x25B2; miasto &#x25BC;</a></td>
                <td>język</td>
            </tr>

            <c:forEach var="lektor" items="${lektorList}" varStatus="iter">
                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'zapiszDodanieLektoraDoKursu?lektorId=${lektor.id}&kursId=${kursId}'">
                    <td><a href="zapiszDodanieLektoraDoKursu?lektorId=${lektor.id}&kursId=${kursId}">${lektor.id}</a></td>
                    <td><a href="zapiszDodanieLektoraDoKursu?lektorId=${lektor.id}&kursId=${kursId}">${lektor.nazwa}</a></td>
                    <td><a href="zapiszDodanieLektoraDoKursu?lektorId=${lektor.id}&kursId=${kursId}">${lektor.miasto}</a></td>
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

</section>

