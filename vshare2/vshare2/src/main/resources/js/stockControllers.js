app.controller('AddStockController', function($scope , $location, DataStockFactory, AddStockFactory, $route ){
	this.addStock = function(addNewStockCtrl){
		AddStockFactory.save(addNewStockCtrl);
		DataStockFactory.addStock(addNewStockCtrl);
		
		window.setTimeout(function() {
			$location.path('/');
        }, 10); 
		
	};
});

app.controller('EditStockController', function($scope, $location, ListStockByIdFactory, EditStockFactory, $route, $routeParams ){
			
	var selectedStock = ListStockByIdFactory.query({id:$routeParams.id});
	selectedStock.$promise.then(function(result){
			
		$scope.editStockCtrl = $scope.editStockCtrl || {};
		$scope.editStockCtrl.name = result.name;		
		$scope.editStockCtrl.currentPrice = result.currentPrice;		
	});
	
	this.editStock = function(editStockCtrl){
		EditStockFactory.update({id:$routeParams.id},editStockCtrl);
		
		window.setTimeout(function() {
			$location.path('/');
        }, 10); 
		
	};
});
	
app.controller('ListStockController', function($scope, $location,  DataStockFactory, ListStockFactory, $route){
	var stock = ListStockFactory.query();
	stock.$promise.then(function (result) {
		DataStockFactory.init(result);
	    $scope.stocks = DataStockFactory.getStocks();
	});
});


app.controller('StockActionsController', function($scope, $location, ListStockByIdFactory, DeleteStockFactory) {
  
     
    $scope.deleteStock = function(stockid) {
        DeleteStockFactory.delete_stock({id:stockid},null);
        
		window.setTimeout(function() {
			location.reload(true);
        }, 10);
		
     };
});
    