app.factory('AddStockFactory', function ($resource, $location) {
    return $resource(($location.protocol() + "://" + $location.host() + ":" + $location.port()) + '/stocks', {}, {
    	save: { method: 'POST' }
    });
});

app.factory('EditStockFactory', function ($resource, $location) {
    return $resource(($location.protocol() + "://" + $location.host() + ":" + $location.port()) + '/stocks/:id',{}, {
    	update: { method: 'PUT' }
    });
});

app.factory('DeleteStockFactory', function ($resource, $location) {
    return $resource(($location.protocol() + "://" + $location.host() + ":" + $location.port()) + '/stocks/:id',{}, {
    	delete_stock: { method: 'DELETE' }
    });
});

app.factory('ListStockFactory', function ($resource, $location) {
    return $resource(($location.protocol() + "://" + $location.host() + ":" + $location.port()) + '/stocks', {}, {
    	query: { method: 'GET', isArray: true }
    })
});

app.factory('ListStockByIdFactory', function ($resource, $location) {
    return $resource(($location.protocol() + "://" + $location.host() + ":" + $location.port()) + '/stocks/:id',{}, {
    	query: { method: 'GET' }
    });
});


app.factory('DataStockFactory', function(){
	  var StockList = [];

	  var init = function(stockList){
		  StockList = angular.copy(stockList);
	  };
	  
	  var addStock = function(newStock) {
		  StockList.push(newStock);
	  };

	  var getStocks = function(){
	      return StockList;
	  };

	  return {
		  init : init,
		  addStock: addStock,
		  getStocks: getStocks
	  };
  });