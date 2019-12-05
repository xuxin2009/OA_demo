<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>管理员登录-OA后台管理系统-OA 1.0</title>
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <meta http-equiv="Cache-Control" content="no-siteapp" />
    <link rel="shortcut icon" href="./favicon.ico" type="image/x-icon" />
    <link rel="stylesheet" href="./static/css/font.css">
	<link rel="stylesheet" href="./static/css/weadmin.css">

    <script src="../lib/layui/layui.js" charset="utf-8"></script>

</head>
<body class="login-bg">
    
    <div class="login">
        <div class="message">WeAdmin 1.0-管理登录</div>
        <div id="darkbannerwrap"></div>
        
        <form method="post" class="layui-form" >
            <input name="username" placeholder="用户名"  type="text" lay-verify="required" class="layui-input" >
            <hr class="hr15">
            <input name="password" lay-verify="required" placeholder="密码"  type="password" class="layui-input">
            <hr class="hr15">
            <div class="layui-form-item">
                <#--<button class="layui-btn " style="width: 100%" lay-filter="login" lay-submit="">登录</button>-->
                <input class="layui-btn" value="登录" lay-submit lay-filter="login" style="width:100%;" type="submit">
            </div>
            <hr class="hr20" >
        </form>
    </div>

    <script type="text/javascript">

        	layui.extend({
				admin: '{/}./static/js/admin'
			});

            layui.use(['form','jquery','layer','admin'], function(){
              var form = layui.form,
                      $=layui.jquery,
                      layer=layui.layer,
              	    admin = layui.admin;


              //监听提交
              form.on('submit(login)', function(data)
              {
                  $.ajax({
                      url:'/loginView',
                      contentType: 'application/json;charset=UTF-8',
                      method:'post',
                      data:JSON.stringify(data.field),
                      dataType:'JSON',
                      success:function(res){
                          if(res.status == 0 )
                          {
                              layer.msg(res.msg);
                              window.location = "/index";
                          }else
                          {
                              layer.msg(res.msg)
                          }

                      }
                  });
                  return false;
              });
            return false;
            });
    </script>

    <!-- 底部结束 -->
</body>
</html>