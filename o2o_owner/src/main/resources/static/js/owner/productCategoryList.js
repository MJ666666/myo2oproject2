$(function () {

	function getlist(e) {
		$.ajax({
			url : "/myo2oproject/productCategory/getList",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {

                    console.log(data.productCategoryList);
					handleList(data.productCategoryList);

					//handleUser(data.user);
				}
			}
		});
	}



	function handleList(shopList) {
		var html = '';
        shopList.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td id="cateId">'+item.productCategoryId+'</td>'+'<td class="text-l">'+ item.productCategoryName +'</td>'+'<td class="text-l">'+ item.priority +'</td>'
				+'</td><td class="f-14"><a href="/myo2oproject/html/owner/productCategoryList.html" style="text-decoration:none">'+goDelete(item.productCategoryId)+'</a>\n' ;
                '\t\t\t\t</tr>';

		});
		$('#productCategoryList').html(html);
	}




    function goDelete( id) {

           return '<a href="/myo2oproject/productCategory/deleteProductCategory?productCategoryId='+ id +'">删除</a>';

    }





	   getlist();










});
