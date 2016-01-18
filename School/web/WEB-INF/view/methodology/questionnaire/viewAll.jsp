<%-- 
    Document   : QUESTIONNAIRE viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <a href="dodajAnkiete" class="smallButton"><span class="smallButtonText">Dodaj nową ankietę</span></a>
            </td>
            <td>
                <form action="ankiety">
                    <input type="text" name="searchPhrase" value="${searchPhrase}" autofocus /><input type="submit" value="Szukaj" />
                </form>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="ankiety?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&laquo;</a></li>
                    <li><a href="ankiety?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="ankiety?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="ankiety?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&rsaquo;</a></li>
                    <li><a href="ankiety?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty ankietaList}">

        <table class="listTable">

            <tr>
                <th colspan="4">ankiety</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="ankiety?sortBy=id&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="ankiety?sortBy=data&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&#x25B2; data &#x25BC;</a></td>
                <td><a href="ankiety?sortBy=lektor&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&#x25B2; lektor &#x25BC;</a></td>
                <td><a href="ankiety?sortBy=kursant&changeSort=true&sortAsc=${sortAsc}&searchPhrase=${searchPhrase}">&#x25B2; kursant &#x25BC;</a></td>
            </tr>

            <c:forEach var="ankieta" items="${ankietaList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazAnkiete?${ankieta.id}'">

                    <td><a href="pokazAnkiete?${ankieta.id}">${ankieta.id}</a></td>
                    <td><a href="pokazAnkiete?${ankieta.id}">${ankieta.data}</a></td>
                        <c:forEach var="lektor" items="${lektorList}">
                            <c:choose>
                                <c:when test="${lektor eq ankieta.lektor}">
                                <td><a href="pokazAnkiete?${ankieta.id}">${lektor.nazwa}</a></td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:forEach var="kursant" items="${kursantList}">
                            <c:choose>
                                <c:when test="${kursant eq ankieta.kursant}">
                                <td><a href="pokazAnkiete?${ankieta.id}">${kursant.nazwa}</a></td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>