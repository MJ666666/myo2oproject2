$(function () {

	function getlist(e) {
		$.ajax({
			url : "/myo2oproject/product/getList",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {
                    console.log(data.productList);
					handleList(data.productList);

				}
			}
		});
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
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.productId+'</td>'+'<td class="text-l" style="text-align: center">'+ item.productName +'</td>'+'<td>'+item.priority+'</td>'+'<td>'+shopStatus(item.enableStatus)+'</td>'
				+'<td><a href="/myo2oproject/editProductPage?productId='+item.productId+'" style="text-decoration:none">编辑&nbsp;&nbsp;</a>'+changeEnableStatus(item.enableStatus,item.productId)+'<a href="" style="text-decoration:none">'+goShop(item.enableStatus, item.productId)+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#productList').html(html);
	}

	function goShop(status, id) {
		if (status != 0 && status != -1) {
			return '<a href="/myo2oproject/productPage?productId='+ id+'">预览&nbsp;&nbsp;</a>';
		} else {
			return '';
		}
	}


	function shopStatus(status) {
		if (status == 0) {
			return '已下架';
		} else if (status == -1) {
			return '商品非法';
		} else {
			return '已上架';
		}
	}

    function changeEnableStatus(status, id) {
        if (status == 0 ) {
            return '<a href="/myo2oproject/product/changeStatus?enableStatus=1&productId='+ id +'">上架&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/product/changeStatus?enableStatus=0&productId='+ id +'">下架&nbsp;&nbsp;</a>';
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
            url:"/myo2oproject/product/getList",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(sendData),
            dataType:"json",
            processData:false,
            success:function (data,status) {
                if (data.result.statusCode==1) {

                    console.log(data.productList);
                    console.log(data.totalPages);
                    handleList(data.productList);
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

