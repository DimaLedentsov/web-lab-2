<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.Collection" %>
<%@ page import="com.dimka228.web2.beans.*" %>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/style.css">    <!-- подключение css -->

    <title>LAB-1-WEB</title>
</head>
<body>

<!-- таблица -->
<table id="content" border="1" cellpadding="0" cellspacing="0" width="100%">

    <!-- шапка -->
    <tr>
        <th class="hat" colspan="3"> Ledentsov Dmitriy Andreevich<br>Group P3108<br>Variant №6732 </th>
    </tr>

    <!-- названия столбцов -->
    <th >Schedule</th>
    <th >Coordinates</th>
    <th >Results</th>

    <!-- содержимое столбцов -->
    <tr>
        <td width="30%">
            <canvas class="areas" id="graph" alt="Фото графика"></canvas>
            <input id="resize_slider" type="range" min="-30" max="25" value="18" class="slider" id="myRange">
        </td>    <!-- график -->

        <td width="20%" valign="top">
            <form id="inpform" class="validate_form" method="post"> <!-- координаты -->

                <div class="input"><b>Enter Х:</b><br>
                    <div>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_-2" value="-4" > -4<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_-1.5" value="-3"> -3<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_-1" value="-2"> -2<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_-0.5" value="-1"> -1<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_0" value="0" checked> 0<br> <!-- выбран по дефолту-->
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_0.5" value="1"> 1<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_1" value="2"> 2<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_1.5" value="3"> 3<br>
                        <input  type="checkbox" name="x_coordinate" class="x" id="x_value_2" value="4"> 4<br>
                    </div>
                </div>

                <div class="input">
                    <div>
                        <b>Enter Y:</b>
                    </div>
                    <div>
                        <input autocomplete="off" maxlength="5" type="text" name="y_coordinate" class="y field" size="19" placeholder="From -5 to 3."  >
                    </div>
                </div>

                <div class="input">
                    <div>
                        <b>Enter R:</b>
                    </div>
                    <div>
                        <input  type="checkbox" name="r_coordinate" class="r" value="1" checked> 1<br>
                        <input  type="checkbox" name="r_coordinate" class="r" value="1.5"> 1.5<br>
                        <input  type="checkbox" name="r_coordinate" class="r" value="2"> 2<br>
                        <input  type="checkbox" name="r_coordinate" class="r" value="2.5"> 2.5<br>
                        <input  type="checkbox" name="r_coordinate" class="r" value="3"> 3<br>
                    </div>
                </div>

                <br><input type="submit" class="validate_button" value='Submit'/>
            </form>
            <input type="submit" class="reset_button" value='Reset table'/>
        </td>


        <!-- факт попадания|текущее время |время работы скрипта -->
        <td valign = "top">   <!-- результаты -->
            <div id="result_table_container">
                <jsp:include page="all_table.jsp" />
            </div>
        </td>
    </tr>

    <!-- низ -->
    <tr>
        <td class="boots" colspan="3">I T M O , September 2022</td>
    </tr>



</table>

<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script> <!-- подключение библиотеки JQuery для отправки POST запроса -->
<script src="js/client.js" ></script>  <!-- подключение js -->
<script src="js/graph.js" ></script>  <!-- подключение js -->
<script src="js/slider.js" ></script>

</body>
</html>