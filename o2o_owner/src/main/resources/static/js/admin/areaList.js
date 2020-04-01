$(function () {

	function getAreaList() {
		var url="/myo2oproject/admin/getAreas";
		$.ajax({
			url:url,
			method:'GET',
			success:function (res) {
				console.log(res)
				handleList(res.areaList)

            }
		})

    }


	function handleUser(data) {
		$('#user-name').text(data.name);
	}


	function handleList(areaList) {
		var html = '';
        areaList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.areaId+'</td>'+'<td class="text-l">'+ item.areaName +'</td>'+'<td class="text-l">'+ item.areaDesc +'</td>'+'<td class="text-l">'+ item.priority +'</td>'+'<td class="text-l">'+ item.createTime +'</td>'+'<td class="text-l">'+item.lastEditTime +'</td>'
				+'</td><td class="f-14"><a  style="text-decoration:none">'+editArea(item)+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#areaList').html(html);
	}

	function editArea( e) {

			return '<a href="/myo2oproject/admin/editArea?areaId='+ e.areaId +'&areaName='+e.areaName+'&areaDesc='+e.areaDesc+'&priority='+e.priority+'">编辑</a>';

	}



	getAreaList();














});
