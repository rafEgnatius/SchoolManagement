<%-- 
    Document   : CUSTOMER confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nową firmę w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>Nazwa: </th>
            <td>${param.nazwa}</td>
        </tr>
        <tr>
            <th>Symbol: </th>
            <td>${param.symbol}</td>
        </tr>
        <tr>
            <th>Miasto:</th>
            <td>${param.miasto}</td>
        </tr>
        <tr>
            <th>Adres: </th>
            <td>${param.adres}</td>
        </tr>
        <tr>
            <th>Osoba do kontaktu: </th>
            <td>${param.osoba}</td>
        </tr>
        <tr>
            <th>Telefon:</th>
            <td>${param.telefon}</td>
        </tr>
        <tr>
            <th>Tel. komórkowy:</th>
            <td>${param.komorka}</td>
        </tr>
        <tr>
            <th>Email:</th>
            <td>${param.email}</td>
        </tr>
        <tr>
            <th>NIP</th>
            <td>${param.nip}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "firmy" : "edytujFirme?"}${id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajFirmeZapisz?id=${id}&nazwa=${param.nazwa}&symbol=${param.symbol}&miasto=${param.miasto}&adres=${param.adres}&osoba=${param.osoba}&telefon=${param.telefon}&komorka=${param.komorka}&email=${param.email}&nip=${param.nip}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
