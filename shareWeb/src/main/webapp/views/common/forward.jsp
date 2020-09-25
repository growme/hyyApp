<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html >
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript">
  var target = "${target}";
  if(target!='' && target!=null){
	  window.top.location.href=target;
  }
</script>
</head>
<body>
</body>
</html>