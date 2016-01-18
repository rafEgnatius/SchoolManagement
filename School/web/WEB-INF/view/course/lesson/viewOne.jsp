<%-- 
    Document   : LESSON viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty lekcja}">

    <section id="section-szkola-one-column">

        <h2>zarządzanie lekcją</h2>

        <table class="viewAllTable">
            <tr>
                <td>
                    <a href="pokazKurs?${kurs.id}" class="smallButton"><span class="smallButtonText">&#x21A9; wróć do kursu</span></a>
                </td>
                <td>
                    <a href="lekcje?kursId=${kurs.id}" class="smallButton"><span class="smallButtonText">&#x21A9; lista lekcji</span></a>
                </td>
            </tr>
        </table>

        <div class="inlineTableDisplay">
            <table class="detailedTable">
                <tr class="tableHeading">
                    <th colspan="2">lekcja: szczegóły</th>
                </tr>
                <tr>
                    <th>Id:</th>
                    <td>${lekcja.id}</td>
                </tr>
                <tr>
                    <th>Data:</th>
                    <td>${lekcja.data}</td>
                </tr>
                <tr>
                    <th>Czy została odwołana:</th>
                    <td>${lekcja.odwolana ? "tak" : "nie"}</td>
                </tr>
                <tr>
                    <th>Kurs:</th>
                    <td><a class="email" href="pokazKurs?${kurs.id}">${kurs.symbol}</a></td>
                </tr>
            </table>
            <a  class="smallButton" href="edytujLekcje?${lekcja.id}">
                <span class="smallButtonText">edytuj informacje dotyczące tej lekcji &#x279f;</span>
            </a>
        </div>

        <div class="inlineTableDisplay">
            <form action="dodajObecnosc" method="POST">
                <table class="detailedTable">
                    <tr class="tableHeading">
                        <th colspan="2">lista obecności</th>
                    </tr>
                    <tr class="tableSubHeading">
                        <th>kursant</th>
                        <th>obecny</th>
                    </tr>
                    <c:forEach var="obecnosc" items="${obecnoscList}">
                        <tr>
                            <td>
                                ${obecnosc.kursant.nazwa}
                            </td>
                            <td class="cellsInCentre">
                                <input type="radio" name="${obecnosc.kursant.id}" value="true" ${obecnosc.obecny ? "checked" : ""} />tak
                                <input type="radio" name="${obecnosc.kursant.id}" value="false" ${obecnosc.obecny ? "" : "checked"}/>nie
                            </td>
                        </tr>
                    </c:forEach>
                    <tr>
                        <td></td>
                        <td class="cellsOnRight">
                            <input type="submit" value="Zapisz" />
                        </td>
                    </tr>
                </table>
                <input type="hidden" name="lekcjaId" value="${lekcja.id}" />
            </form>
        </div>





    </section>
</c:if>