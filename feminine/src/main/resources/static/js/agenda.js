var dataSelect = "";
var agendado =  $("#top").val();
var ob = []; 

 $(function () {
        $.ajax({
            dataType: "json",
            type: "GET",
            url: "/ag",
            success: function (dados) {
                $(dados).each(function (i) {
				  ob.push(dados[i]);
                });
			var cliente = [];
			for(var i = 0; i < ob.length; i++){
			if(ob[i].cliente == agendado){
			cliente.push(ob[i]);
			}
			}
			$( "#marcado" ).text(cliente[0].dia+" às "+cliente[0].horas+" .")
            }
        });
    });

$(function() {
	$( "#calendario" ).datepicker({
	 dateFormat: 'dd/mm/yy',
	 closeText:"Fechar",
     prevText:"&#x3C;Anterior",
     nextText:"Próximo&#x3E;",
     currentText:"Hoje",
     monthNames: ["Janeiro","Fevereiro","Março","Abril","Maio","Junho","Julho","Agosto","Setembro","Outubro","Novembro","Dezembro"],
     monthNamesShort:["Jan","Fev","Mar","Abr","Mai","Jun","Jul","Ago","Set","Out","Nov","Dez"],
	 dayNames:["Domingo","Segunda-feira","Terça-feira","Quarta-feira","Quinta-feira","Sexta-feira","Sábado"],
	 dayNamesShort:["Dom","Seg","Ter","Qua","Qui","Sex","Sáb"],
     dayNamesMin:["Dom","Seg","Ter","Qua","Qui","Sex","Sáb"],
     weekHeader:"Sm",
     firstDay:1,
	 showOn: "button",
	 buttonImage: "/imagens/calendar.png",
	 buttonImageOnly: true,
	 buttonText: "Selecione uma data",
	 minDate: new Date(),
	 maxDate: 60,
     beforeShowDay: function(date){
     return [date.getDay() > 0 || date.getDay() > 0,""]
	 },
	 onSelect: function() { 
	 dataSelect = $('#calendario');
	 dataSelect = dataSelect.val();
	
	$("#option1").prop('disabled',false);
	$("#option2").prop('disabled',false);
	$("#option3").prop('disabled',false);
	for(var i = 0; i < ob.length; i++){
		if(new String(dataSelect) == ob[i].dia){
			if(ob[i].horas == "9:00"){
  					$("#option1").prop('disabled',true);
			}
			if(ob[i].horas == "13:00"){
  					$("#option2").prop('disabled',true);
			}
			if(ob[i].horas == "15:30"){
  					$("#option3").prop('disabled',true);
			}
		}
	}
		
   }
 });
});

