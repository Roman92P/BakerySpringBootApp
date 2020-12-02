document.addEventListener("DOMContentLoaded", function () {



    let soldSelect = document.getElementById('soldPeriod');

    soldSelect.addEventListener('change', function (){

        if(soldSelect.value==="cm"){

            document.getElementById("usersSoldHistoryLm").style.display='none';
            document.getElementById("usersSoldHistory").style.display='block';
            document.getElementById('beginDate').style.display='none';
            document.getElementById('endDate').style.display='none';
            document.getElementById('customBtn').style.display='none';
            document.getElementById('usersSoldHistoryCm').style.display='none'

            new Promise((resolve, reject) => {
                fetch('/dashboard/soldCurrentMonth').then(response => {
                    return response.json();
                }).then(data =>{
                    // console.log("Result form response: ", data)

                    var reduced = data.reduce(function(allDates, date) {
                        if (allDates.some(function(e) {
                            return e.date === date.date
                        })) {
                            allDates.filter(function(e) {
                                return e.date === date.date
                            })[0].sum += +date.sum
                        } else {
                            allDates.push({
                                date: date.date,
                                sum: +date.sum
                            })
                        }
                        return allDates
                    }, []);

                    // console.log(reduced)


                    var labels = reduced.map(function(e) {
                        return e.date;
                    });
                    var data = reduced.map(function(e) {
                        return e.sum;
                    });
                    Chart.defaults.global.defaultFontFamily='Lato';
                    Chart.defaults.global.defaultFontSize=18;
                    Chart.defaults.global.defaultFontColor='#777';
                    let userSoldHistoryChart = document.getElementById('usersSoldHistory').getContext('2d');


                    let barChart = new Chart(userSoldHistoryChart,{
                        type:'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                        data:{
                            labels: labels,
                            datasets:[{
                                label: 'Orders Value per Day',
                                data: data,
                                backgroundColor:'dark'
                            }]
                        },
                        options:{}
                    });

                })
            }).catch(error =>{
                console.log("Error during fetch: ",index);
            })
        }

        if(soldSelect.value==="pm"){

            document.getElementById("usersSoldHistoryLm").style.display='block';
            document.getElementById("usersSoldHistory").style.display='none';
            document.getElementById('beginDate').style.display='none';
            document.getElementById('endDate').style.display='none';
            document.getElementById('customBtn').style.display='none';
            document.getElementById('usersSoldHistoryCm').style.display='none'

            new Promise((resolve, reject) => {
                fetch('/dashboard/soldPreviousMonth').then(response => {
                    return response.json();
                }).then(resp =>{
                    // console.log("Result form response: ", resp)
                    var reduced = resp.reduce(function(allDates, date) {
                        if (allDates.some(function(e) {
                            return e.date === date.date
                        })) {
                            allDates.filter(function(e) {
                                return e.date === date.date
                            })[0].sum += +date.sum
                        } else {
                            allDates.push({
                                date: date.date,
                                sum: +date.sum
                            })
                        }
                        return allDates
                    }, []);

                    // console.log(reduced)


                    var labels = reduced.map(function(e) {
                        return e.date;
                    });
                    var data = reduced.map(function(e) {
                        return e.sum;
                    });
                    Chart.defaults.global.defaultFontFamily='Lato';
                    Chart.defaults.global.defaultFontSize=18;
                    Chart.defaults.global.defaultFontColor='#777';
                    let userSoldHistoryChartLm = document.getElementById('usersSoldHistoryLm').getContext('2d');


                    let barChart = new Chart(userSoldHistoryChartLm,{
                        type:'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                        data:{
                            labels: labels,
                            datasets:[{
                                label: 'Orders Value per Day last month',
                                data: data,
                                backgroundColor:'blue'
                            }]
                        },
                        options:{}
                    });

                })
            }).catch(error =>{
                console.log("Error during fetch: ",index);
            })
        }
        if(soldSelect.value==="cd"){
            document.getElementById("usersSoldHistoryLm").style.display='none';
            document.getElementById("usersSoldHistory").style.display='none';
            document.getElementById('beginDate').style.display='block';
            document.getElementById('endDate').style.display='block';
            document.getElementById('customBtn').style.display='block';

            let elementById = document.getElementById('beginDate');

            let elementById1 = document.getElementById('endDate');

            let elementById2 = document.getElementById('customBtn');

            let elementsArray = document.querySelectorAll('input[type="date"]');

            elementsArray.forEach(function(elem) {
                elem.addEventListener("input", function() {

                    elementById2.addEventListener('click', function (){

                    new Promise((resolve, reject) => {
                        fetch('/dashboard/customSoldDate/'+elementById.value+'/'+elementById1.value).then(response => {
                            return response.json();
                        }).then(answ =>{
                            // console.log("Result form response: ", data)

                            var reduced = answ.reduce(function(allDates, date) {
                                if (allDates.some(function(e) {
                                    return e.date === date.date
                                })) {
                                    allDates.filter(function(e) {
                                        return e.date === date.date
                                    })[0].sum += +date.sum
                                } else {
                                    allDates.push({
                                        date: date.date,
                                        sum: +date.sum
                                    })
                                }
                                return allDates
                            }, []);

                            // console.log(reduced)


                            var labels = reduced.map(function(e) {
                                return e.date;
                            });
                            var data = reduced.map(function(e) {
                                return e.sum;
                            });
                            Chart.defaults.global.defaultFontFamily='Lato';
                            Chart.defaults.global.defaultFontSize=18;
                            Chart.defaults.global.defaultFontColor='#777';
                            let userSoldHistoryChartCm = document.getElementById('usersSoldHistoryCm').getContext('2d');


                            let barChart = new Chart(userSoldHistoryChartCm,{
                                type:'bar',// bar, horizontalBar, pie, line, doughtnut, radar, polarArea
                                data:{
                                    labels: labels,
                                    datasets:[{
                                        label: 'Orders Value per Day',
                                        data: data,
                                        backgroundColor:'dark'
                                    }]
                                },
                                options:{}
                            });

                        })
                    }).catch(error =>{
                        console.log("Error during fetch: ",index);
                    })
                    })
                });
            });

        }
    })
})