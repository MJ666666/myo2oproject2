$(function(){
    var countList;
    function getCountList() {
        $.ajax({
            url:"/myo2oproject/useProduct/getStatistic",
            method:"GET",
            success:function (data) {
                 countList=data.countList;
                console.log(countList)
                generateChart();
            }
        })
    }


    getCountList();
    function generateChart() {
        var size=countList[0].length;
        var dataCates=new Array(size)
        for (var i = 0; i < size; i++) {
            dataCates[i]=countList[0][i].productName
        }
        console.log(dataCates)

        //获取日期
        var day1=new Date();
        var dataDates=new Array(7);
        for (var i = 0; i < 7; i++) {
            day1.setTime(day1.getTime()-24*60*60*1000)
            dataDates[i]=day1.getFullYear()+"-" + (day1.getMonth()+1) + "-" + day1.getDate();
        }

        console.log(dataDates)

        var dataBox=new Array(size);

            for(var j=0;j<size;j++){
                var productData=new Array(7)
                for (var i = 0; i < 7; i++) {
                    productData[i]=countList[i][j].count
                }
                dataBox[j]=productData;
            }
        console.log(dataBox)






        var dataSeries=new Array(size);
        for (var i = 0; i < size; i++) {
            var barData=new Object();
            barData.name=dataCates[i];
            barData.type='bar'
            barData.data=dataBox[i]
            dataSeries[i]=barData
        }






        //var app.title = '2000-2016年中国汽车销量及增长率';
        var chart=echarts.init(document.getElementById("main"));
        var option = {
            tooltip: {
                trigger: 'axis',
                axisPointer: {            // 坐标轴指示器，坐标轴触发有效
                    type: 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: dataCates
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis: [
                {
                    type: 'category',
                    data: dataDates
                }
            ],
            yAxis: [
                {
                    type: 'value'
                }
            ],
            series:dataSeries

        };
        chart.setOption(option);

    }

})


