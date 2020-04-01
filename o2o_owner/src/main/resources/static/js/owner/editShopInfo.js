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
                    $("#shopCategory").html("<span>"+shopData.shopCategory.shopCategoryName+"</span>");
				}
			}
		});
	}

    getShopInfo();









});
