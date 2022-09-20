// Валидация данных
// https://monsterlessons.com/project/lessons/validaciya-formy-v-javascript  ---- вдохновлялся этим

var form= document.querySelector('.validate_form');
var validatedButton = document.querySelector('.validate_button');
var xOptions = document.querySelectorAll(".x");
var yCoordinate = document.querySelector(".y");
var rOptions = document.querySelectorAll(".R");
var table = [];

function isNumber(s){
  var n = parseFloat(s.replace(',','.'));
  return !isNaN(n) && isFinite(n);
}
function formatParams(params) {
    return "?" + Object
        .keys(params)
        .map(function (key) {
            return key + "=" + encodeURIComponent(params[key])
        })
        .join("&")
}

//функция для генерации ошибок
function generateTip(text, color) { 
    var tip = document.createElement('div');
    tip.className = 'tip';
    tip.style.color = color;
    tip.innerHTML = text;
    return tip;
}


//функция для очистки подсказок при повторной валидации 
function removeValidation() {
  var tips = form.querySelectorAll('.tip')      
  for (var i = 0; i < tips.length; i++) {
      tips[i].remove()
  }
}


//функция для проверки наличия выбранной radio кнопки
function checkSelection(radios) {
    for(var i=0; i<radios.length; i++){
      if(radios[i].checked) return true;
    }
    var error = generateTip('field is blank','red');
    radios[0].parentElement.insertBefore(error, radios[0]);
    return false;
}

// проверка значения в поле на попадание в заданный диапазон
function validateField(coordinate,min,max){
  if(coordinate.value){
      coordinate.value = coordinate.value.replace(',','.');
      if(coordinate.value<=min || coordinate.value>=max || !isNumber(coordinate.value)){
          var error = generateTip('Wrong number format','red')
          coordinate.parentElement.insertBefore(error, coordinate)              
          return false;
      }
      else{
          var correct = generateTip('Correct data','green');
          coordinate.parentElement.insertBefore(correct, coordinate)              
          return true;              
      }
  }
  var error = generateTip('field is blank','red');
  coordinate.parentElement.insertBefore(error, coordinate);
  return false; 
}


// фунция для повторной проверки, что поля заполнены верно, чтобы передать их php скрипту
function validateAll(){
  return checkSelection(xOptions)&&validateField(yCoordinate,-5,3) && checkSelection(rOptions);
}

function  redrawDataOnGraph(){
    redrawGraph();
    if(table.length>0){
        for(var i=0;i<table.length;i++){
            var point = table[i];
            printDotOnGraph(point.x, point.y, point.hit);
        }
    }
}

$('.x:checkbox').click(function () {
    $('.x:checkbox').not(this).prop('checked', false);
});
$('.r:checkbox').click(function () {
    if(this.checked) rValue = this.value;
    redrawDataOnGraph();
    $('.r:checkbox').not(this).prop('checked', false);
});

$(document).ready(function(){
    resize();
    table = [];
    $("#result_table>tbody tr").each(function (i, el) {
        var self = $(this);
        var x = self.find(".x").text().trim();
        var y = self.find(".y").text().trim();
        var r = self.find(".r").text().trim();
        var hit = self.find(".hit").text().trim() == "hit"?true:false;
        var result = `${x}, ${y}, ${r}, ${hit}`;
        console.log(result);
        var raw = {x:x, y:y, r:r, hit:hit};
        table[i]=raw;
    });
    if(table.length>0) rValue = table[table.length-1].r;
    redrawDataOnGraph(rValue);

});


$("#inpform").on("submit", function(event){
  event.preventDefault();

    console.log("Got data for check!" );
    console.log('y: ', yCoordinate.value);
    removeValidation();

    if(!validateAll()){
      console.log("post canceled")
      return
    }
    console.log("data sending...")
    console.log($(this).serialize());
    $.ajax({
      url: 'control',     method: "GET",
      data: $(this).serialize() + "&timezone=" + new Date().getTimezoneOffset(),
      dataType: "html",

      success: function(data){
        console.log(data);
        $(".validate_button").attr("disabled", false);
        //window.localStorage.setItem("table",data["table"]);
        window.location.replace("result_page.jsp");
        //$("#result_table>tbody").html(data);
      },
      error: function(error){
        console.log(error);
        $(".validate_button").attr("disabled", false);
      },
    });
});


$(".reset_button").on("click",function(e){
    e.preventDefault();
    const params = {'clear': true}
    window.location.replace("control" + formatParams(params));

})