<%-- 
    Document   : CUSTOMER viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty firma}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">firma: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${firma.id}</td>
            </tr>
            <tr>
                <th>Nazwa:</th>
                <td>${firma.nazwa}</td>
            </tr>
            <tr>
                <th>Symbol:</th>
                <td>${firma.symbol}</td>
            </tr>
            <tr>
                <th>Miasto:</th>
                <td>${firma.miasto}</td>
            </tr>
            <tr>
                <th>Adres:</th>
                <td>${firma.adres}</td>
            </tr>
            <tr>
                <th>Osoba do kontaktu:</th>
                <td><a class="email" href="mailto:${firma.email}">${firma.osoba}</a></td>
            </tr>
            <tr>
                <th>Telefon:</th>
                <td>${firma.telefon}</td>
            </tr>
            <tr>
                <th>Komórka:</th>
                <td>${firma.komorka}</td>
            </tr>
            <tr>
                <th>Email:</th>
                <td><a class="email" href="mailto:${firma.email}">${firma.email}</a></td>
            </tr>
            <tr>
                <th>NIP:</th>
                <td>${firma.nip}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujFirme?${firma.id}">
            <span class="smallButtonText">edytuj dane firmy (klienta) &#x279f;</span>
        </a>

    </section>
</c:if>