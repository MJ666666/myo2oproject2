$(function () {

	function getlist() {
		$.ajax({
			url : "/myo2oproject/adminCates/getAllCates",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {
                    console.log(data.cateList);
					handleList(data.cateList);

				}
			}
		});
	}

	/*
			<tr class="text-c">
					<td><input name="" type="checkbox" value=""></td>
					<td>001</td>
					<td>分类名称</td>
					<td><a href="javascript:;" onClick="picture_edit('图库编辑','picture-show.html','10001')"><img width="210" class="picture-thumb" src="temp/200x150.jpg"></a></td>
					<td class="text-l"><a class="maincolor" href="javascript:;" onClick="picture_edit('图库编辑','picture-show.html','10001')">现代简约 白色 餐厅</a></td>
					<td class="text-c">标签</td>
					<td>2014-6-11 11:11:42</td>
					<td class="td-status"><span class="label label-success radius">已发布</span></td>
					<td class="td-manage"><a style="text-decoration:none" onClick="picture_stop(this,'10001')" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a> <a style="text-decoration:none" class="ml-5" onClick="picture_edit('图库编辑','picture-add.html','10001')" href="javascript:;" title="编辑"><i class="Hui-iconfont">&#xe6df;</i></a> <a style="text-decoration:none" class="ml-5" onClick="picture_del(this,'10001')" href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a></td>
				</tr>
	*/

	function handleList(cateList) {
		var html = '';
		var imgServer='http://192.168.174.128';
        cateList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.shopCategoryId+'</td>'+'<td class="text-l" style="text-align: center">'+ item.shopCategoryName +'</td>'+'<td>'+'<img width="210" class="picture-thumb" src="'+imgServer+item.shopCategoryImg+'">'+'</td>'+'<td>'+item.shopCategoryDesc+'</td>'+'</td>'+'<td>'+item.priority+'</td>'+'</td>'+'<td>'+formateDate(item.createTime)+'</td>'+'</td>'+'<td>'+formateDate(item.lastEditTime)+'</td>'+'<td>'+item.parentId+'</td>'
				+'<td><a href="' +"/myo2oproject/adminCates/editShopCate?shopCategoryId=" +item.shopCategoryId+"&shopCategoryName="+item.shopCategoryName+"&priority="+item.priority+"&shopCategoryDesc="+item.shopCategoryDesc+"&parentId="+item.parentId+
				'" style="text-decoration:none">编辑&nbsp;&nbsp;</a>';
                '\t\t\t\t</tr>';

		});
		$('#cateList').html(html);
	}



	function formateDate(data) {
		var date=new Date();
		date.setTime(data);
        var formate=date.getFullYear()+"-" + (date.getMonth()+1) + "-" + date.getDate()+'&nbsp;'+date.getHours()+":"+date.getMinutes();
		return formate;

    }






    getlist();


});

