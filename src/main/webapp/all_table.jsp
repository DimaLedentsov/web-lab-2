<%@ page import="com.dimka228.web2.beans.Raw" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<table id="result_table"table class="results">
    <jsp:useBean id="table" class="com.dimka228.web2.beans.RawBean" scope="session"/>
    <thead>
    <th >X</th>
    <th >Y</th>
    <th >R</th>
    <th >Hit fact</th>
    <th >Current time</th>
    <th >Script running time, sec</th>
    </thead>

    <tbody>
    <%
        if (table != null) {
            for (Raw check : table.getRaws()) {
    %>
    <tr>
        <td class="x"><%=check.getX()%>
        </td>
        <td class="y"><%=check.getY()%>
        </td>
        <td class="r"><%=check.getR()%>
        </td>
        <td class="hit" style='color:<%=(check.getHitFact() ? "lime" : "red")%>'>
            <%= check.getHitFact() ? "hit" : "miss" %>
        </td>
        <td><%=check.getCurrentTime()%>
        </td>
        <td><%=check.getExecutionTime()%>
        </td>
    </tr>
    <%
            }
        }
    %>
    </tbody>
</table>