<%-- 
    Document   : LANGUAGE TEST viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="cancelSaveTable">
        <tr>
            <td>
                <form action="testy">
                    <select name="searchOption" >
                        <c:forEach var="firma" items="${firmaList}">
                            <option value="${firma.id}" ${firma.id eq searchOption ? "selected" : ""}>${firma.nazwa}</option>
                        </c:forEach>
                    </select>
                    <input type="text" name="searchPhrase" value="${searchPhrase}" />
                    <input type="hidden" name="kursId" value="${kursId}" />
                    <input type="hidden" name="kursantId" value="${kursantId}" />
                    <input type="submit" value="Szukaj" />
                </form>
            </td>
            <c:if test="${!empty kursId && !empty kursantId}">
                <td class="rightCell">
                <a href="dodajTest?kursId=${kursId}&kursantId=${kursantId}" class="smallButton"><span class="smallButtonText">Dodaj nowy test</span></a>
            </td>
            </c:if>
            
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="testy?kursId=${kursId}&kursantId=${kursantId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="testy?kursId=${kursId}&kursantId=${kursantId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="testy?kursId=${kursId}&kursantId=${kursantId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="testy?kursId=${kursId}&kursantId=${kursantId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="testy?kursId=${kursId}&kursantId=${kursantId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty testList}">

        <table class="listTable">

            <tr>
                <th colspan="5">wszystkie testy</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="testy?kursId=${kursId}&kursantId=${kursantId}&sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="testy?kursId=${kursId}&kursantId=${kursantId}&sortBy=rodzaj&changeSort=true&sortAsc=${sortAsc}">&#x25B2; rodzaj &#x25BC;</a></td>
                <td><a href="testy?kursId=${kursId}&kursantId=${kursantId}&sortBy=ocena&changeSort=true&sortAsc=${sortAsc}">&#x25B2; ocena &#x25BC;</a></td>
                <td><a href="testy?kursId=${kursId}&kursantId=${kursantId}&sortBy=kurs&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kurs &#x25BC;</a></td>
                <td><a href="testy?kursId=${kursId}&kursantId=${kursantId}&sortBy=kursant&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kursant &#x25BC;</a></td>
            </tr>

            <c:forEach var="test" items="${testList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow">

                    <td>
                        ${test.id}
                    </td>
                    <td>
                        <a href="#">${test.rodzaj}</a>
                    </td>
                    <td>
                        <a href="#">${test.ocena}</a>
                    </td>
                        <c:forEach var="kurs" items="${kursList}">
                            <c:choose>
                                <c:when test="${test.kurs eq kurs}">
                                <td onclick="document.location.href = 'pokazKurs?${kurs.id}'">
                                    <a href="pokazKurs?${kurs.id}">${kurs.symbol}</a>
                                </td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                        <c:forEach var="kursant" items="${kursantList}">
                            <c:choose>
                                <c:when test="${test.kursant eq kursant}">
                                <td onclick="document.location.href = 'pokazKursanta?${kursant.id}'">
                                    <a href="pokazKursanta?${kursant.id}">${kursant.nazwa}</a>
                                </td>
                                </c:when>
                            </c:choose>
                        </c:forEach>
                </tr>

            </c:forEach>

        </table>

    </c:if>

    <!--                pagination goes here (if necessary)-->

    
    kursId ${kursId}
    kursantId ${kursantId}
    
    
</section>