$(function () {

	function getShopInfo() {
		$.ajax({
			url : "/myo2oproject/shop/getShopDetail",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {

                    console.log(data.shop);
                    var shopData=data.shop;
                    $("#shopName").html("<span>"+shopData.shopName+"</span>");
                    $("#area").html("<span>"+shopData.area.areaName+"</span>");
                    $("#category").html("<span>"+shopData.shopCategory.shopCategoryName+"</span>");
                    $("#shopAddr").html("<span>"+shopData.shopAddr+"</span>");
                    $("#phone").html("<span>"+shopData.phone+"</span>");
                    $("#shopDesc").html("<span>"+shopData.shopDesc+"</span>");
                    $("#shop_thumbnail_show").attr("src","http://192.168.174.128"+shopData.shopImg);

				}
			}
		});
	}

    //getShopInfo();



    $("#editButton").click(
        function () {
        /*    $("#shopAddr").html("<input type='text' class='input-text' value='' placeholder='请输入地址'  name='shopAddr'>");
            $("#phone").html("<input type='text' class='input-text' value='' placeholder='请输入电话'  name='phone'>");
            $("#shopDesc").html("<input type='text' class='input-text' value='' placeholder='请描述店铺'  name='shopDesc'>");
            $("#thumbnail").html("<input type='file' name='thumbnail'>");
            $("#shopEditSwitch").html("<button id='submitButton' class='btn btn-primary radius' type='button' >&nbsp;&nbsp;提交&nbsp;&nbsp;</button>");*/
       $.ajax(
           {
           url : "/myo2oproject/shop/editPage",
           type : "GET"

           })
        }
    )





});
