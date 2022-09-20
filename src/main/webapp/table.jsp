<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<table id="result_table" border="1" cellpadding="0" cellspacing="0" width="100%" class="results">
    <!-- <tr id="table_header"> -->
    <jsp:useBean id="check" class="com.dimka228.web2.beans.Raw" scope="session"/>
    <thead>
    <th >X</th>
    <th >Y</th>
    <th >R</th>
    <th >Hit fact</th>
    <th >Current time</th>
    <th >Script running time, sec</th>
    </thead>
    <tbody>

    <tr>
        <th><%=check.getX()%>
        </th>
        <th><%=check.getY()%>
        </th>
        <th><%=check.getR()%>
        </th>
        <th style='color:<%=(check.getHitFact() ? "lime" : "red")%>'>
            <%= check.getHitFact() ? "hit" : "miss" %>
        </th>
        <th><%=check.getCurrentTime()%>
        </th>
        <th><%=check.getExecutionTime()%>
        </th>
    </tr>
    </tbody>
</table>