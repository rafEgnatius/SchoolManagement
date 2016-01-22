<%-- 
    Document   : QUESTIONNAIRE viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>



<%-- customerRecord is requested --%>


<section id="section-szkola-one-column">
    <c:if test="${!empty ankieta}">
        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">ankieta: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${ankieta.id}</td>
            </tr>
            <tr>
                <th>Data: </th>
                <td>${ankieta.data}</td>
            </tr>
            <tr>
                <th>Lektor: </th>
                <td>${ankieta.lektor.nazwa}</td>
            </tr>
            <tr>
                <th>Kursant: </th>
                <td>${ankieta.kursant.nazwa}</td>
            </tr>
            <tr class="tableHeading">
                <th colspan="2">ankieta: odpowiedzi</th>
            </tr>
            <tr>
                <th>Pytanie 1:</th>
                <td>${ankieta.pytanie1 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 2:</th>
                <td>${ankieta.pytanie2 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 3:</th>
                <td>${ankieta.pytanie3 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 4:</th>
                <td>${ankieta.pytanie4 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 5:</th>
                <td>${ankieta.pytanie5 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 6:</th>
                <td>${ankieta.pytanie6 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 7:</th>
                <td>${ankieta.pytanie7 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 8:</th>
                <td>${ankieta.pytanie8 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 9:</th>
                <td>${ankieta.pytanie9 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 10:</th>
                <td>${ankieta.pytanie10 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 11:</th>
                <td>${ankieta.pytanie11 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 12:</th>
                <td>${ankieta.pytanie12 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 13:</th>
                <td>${ankieta.pytanie13 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 14:</th>
                <td>${ankieta.pytanie14 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 15:</th>
                <td>${ankieta.pytanie15 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 16:</th>
                <td>${ankieta.pytanie16 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 17:</th>
                <td>${ankieta.pytanie17 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 18:</th>
                <td>${ankieta.pytanie18 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 19:</th>
                <td>${ankieta.pytanie19 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 20:</th>
                <td>${ankieta.pytanie20 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 21:</th>
                <td>${ankieta.pytanie21 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie 22:</th>
                <td>${ankieta.pytanie22 ? "tak" : "nie"}</td>
            </tr>
            <tr>
                <th>Pytanie opisowe:</th>
                <td>${ankieta.pytanieOpisowe23}</td>
            </tr>
        </table>
    </c:if>
</section>
