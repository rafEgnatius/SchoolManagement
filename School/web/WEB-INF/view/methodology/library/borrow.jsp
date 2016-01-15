<%-- 
    Document   : borrow
    Created on : 14-Dec-2015, 19:40:22
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!--if both are empty means something went wrong-->
<c:if test="${! ( empty lektorId && empty podrecznikId ) }"> 

    <section id="section-szkola-one-column">

        <c:if test="${!empty podrecznikId}">
            <!--    if lector id not set show this part,
                we search for database record here-->
            <table class="viewAllTable">
                <tr>
                    <td>
                        <form action="wyszukajLektoraDoWypozyczenia" method="POST">
                            <input type="text" name="searchEntity" value="${searchEntity}" />
                            <input type="hidden" name="podrecznikId" value="${podrecznikId}" />
                            <input type="submit" value="Szukaj" />
                        </form>
                    </td>
                    <td class="cellWithPagination">
                        <ul class="pagination">
                            <li><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&laquo;</a></li>
                            <li><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&lsaquo;</a></li>
                                ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                                <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                                <li><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                                </c:forEach>
                                ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                            <li><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&rsaquo;</a></li>
                            <li><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&raquo;</a></li>
                        </ul>
                </tr>
            </table>

            <c:if test="${!empty lektorList}">
                <table class="listTable">

                    <tr>
                        <th colspan="3">wskaż lektora</th>
                    </tr>

                    <tr class="tableHeading">
                        <td><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=id&changeSort=true">&#x25B2; id &#x25BC;</a></td>
                        <td><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=nazwa&changeSort=true">&#x25B2; imię i nazwisko &#x25BC;</a></td>
                        <td><a href="dodajLektoraDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=miasto&changeSort=true">&#x25B2; miasto &#x25BC;</a></td>
                    </tr>

                    <c:forEach var="lektor" items="${lektorList}" varStatus="iter">

                        <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                            onclick="document.location.href = 'potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznikId}&lektorId=${lektor.id}'">

                            <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznikId}&lektorId=${lektor.id}">${lektor.id}</a></td>
                            <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznikId}&lektorId=${lektor.id}">${lektor.nazwa}</a></td>
                            <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznikId}&lektorId=${lektor.id}">${lektor.miasto}</a></td>
                        </tr>

                    </c:forEach>

                </table>
            </c:if>

        </c:if>

        <!--    if textbook id not set show this part,
            we search for database record here-->

        <c:if test="${!empty lektorId}">
            <!--    if lector id not set show this part,
                we search for database record here-->
            <table class="viewAllTable">
                <tr>
                    <td>
                        <form action="dodajPodrecznikDoWypozyczenia">
                            <input type="text" name="searchEntity" value="${searchEntity}" /><input type="submit" value="Szukaj" />
                        </form>
                    </td>
                    <td class="cellWithPagination">
                        <ul class="pagination">
                            <li><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&laquo;</a></li>
                            <li><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&lsaquo;</a></li>
                                ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                                <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                                <li><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                                </c:forEach>
                                ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                            <li><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&rsaquo;</a></li>
                            <li><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchEntity=${searchEntity}">&raquo;</a></li>
                        </ul>
                </tr>
            </table>

            <c:if test="${!empty podrecznikList}">
                <table class="listTable">

                    <tr>
                        <th colspan="3">wybierz podręcznik</th>
                    </tr>

                    <tr class="tableHeading">
                        <td><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=id&changeSort=true">&#x25B2; id &#x25BC;</a></td>
                        <td><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=nazwa&changeSort=true">&#x25B2; imię i nazwisko &#x25BC;</a></td>
                        <td><a href="dodajPodrecznikDoWypozyczenia?podrecznikId=${podrecznikId}&sortBy=miasto&changeSort=true">&#x25B2; miasto &#x25BC;</a></td>
                    </tr>

                    <c:forEach var="podrecznik" items="${podrecznikList}">

                        <c:set var="borrowed" value="false"></c:set>

                        <c:forEach var="wypozyczenie" items="${wypozyczenieList}">
                            <c:choose>
                                <c:when test="${wypozyczenie.podrecznik eq podrecznik}">
                                    <c:set var="borrowed" value="true"></c:set>
                                </c:when>
                            </c:choose>
                        </c:forEach>

                        <c:choose>
                            <c:when test="${borrowed eq false}">
                                <tr onclick="document.location.href = 'potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznik.id}&lektorId=${lektorId}'">
                                    <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznik.id}&lektorId=${lektorId}">${podrecznik.id}</a></td>
                                    <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznik.id}&lektorId=${lektorId}">${podrecznik.nazwa}</a></td>
                                    <td><a href="potwierdzWypozyczeniePodrecznika?podrecznikId=${podrecznik.id}&lektorId=${lektorId}">${podrecznik.poziom}</a></td>
                                </tr>
                            </c:when>
                        </c:choose>

                    </c:forEach>

                </table>
            </c:if>

        </c:if>


    </section>

</c:if>

