<%-- 
    Document   : PROGRAMME viewOne
    Author     : Rafa
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>


<%-- customerRecord is requested --%>
<c:if test="${!empty program}">

    <section id="section-szkola-one-column">

        <table class="detailedTable">
            <tr class="tableHeading">
                <th colspan="2">program: szczegóły</th>
            </tr>
            <tr>
                <th>Id:</th>
                <td>${program.id}</td>
            </tr>
            <tr>
                <th>Metoda:</th>
                <td>${program.metoda}</td>
            </tr>
            <tr>
                <th>Referencja:</th>
                <td>${program.referencja}</td>
            </tr>
        </table>

        <a  class="smallButton" href="edytujProgram?${program.id}">
            <span class="smallButtonText">edytuj ten program &#x279f;</span>
        </a>

    </section>
</c:if>