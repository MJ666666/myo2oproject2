$(function () {



	function handleUser(data) {
		$('#user-name').text(data.name);
	}
	/*
			<tr class="text-c">
					<td><input type="checkbox" name="" value=""></td>
					<td>3</td>
					<td>3</td>
					<td class="text-l">&nbsp;&nbsp;├&nbsp;二级栏目</td>
					<td class="f-14"><a title="编辑" href="javascript:;" onclick="system_category_edit('栏目编辑','system-category-add.html','3','700','480')" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
						<a title="删除" href="javascript:;" onclick="system_category_del(this,'3')" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</tr>
	*/

	function handleList(shopList) {
		var html = '';
        shopList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.shopId+'</td>'+'<td class="text-l">'+ item.shopName +'</td>'+'<td>'+shopStatus(item.enableStatus)+'</td>'
				+'</td><td class="f-14"><a href="/myo2oproject/html/welcome.html" style="text-decoration:none">'+goShop(item.enableStatus, item.shopId)+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#shopList').html(html);
	}

	function goShop(status, id) {
		if (status != 0 && status != -1) {
			return '<a href="/myo2oproject/shopIndex?shopId='+ id +'">进入</a>';
		} else {
			return '';
		}
	}

	function shopStatus(status) {
		if (status == 0) {
			return '审核中';
		} else if (status == -1) {
			return '店铺非法';
		} else {
			return '审核通过';
		}
	}


	$('#log-out').click(function () {
		$.ajax({
			url : "/myo2o/shop/logout",
			type : "post",
			contentType : false,
			processData : false,
			cache : false,
			success : function(data) {
				if (data.success) {
					window.location.href = '/myo2o/shop/ownerlogin';
				}
			},
			error : function(data, error) {
				alert(error);
			}
		});
	});


	//getlist();
    var sendData={"currentPage":"1","pageSize":"5"}

    sendRequest(1,true);



                   //区分是不是第一次请求

    function sendRequest(changePage,isFirst) {

        $.ajax({
            type:"POST",
            url:"/myo2oproject/shop/getShopList",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(sendData),
            dataType:"json",
            processData:false,
            success:function (data,status) {
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
