// $('.nav span').click(function(){
// 	$('.nav span').removeClass('nav_c');
// 	$(this).addClass('nav_c');
// 	var flag=$(this).attr('flag');
// 	console.log(flag);
// 	if (flag==1) {
// 		$('.review').css('display',"block");
// 		$('.mylist').css('display',"none");
// 	}else{
// 		$('.review').css('display',"none");
// 		$('.mylist').css('display',"block");
// 	}
// })
// console.log($('.review').width());
// console.log($('.review').width());
// var x=0.0005199999999980776/0.000560000000000116;
// console.log(x);

// function linedis(lnx1,lny1,lnx2,lny2,pix,piy){
// 	var dis;
// 	if (lnx2==lnx1) {
// 		dis=Math.abs(lnx2-pix);
// 	}else{
// 		var k=-((lny1-lny2)/(lnx1-lnx2))
// 		var b=(lny1*lnx2-lny2*lnx1)/(lnx2-lnx1);
// 		dis=Math.abs(k*pix+1*piy+b)/Math.sqrt(1+k*k);
// 	}
// 	return dis;
// }
// console.log(linedis(0,0,1,5,5,0));
// var p0={x:?,y:?},p1={x:?,y:?},p={x:?,y:?}
// var dis
// if(p1.x==p0.x)
// {
// 　　dis=Math.abs(p1.x-p.x);
// }
// else
// {
// 　　var k=-((p0.y-p1.y)/(p0.x-p1.x))
// 　　var b=(p0.y*p1.x-p1.y*p0.x)/(p1.x-p0.x)
// 　　dis=Math.abs(k*p.x+1*p.y+b)/Math.sqrt(1+k*k);
// }
// 
var arr = [1,3,5,7,9,11];
var sideA=Math.floor(arr.length/2);//向下取整
var arrCopy = arr.slice(0,sideA);
var arrCopy2 = arr.slice(sideA,arr.length);
console.log(arrCopy); 　　　　　　　　　　　 //[3, 5, 7, 9, 11]
console.log(arrCopy2); 　　　　　　　　　　　//[3, 5, 7]
console.log(arr);