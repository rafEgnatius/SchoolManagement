<%-- 
    Document   : COURSE addParticipant
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="cancelSaveTable">
        <tr>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="dodajKursantaDoKursu?kursId=${kursId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="dodajKursantaDoKursu?kursId=${kursId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="dodajKursantaDoKursu?kursId=${kursId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="dodajKursantaDoKursu?kursId=${kursId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="dodajKursantaDoKursu?kursId=${kursId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty kursantList}">

        <table class="listTable">

            <tr>
                <th colspan="5">kursanci</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="dodajKursantaDoKursu?kursId=${kursId}&sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="dodajKursantaDoKursu?kursId=${kursId}&sortBy=nazwa&changeSort=true&sortAsc=${sortAsc}">&#x25B2; imię i nazwisko &#x25BC;</a></td>
                <td><a href="dodajKursantaDoKursu?kursId=${kursId}&sortBy=symbol&changeSort=true&sortAsc=${sortAsc}">&#x25B2; telefon &#x25BC;</a></td>
                <td><a href="dodajKursantaDoKursu?kursId=${kursId}&sortBy=miasto&changeSort=true&sortAsc=${sortAsc}">&#x25B2; email &#x25BC;</a></td>
                <td><a href="dodajKursantaDoKursu?kursId=${kursId}&sortBy=firma&changeSort=true&sortAsc=${sortAsc}">&#x25B2; firma &#x25BC;</a></td>
            </tr>

            <c:forEach var="kursant" items="${kursantList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}'">

                    <td><a href="zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}">${kursant.id}</a></td>
                    <td><a href="zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}">${kursant.nazwa}</a></td>
                    <td><a href="zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}">${kursant.telefon}</a></td>
                    <td><a href="zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}">${kursant.email}</a></td>
                        <c:forEach var="firma" items="${firmaList}">
                            <c:choose>
                                <c:when test="${kursant.firma eq firma}">
                                <td><a href="zapiszDodanieKursantaDoKursu?kursId=${kursId}&kursantId=${kursant.id}">${firma.nazwa}</a></td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>



