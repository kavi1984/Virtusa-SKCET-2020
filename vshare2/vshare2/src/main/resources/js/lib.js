window.onload = () => {
	var stockData = [];
	var chart = am4core.create("chartdiv", am4charts.XYChart);
	chart.paddingRight = 20;
	am4core.useTheme(am4themes_dark);
	am4core.useTheme(am4themes_material);
	
	var stockHistory = document.getElementById("stockHistory");
	var stockIntraday = document.getElementById("stockIntraday");
	
	if(stockHistory.value && JSON.parse(stockHistory.value).length > 0) {
		stockData = JSON.parse(stockHistory.value);
		chart.dateFormatter.inputDateFormat = "yyyy-MM-dd";
		
	} else if (stockIntraday.value && JSON.parse(stockIntraday.value).length > 0) {
		stockData = JSON.parse(stockIntraday.value);
		chart.dateFormatter.inputDateFormat = "yyyy-MM-dd HH:mm:ss";
	}
		
	var dateAxis = chart.xAxes.push(new am4charts.DateAxis());
	dateAxis.renderer.grid.template.location = 0;

	var valueAxis = chart.yAxes.push(new am4charts.ValueAxis());
	valueAxis.tooltip.disabled = true;

	var series = chart.series.push(new am4charts.CandlestickSeries());
	series.dataFields.dateX = "date";
	series.dataFields.valueY = "close";
	series.dataFields.openValueY = "open";
	series.dataFields.lowValueY = "low";
	series.dataFields.highValueY = "high";
	series.simplifiedProcessing = true;
	series.tooltipText = "Open:${openValueY.value}\nLow:${lowValueY.value}\nHigh:${highValueY.value}\nClose:${valueY.value}";

	chart.cursor = new am4charts.XYCursor();
	var lineSeries = chart.series.push(new am4charts.LineSeries());
	lineSeries.dataFields.dateX = "date";
	lineSeries.dataFields.valueY = "close";
	lineSeries.defaultState.properties.visible = false;
	lineSeries.hiddenInLegend = true;
	lineSeries.fillOpacity = 0.5;
	lineSeries.strokeOpacity = 0.5;

	var scrollbarX = new am4charts.XYChartScrollbar();
	scrollbarX.series.push(lineSeries);
	chart.scrollbarX = scrollbarX;

	chart.data = stockData;
	
};     

$('soflow').selectize({
    create: true,
    sortField: 'text'
})
