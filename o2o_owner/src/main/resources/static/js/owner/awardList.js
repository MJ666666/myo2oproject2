$(function () {

	function getlist(e) {
		$.ajax({
			url : "/myo2oproject/award/getAwardList",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {
                    console.log(data.awardList);
					handleList(data.awardList);

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

	function handleList(awardList) {
		var html = '';
        awardList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.awardId+'</td>'+'<td class="text-l" style="text-align: center">'+ item.awardName +'</td>'+'<td>'+item.priority+'</td>'+'<td>'+awardStatus(item.enableStatus)+'</td>'
				+'<td><a href="/myo2oproject/award/editAwardPage?awardId='+item.awardId+'" style="text-decoration:none">编辑&nbsp;&nbsp;</a>'+changeEnableStatus(item.enableStatus,item.awardId)+'<a href="" style="text-decoration:none">'+goShop(item.enableStatus, item.awardId)+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#awardList').html(html);
	}

	function goShop(status, id) {
		if (status != 0 && status != -1) {
			return '<a href="/myo2oproject/award/getAwardInfo?awardId='+ id+'">预览&nbsp;&nbsp;</a>';
		} else {
			return '';
		}
	}


	function awardStatus(status) {
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
            return '<a href="/myo2oproject/award/changeStatus?enableStatus=1&awardId='+ id +'">上架&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/award/changeStatus?enableStatus=0&awardId='+ id +'">下架&nbsp;&nbsp;</a>';
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

    getlist();






});

