$(function () {

	function getlist() {
		$.ajax({
			url : "/myo2oproject/adminHeadLine/getHeadLines",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {
                    console.log(data.headLineList);
					handleList(data.headLineList);

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

	function handleList(headLineList) {
		var html = '';
		var imgServer='http://192.168.174.128';
        headLineList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.lineId+'</td>'+'<td class="text-l" style="text-align: center">'+ item.lineName +'</td>'+'<td>'+'<img width="210" class="picture-thumb" src="'+imgServer+item.lineImg+'">'+'</td>'+'<td>'+item.lineLink+'</td>'+'</td>'+'<td>'+item.priority+'</td>'+'</td>'+'<td>'+formateDate(item.createTime)+'</td>'+'</td>'+'<td>'+formateDate(item.lastEditTime)+'</td>'+'<td>'+lineStatus(item.enableStatus)+'</td>'
				+'<td><a href="' +"/myo2oproject/adminHeadLine/editHeadLine?lineId=" +item.lineId+"&lineName="+item.lineName+"&priority="+item.priority+"&lineLink="+item.lineLink+
				'" style="text-decoration:none">编辑&nbsp;&nbsp;</a>'+changeEnableStatus(item.enableStatus,item.lineId)+'<a href="/myo2oproject/adminHeadLine/deleteHeadLine?lineId='+item.lineId+'" style="text-decoration:none">'+'删除'+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#headLineList').html(html);
	}



	function formateDate(data) {
		var date=new Date();
		date.setTime(data);
        var formate=date.getFullYear()+"-" + (date.getMonth()+1) + "-" + date.getDate()+'&nbsp;'+date.getHours()+":"+date.getMinutes();
		return formate;

    }

	function lineStatus(status) {
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
            return '<a href="/myo2oproject/adminHeadLine/updateHeadLineStatus?enableStatus=1&lineId='+ id +'">上线&nbsp;&nbsp;</a>';
        }
        if (status == 1 ) {
            return '<a href="/myo2oproject/adminHeadLine/updateHeadLineStatus?enableStatus=0&lineId='+ id +'">下线&nbsp;&nbsp;</a>';
        }
    }




    getlist();


});

