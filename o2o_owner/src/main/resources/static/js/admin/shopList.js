$(function () {



	/*
				<th width="40"><input name="" type="checkbox" value=""></th>
					<th width="30">商铺ID</th>
					<th width="30">主人ID</th>
					<th width="30">区域ID</th>
					<th width="30">商店类别ID</th>
					<th width="100">商店名</th>
					<th width="100">商店描述</th>
					<th width="120">商店地址</th>
					<th width="100">商店照片</th>
					<th width="100">商店电话</th>
					<th width="100">建议</th>
					<th width="30">优先度</th>
					<th width="100">创建时间</th>
					<th width="100">最后修改时间</th>
					<th width="40">状态</th>
					<th width="40">操作</th>
	*/

	function handleList(cateList) {
		var html = '';
		var imgServer='http://192.168.174.128';
        cateList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.shopId+'</td>'+'<td>'+item.ownerId+'</td>'+'<td>'+item.areaId+'</td>'+'<td>'+item.shopCategoryId+'</td>'
				+'<td class="text-l" style="text-align: center">'+ item.shopName +'</td>'+'<td class="text-l" style="text-align: center">'+ item.shopDesc +'</td>'+'<td class="text-l" style="text-align: center">'+ item.shopAddr +'</td>'+'<td>'+'<img width="210" class="picture-thumb" src="'+imgServer+item.shopImg+'">'+'</td>'+
				'<td>'+item.phone+'</td>'+'<td>'+item.advice+'</td>'+'<td>'+item.priority+'</td>'+'</td>'+'<td>'+formateDate(item.createTime)+'</td>'+'</td>'+'<td>'+formateDate(item.lastEditTime)+'</td>'+'<td>'+shopStatus(item.enableStatus)+'</td>'
				+'<td><a href="' +"/myo2oproject/adminShop/editShop?shopId=" +item.shopId+"&shopName="+item.shopName+"&priority="+item.priority+"&shopCategoryId"+item.shopCategoryId+"&advice="+item.advice+
				'" style="text-decoration:none">编辑&nbsp;&nbsp;</a>'+changeEnableStatus(item.enableStatus,item.shopId);
                '\t\t\t\t</tr>';

		});
		$('#cateList').html(html);
	}

    function shopStatus(status) {
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
            return '<a href="/myo2oproject/adminShop/updateShopStatus?enableStatus=1&shopId='+ id +'">上线&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminShop/updateShopStatus?enableStatus=0&shopId='+ id +'">下线&nbsp;&nbsp;</a>';
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
            url:"/myo2oproject/adminShop/getShopList",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(sendData),
            dataType:"json",
            processData:false,
            success:function (data,status) {
                console.log(data)
                if (data.result.statusCode==1) {

                    console.log(data.shopList);
                    console.log(data.totalPages);
                    handleList(data.shopList);
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

