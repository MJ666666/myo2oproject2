$(function () {


/*
		<th width="40"><input name="" type="checkbox" value=""></th>
					<th width="30">用户ID</th>
					<th width="30">用户名</th>
					<th width="30">用户生日</th>
					<th width="30">用户性别</th>
					<th width="100">用户电话</th>
					<th width="100">用户email</th>
					<th width="100">用户头像</th>
					<th width="30">用户权限</th>
					<th width="30">店主权限</th>
					<th width="30">管理员权限</th>
					<th width="100">创建时间</th>
					<th width="100">最后修改时间</th>
					<th width="40">状态</th>
					<th width="40">操作</th>
 */



	function handleList(personInfoList) {
		var html = '';
        personInfoList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.userId+'</td>'+'<td>'+item.name+'</td>'+'<td>'+item.birthday+'</td>'+'<td>'+item.gender+'</td>'
				+'<td class="text-l" style="text-align: center">'+ item.phone +'</td>'+'<td class="text-l" style="text-align: center">'+ item.email +'</td>'+'<td>'+'<img width="210" class="picture-thumb" src="'+item.profileImg+'">'+'</td>'+
				'<td>'+changeCustomerLimitStatus(item.customerFlag,item.userId)+'</td>'+'<td>'+changeOwnerLimitStatus(item.shopOwnerFlag,item.userId)+'</td>'+'<td>'+changeAdminLimitStatus(item.adminFlag,item.userId)+'</td>'+'</td>'+'<td>'+formateDate(item.createTime)+'</td>'+'</td>'+'<td>'+formateDate(item.lastEditTime)+'</td>'+'<td>'+accountStatus(item.enableStatus)+'</td>'
				+'<td>'+changeEnableStatus(item.enableStatus,item.userId)+'</td>';
                '\t\t\t\t</tr>';

		});
		$('#cateList').html(html);
	}
    function accountStatus(status) {
        if (status == 0) {
            return '已下线';
        } else if (status == -1) {
            return '头条违规';
        } else {
            return '已上线';
        }
    }



    function changeEnableStatus(status, id) {
        if (status == 0 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?enableStatus=1&userId='+ id +'">启用&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?enableStatus=0&userId='+ id +'">禁用&nbsp;&nbsp;</a>';
        }
    }

    function changeCustomerLimitStatus(status, id) {
        if (status == 0 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?customerFlag=1&userId='+ id +'">授权&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?customerFlag=0&userId='+ id +'">取消授权&nbsp;&nbsp;</a>';
        }
    }
    function changeOwnerLimitStatus(status, id) {
        if (status == 0 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?shopOwnerFlag=1&userId='+ id +'">授权&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?shopOwnerFlag=0&userId='+ id +'">取消授权&nbsp;&nbsp;</a>';
        }
    }
    function changeAdminLimitStatus(status, id) {
        if (status == 0 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?adminFlag=1&userId='+ id +'">授权&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminUser/changeAccountStatus?adminFlag=0&userId='+ id +'">取消授权&nbsp;&nbsp;</a>';
        }
    }



    function formateDate(data) {
		var date=new Date();
		date.setTime(data);
        var formate=date.getFullYear()+"-" + (date.getMonth()+1) + "-" + date.getDate()+'&nbsp;'+date.getHours()+":"+date.getMinutes();
		return formate;

    }




    var sendData={"currentPage":"1","pageSize":"5"}

    sendRequest(1,true);



    //区分是不是第一次请求

    function sendRequest(changePage,isFirst) {

        $.ajax({
            type:"POST",
            url:"/myo2oproject/adminUser/getAccountList",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(sendData),
            dataType:"json",
            processData:false,
            success:function (data,status) {
                console.log(data)
                if (data.result.statusCode==1) {

                    console.log(data.personInfoList);
                    console.log(data.totalPages);
                    handleList(data.personInfoList);
                    var recordHtml="共有数据：<strong>"+data.totalRecords+"</strong>条";
                    $("#totalDatas").html(recordHtml)
                    //handleUser(data.user);
                }
                if(isFirst){
                    $("#pagin").jqPaginator({      //配置好分页插件的相关参数
                        totalPages: data.totalPages,
                        visiblePages:10,
                        currentPage:changePage,
                        first:'<li class="page-item"><a class="page-link">首页</a></li>',
                        prev:'<li class="page-item"><a class="page-link">上一页</a></li>',
                        next:'<li class="page-item"><a class="page-link">下一页</a></li>',
                        last:'<li class="page-item"><a class="page-link">末页</a></li>',
                        page:'<li class="page-item"><a class="page-link">{{page}}</a></li>',
                        onPageChange:function (changePage) {        //实现ajax
                            sendData={"currentPage":changePage,"pageSize":5}
                            sendRequest(changePage,false)
                        }
                    })
                }
            },fail:function (res) {
                console.log("请求数据失败")
            }
        })
    }




});

