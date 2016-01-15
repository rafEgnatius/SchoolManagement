<%-- 
    Document   : LESSON add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajLekcjePotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>wprowadź informacje dotyczące lekcji: </th>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="data">Data (rrrr-mm-dd): </label></td>
                <td><input type="date" name="data" value="${!empty data ? data : ""}" /></td>
                <td class="formErrorAsterix">${dataError}</td>
            </tr>
            <tr>
                <td><label for="ocena">Odwołana: </label></td>
                <td>
                    <input type="radio" name="ocena" value="false" ${odwolana ? "" : "checked"}>nie<br>
                    <input type="radio" name="ocena" value="true" ${odwolana ? "checked" : ""}>tak<br>
                </td>
                <td class="formErrorAsterix">${odwolanaError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
                <td></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" />
        <input type="hidden" name="kursId" value="${!empty kurs ? kurs.id : kursId}" />
        <!-- we use this one in case we are editing -->
    </form>
        
</section>