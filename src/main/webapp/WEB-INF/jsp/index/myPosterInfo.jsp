<%@ page language="java" contentType="text/html; charset=GBK" pageEncoding="GBK"%>
<!DOCTYPE HTML>
<html>
<head>
    <meta charset="utf-8" />
	<meta name="viewport" content="width=device-width,user-scalable=no,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0" />   
    <!--允许全屏-->
    <meta content="yes" name="apple-mobile-web-app-capable" />
    <meta content="yes" name="apple-touch-fullscreen" />    
    <meta name="data-spm" content="a215s" />
    <meta content="telephone=no,email=no" name="format-detection" />    
    <meta content="fullscreen=yes,preventMove=no" name="ML-Config" />
<script type="text/javascript" src="../assets/js/newqrcode.js"></script> 
<script type="text/javascript" src="../assets/jquery-3.4.1.min.js"></script>
<title>-推广海报-</title>
</head>
<body>
<div class="container">
         <div id="qrcode" style="display:block"></div>
		
</div>
</body>
<script type="text/javascript">
  $(document).ready(function(){
	  var qrCodeUrl='https://wap.fj.10086.cn/micp/tydg/qtorder/t?ud=908c839beaed476ca5cdd2c6c0f478d5TYGD';
	  if(qrCodeUrl != ''){
		   RQCode(qrCodeUrl);
		   addVisitShareLog('visit');
		  }
  });
  
  var qrcode;
  var content;
  var size = 200;

  function RQCode(rqurl){

    	        qrcode = new QRCode(document.getElementById("qrcode"), {
    	            width : size,
    	            height : size,
    	            border :5,
    				correctLevel:QRCode.CorrectLevel.H   //L
    	        });

    	        qrcode.makeCode(rqurl);
  }

</script>
</html>

