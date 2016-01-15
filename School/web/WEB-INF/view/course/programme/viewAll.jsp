<%-- 
    Document   : PROGRAMME viewAll
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">
    <table class="viewAllTable">
        <tr>
            <td>
                <a href="dodajProgram" class="smallButton"><span class="smallButtonText">Dodaj program</span></a>
            </td>
            <td class="cellWithPagination">
                <ul class="pagination">
                    <li><a href="programy?pageNumber=1&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&laquo;</a></li>
                    <li><a href="programy?pageNumber=${pageNumber<2 ? 1 : (pageNumber-1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&lsaquo;</a></li>
                        ${pageNumber-2>1 ? "<li><a href=\"#\">...</a></li>" : "" }
                        <c:forEach begin="${pageNumber-2>1 ? pageNumber-2 : 1}" end="${pageNumber+2<numberOfPages ? pageNumber+2 : numberOfPages}" var="i">
                        <li><a href="programy?pageNumber=${i}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}" class="${i eq pageNumber ? "active" : ""}">${i}</a></li>    
                        </c:forEach>
                        ${pageNumber+2>numberOfPages ? "" : "<li><a href=\"#\">...</a></li>" }
                    <li><a href="programy?pageNumber=${(pageNumber+1)>(numberOfPages) ? numberOfPages : (pageNumber+1)}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&rsaquo;</a></li>
                    <li><a href="programy?pageNumber=${numberOfPages}&sortBy=${sortBy}&changeSort=false&sortAsc=${sortAsc}">&raquo;</a></li>
                </ul>
            </td>
        </tr>
    </table>

    <c:if test="${!empty programList}">

        <table class="listTable">

            <tr>
                <th colspan="3">programy</th>
            </tr>

            <tr class="tableHeading">
                <td><a href="programy?sortBy=id&changeSort=true&sortAsc=${sortAsc}">&#x25B2; id &#x25BC;</a></td>
                <td><a href="programy?sortBy=metoda&changeSort=true&sortAsc=${sortAsc}">&#x25B2; metoda &#x25BC;</a></td>
                <td><a href="programy?sortBy=referencja&changeSort=true&sortAsc=${sortAsc}">&#x25B2; referencja &#x25BC;</a></td>
            </tr>

            <c:forEach var="program" items="${programList}" varStatus="iter">

                <tr class="${((iter.index % 2) == 1) ? 'lightBlue' : 'white'} tableRow"
                    onclick="document.location.href = 'pokazProgram?${program.id}'">

                    <td><a href="pokazProgram?${program.id}">${program.id}</a></td>
                    <td><a href="pokazProgram?${program.id}">${program.metoda}</a></td>
                    <td><a href="pokazProgram?${program.id}">${program.referencja}</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <!--                pagination goes here (if necessary)-->

</section>