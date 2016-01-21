<%-- 
    Document   : LANGUAGE TEST add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajTestPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź informacje dotyczące testu: </th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="rodzaj">Rodzaj: </label></td>
                <td><input id="rodzaj" type="text" name="rodzaj" value="${rodzaj}" class="inputForm" /></td>
                <td class="formErrorAsterix">${rodzajError}</td>
            </tr>
            <tr>
                <td><label for="ocena">Ocena: </label></td>
                <td><input id="ocena" type="number" name="ocena" value="${ocena}" class="inputForm" min="0" max="100" step="1"/></td>
                <td class="formErrorAsterix">${ocenaError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
                <td></td>
            </tr>
        </table>
            <input type="hidden" name="id" value="${id}" />
            <input type="hidden" name="kursId" value="${kursId}" />
            <input type="hidden" name="kursantId" value="${kursantId}" />
            <!-- we use this one in case we are editing -->
    </form>

</section>