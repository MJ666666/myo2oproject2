$(function () {





	function handleList(records) {
		var html = '';
        records.map(function (item, index) {
			html += '<tr class="text-c">\n' +
                '\t\t\t\t\t<td><input type="checkbox" name="" value=""></td><td>'+item.userAwardId+'</td>'+'<td class="text-l" style="text-align: center">'+ item.awardName +'</td>'+'<td>'+changeTime(item.createTime)+'</td>'+'<td>'+item.userName+'</td>'+'<td>'+exchangeStatus(item.usedStatus)+'</td>'
				+ '</tr>';

		});
		$('#recordList').html(html);
	}


	function changeTime(date) {
	    var data=new Date(date)
        var text=data.getFullYear()+'-'+(data.getMonth()+1)+'-'+data.getDate()+'&nbsp;'+data.getHours()+':'+data.getMinutes();
	    return text;

    }

    function exchangeStatus(status) {
        if (status =='0') {
            return '未兑换';
        }
        if (status=='1') {
            return '已兑换'
        }
    }

	//getlist();
    var sendData={"currentPage":"1","pageSize":"5"}

    sendRequest(1,true);



                   //区分是不是第一次请求

    function sendRequest(changePage,isFirst) {

        $.ajax({
            type:"POST",
            url:"/myo2oproject/userAward/getCustomerAwardRecords",
            contentType:"application/json;charset=utf-8",
            data:JSON.stringify(sendData),
            dataType:"json",
            processData:false,
            success:function (data,status) {
                if (data.result.statusCode==1) {

                    console.log(data.records);
                    console.log(data.totalPages);
                    handleList(data.records);
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

