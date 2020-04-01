$(function () {

	function getlist(e) {
		$.ajax({
			url : "/myo2oproject/shopAuth/getShopAuthList",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {

                    console.log(data.authList);
					handleList(data.authList);

					//handleUser(data.user);
				}
			}
		});
	}



	function handleList(authList) {
		var html = '';
        authList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td class="text-l">'+ item.name +'</td>'+'<td class="text-l">'+ item.title +'</td>'
				+'</td><td class="f-14">'+'<a href='+'/myo2oproject/shopAuth/getShopAuth?employeeId='+item.employeeId+' style="text-decoration:none">'+'编辑'+'</a>'+'&nbsp;&nbsp;<a href="" style="text-decoration:none">'+goDelete(item.employeeId)+'</a>'+'\n' ;
                '\t\t\t\t</tr>';

		});
		$('#authList').html(html);
	}




    function goDelete( id) {

           return '<a href="/myo2oproject/shopAuth/deleteShopAuth?employeeId='+ id +'">删除</a>';

    }





	   getlist();










});
