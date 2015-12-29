<%-- 
    Document   : COURSE viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty kurs}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">kurs: szczegóły</th>
            </tr>
            <tr>
                <th>Id: </th>
                <td>${kurs.id}</td>
            </tr>
            <tr>
                <th>Język: </th>
                <td>${kurs.jezyk.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol: </th>
                <td>${kurs.symbol}</td>
            </tr>
            <tr>
                <th>Opis: </th>
                <td>${kurs.opis}</td>
            </tr>
            <tr>
                <th>Rok: </th>
                <td>${kurs.rok}</td>
            </tr>
            <tr>
                <th>Semestr: </th>
                <td>${kurs.semestr}</td>
            </tr>
            <tr>
                <th>Sala: </th>
                <td>${kurs.sala}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujKurs?${kurs.id}">
            <span class="smallButtonText">edytuj informacje dotyczące kursu &#x279f;</span>
        </a>

        <hr />

        <!--        course set client-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">Firma</th>
            </tr>

            <c:if test="${not empty kurs.firma}">
                <tr>
                    <td>
                        kurs prowadzony w: ${kurs.firma.nazwa}
                    </td>
                    <td>
                        <a class="tinyButton" href="usunFirmeZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.firma}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajFirmeDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

        <hr />

        <!--        course set lector-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">Kurs prowadzi</th>
            </tr>

            <c:if test="${not empty kurs.lektor}">
                <tr>
                    <td>
                        lektor: ${kurs.lektor.nazwa}
                    </td>
                    <td>
                        <a class="tinyButton" href="usunLektoraZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.lektor}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajLektoraDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

        <hr />

        <!--        course set program-->

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">Program kursu</th>
            </tr>

            <c:if test="${not empty kurs.program}">
                <tr>
                    <td>
                        metoda: ${kurs.program.metoda}
                    </td>
                    <td rowspan="2">
                        <a class="tinyButton" href="usunProgramZKursu?${kurs.id}">
                            <span class="tinyButtonText">usuń &#x279f;</span>
                        </a>
                    </td>
                </tr>
                <tr>
                    <td>
                        referencja: ${kurs.program.referencja}
                    </td>
                </tr>
            </c:if>
            <c:if test="${empty kurs.program}">
                <tr>
                    <td colspan="2">
                        <a class="tinyButton" href="dodajProgramDoKursu?kursId=${kurs.id}">
                            <span class="tinyButtonText">dodaj &#x279f;</span>
                        </a>
                    </td>
                </tr>
            </c:if>
        </table>

    </section>
</c:if>
