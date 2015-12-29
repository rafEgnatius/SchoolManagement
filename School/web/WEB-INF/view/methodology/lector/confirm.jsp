<%-- 
    Document   : LECTOR confirm
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<section id="section-szkola-one-column">

    <table class="detailedTable">
        <tr class="tableHeading">
            <th colspan="2">${id eq "-1" ? "czy zapisać nowego lektora w bazie?" : "czy zatwierdzić zmienione dane?"}</th>
        </tr>
        <tr>
            <th>Nazwa: </th>
            <td>${param.nazwa}</td>
        </tr>
        <tr>
            <th>Miasto: </th>
            <td>${param.miasto}</td>
        </tr>
        <tr>
            <th>Telefon: </th>
            <td>${param.telefon}</td>
        </tr>
        <tr>
            <th>Email: </th>
            <td>${param.email}</td>
        </tr>
        <tr>
            <th>Umowa: </th>
            <td>${param.umowa}</td>
        </tr>
        <tr>
            <th>NIP/PESEL</th>
            <td>${param.nip}</td>
        </tr>
    </table>
    <table class="cancelSaveTable">
        <tr>
            <td class="leftCell">
                <a class="smallButton" href="${id eq "-1" ? "lektorzy" : "edytujLektora?"}${id eq "-1" ? "" : id}">
                    <span class="smallButtonText">&#x21A9; anuluj</span>
                </a>
            </td>
            <td class="rightCell">
                <a class="smallButton" href="dodajLektoraZapisz?id=${id}&nazwa=${param.nazwa}&miasto=${param.miasto}&telefon=${param.telefon}&email=${param.email}&umowa=${param.umowa}&nip=${param.nip}">
                    <span class="smallButtonText">zapisz &#x21AA;</span>
                </a>
            </td>
        </tr>
    </table>



</section>
