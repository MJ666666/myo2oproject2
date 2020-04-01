$(function () {

	function getShopInfo() {
		$.ajax({
			url : "/myo2oproject/product/getProduct",
			type : "GET",
			success : function(data) {
				if (data.result.statusCode==1) {

                    console.log(data.product);
                    var productData=data.product;
                    $("#productName").html("<span>"+productData.productName+"</span>");
                    $("#productCategory").html("<span>"+productData.productCategory.productCategoryName+"</span>");
                    $("#priority").html("<span>"+productData.priority+"</span>");
                    $("#normalPrice").html("<span>"+productData.normalPrice+"</span>");
                    $("#promotionPrice").html("<span>"+productData.promotionPrice+"</span>");
                    $("#point").html("<span>"+productData.point+"</span>");
                    $("#thumbnail").attr("src","http://192.168.174.128"+productData.imgAddr);
                    $("#imgBoxs").html('<a href="/myo2oproject/productImgList?productId='+productData.productId+'"><b>点击查看</b></a>');
                    $("#productDesc").html("<span>"+productData.productDesc+"</span>");

                }
			}
		});
	}

    getShopInfo();







});
