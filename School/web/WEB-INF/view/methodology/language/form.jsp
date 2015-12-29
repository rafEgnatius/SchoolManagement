<%-- 
    Document   : LANGUAGE add
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<section id="section-szkola-one-column">

    <form action="dodajJezykPotwierdz" method="POST">
        <table class="formTable">
            <tr>
                <th>podaj szczegóły nowego języka:</td>
                <td colspan="2" class="formError">${formError eq "formError" ? "Formularz zawiera błędy:" : ""}</td>
            </tr>
            <tr>
                <td><label for="nazwa">Język: </label></td>
                <td><input type="text" name="nazwa" value="${nazwa}" /></td>
                <td class="formErrorAsterix">${nazwaError}</td>
            </tr>
            <tr>
                <td><label for="symbol">Symbol: </label></td>
                <td><input type="text" name="symbol" value="${symbol}" /></td>
                <td class="formErrorAsterix">${symbolError}</td>
            </tr>
            <tr>
                <td colspan="2"><input type="submit" value="Zapisz" /></td>
            </tr>
        </table>
        <input type="hidden" name="id" value="${id}" />
        <!-- we use this one in case we are editing -->
    </form>

</section>