<%-- 
    Document   : LESSON viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <h2>lista lekcji</h2>

    <table class="viewAllTable">
        <tr>
            <td>
                <a href="pokazKurs?${kursId}" class="smallButton"><span class="smallButtonText">&#x21A9; wróć do kursu</span></a>
            </td>
            <td>
                <a  class="smallButton" href="dodajLekcje?${kursId}">
                    <span class="smallButtonText">dodaj lekcję &#x279f;</span>
                </a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="lekcje?kursId=${kursId}&pageNumber=1&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="lekcje?kursId=${kursId}&pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="lekcje?kursId=${kursId}&pageNumber=${i}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="lekcje?kursId=${kursId}&pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="lekcje?kursId=${kursId}&pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&searchPhrase=${searchPhrase}&searchOption=${searchOption}&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty lekcjaList}">

        <table class="listTable">

            <tr>
                <th colspan="4">lekcje</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="lekcje?kursId=${kursId}&sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="lekcje?kursId=${kursId}&sortBy=data&changeSort=true&sortAsc=${sortAsc}">&#x25B2; data &#x25BC;</a></td>
                <td>odwołana</td>
                <td><a href="lekcje?kursId=${kursId}&sortBy=kurs&changeSort=true&sortAsc=${sortAsc}">&#x25B2; kurs &#x25BC;</a></td>
            </tr>

            <c:forEach var="lekcja" items="${lekcjaList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazLekcje?${lekcja.id}'">

                    <td>
                        <a href="pokazLekcje?${lekcja.id}">${lekcja.id}</a>
                    </td>
                    <td>
                        <a href="pokazLekcje?${lekcja.id}">${lekcja.data}</a>
                    </td>
                    <td>
                        <a href="pokazLekcje?${lekcja.id}">${lekcja.odwolana ? "tak" : "nie"}</a>
                    </td>
                    <c:forEach var="kurs" items="${kursList}">
                        <c:if test="${lekcja.kurs eq kurs}">
                            <td>
                                <a href="pokazLekcje?${lekcja.id}">${kurs.symbol}</a>
                            </td>
                        </c:if>
                    </c:forEach>
                </tr>

            </c:forEach>

        </table>

    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>