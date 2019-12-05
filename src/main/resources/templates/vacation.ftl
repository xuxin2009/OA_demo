<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>添加管理员-WeAdmin Frame型后台管理系统-WeAdmin 1.0</title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=0">
    <link rel="stylesheet" href="../../static/css/font.css">
    <link rel="stylesheet" href="../../static/css/weadmin.css">
    <!-- 让IE8/9支持媒体查询，从而兼容栅格 -->
    <!--[if lt IE 9]>
    <script src="https://cdn.staticfile.org/html5shiv/r29/html5.min.js"></script>
    <script src="https://cdn.staticfile.org/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>

<body>
<div class="weadmin-body">
    <form class="layui-form" >
        <div class="layui-form-item">
            <label for="vacationType" class="layui-form-label">
                <span class="we-red">*</span>请假类型
            </label>
            <div class="layui-input-inline">
                <select  id ="vacationType" name="vacationType" lay-filter="vacationType">
                    <option value="0">--请选择--</option>
                    <option value="1">带薪</option>
                    <option value="2">病假</option>
                    <option value="3">事假</option>
                </select>
            </div>
        </div>

        <div class="layui-form-item">
            <label for="beginDate" class="layui-form-label">
                <span class="we-red">*</span>开始时间
            </label>
            <div class="layui-input-inline">
                <input type="text" id="beginDate" name="beginDate" required="" lay-verify="beginDate"
                       autocomplete="off" class="layui-input">
            </div>

        </div>
        <div class="layui-form-item">
            <label for="endDate" class="layui-form-label">
                <span class="we-red">*</span>结束时间
            </label>
            <div class="layui-input-inline">
                <input type="text" id="endDate" name="endDate" required="" lay-verify="endDate"
                       autocomplete="off" class="layui-input">
            </div>

        </div>

        <div class="layui-form-item">
            <label for="reason" class="layui-form-label">
                <span class="we-red">*</span>请假原因
            </label>
            <div class="layui-input-inline">
                <textarea  placeholder="请输入内容" id="reason" name="reason" required="" lay-verify="reason"
                       autocomplete="off" class="layui-textarea"></textarea>
            </div>

        </div>

        <div class="layui-form-item">
            <label for="L_repass" class="layui-form-label"></label>
            <button  class="layui-btn layui-btn-radius" lay-filter="add" lay-submit="" >提交</button>
            <button  class="layui-btn layui-btn-radius" lay-filter="cancel" lay-cancel="">取消</button>
        </div>
    </form>
</div>

<div>
    <h2 style="background-color: #1E9FFF; width: 20%">我正在申请的假</h2>
    <table class="layui-table">
        <tr>
            <td>时间</td>
            <td>天数</td>
            <td>事由</td>
            <td>申请状态</td>
        </tr>

   <#-- <#assign json>${vacationData}</#assign>
        <#assign jsonData=json?eval/>
       <#list  jsonData.data as vacations>
            <tr>
                <td>${vacations.applyTime!"test"}</td>
                <td>${vacations.days!"test"}</td>
                <td>${vacations.reason!"test"}</td>
                <td>${vacations.applyStatus!"test"}</td>
            </tr>
       </#list>-->

      </table>

</div>


<script src="../../lib/layui/layui.js" charset="utf-8"></script>

<script type="text/javascript">
    layui.use('laydate', function(){
        var laydate = layui.laydate;

        //执行一个laydate实例
        laydate.render({
            elem: '#beginDate' //指定元素
        });

        laydate.render({
            elem: '#endDate' //指定元素
        });
    });


</script>


<script type="text/javascript">
    layui.extend({
        admin: '{/}../../static/js/admin'
    });

    layui.use(['form','layer','admin'], function() {
        var form = layui.form,
                admin = layui.admin,
                $=layui.$,
                element=layui.element,
                layer = layui.layer;
        form.render();


        //自定义验证规则
        form.verify({
            nikename: function(value){
                if(value.length < 5){
                    return '昵称至少得5个字符啊';
                }
            }
            ,pass: [/(.+){6,12}$/, '密码必须6到12位']
            ,repass: function(value){
                if($('#L_pass').val()!=$('#L_repass').val()){
                    return '两次密码不一致';
                }
            }
        });


        //监听提交
        form.on('submit(add)', function(data){

            $.ajax({
                url:'/vacationApply',
                contentType: 'application/json;charset=UTF-8',
                method:'post',
                data:JSON.stringify(data.field),
                dataType:'JSON',
                success:function(res){
                    if(res.status==0)
                    {
                        layer.msg(res.msg);
                       $("#vacationType").val(0);
                        $("#beginDate").val("");
                        $("#endDate").val("");
                        $("#reason").val("");

                    }
                }
            });

            return false;

         });
    });



</script>


</body>

</html>