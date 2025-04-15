/**
 * 
 */

function calc(){
	
	//x,y요소 검색
	xValue = document.getElementById('x').value;
	
	yValue = document.getElementById('y').value;
	
	add = parseInt(xValue) + parseInt(yValue);
	sub = xValue - yValue;
	multi = xValue * yValue;
	divide = xValue / yValue;
	
	//출력내용
	str= `x:${xValue} y:${yValue} <br>
	   add : ${add}<br>
	   sub: ${sub}<br>
	   multi: ${multi.toLocaleString()}<br>
	   divide: ${divide.toFixed(2)}<br>
	`
	
	//출력
	document.getElementById('result').innerHTML=str;
	
}